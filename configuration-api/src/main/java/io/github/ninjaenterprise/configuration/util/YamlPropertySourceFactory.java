package io.github.ninjaenterprise.configuration.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

/**
 * Factory that allows you to use the connection of system files in YML/YAML format.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.2.
 */
public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {

  /**
   * Source configuration with properties from YML/YAML format resource.
   *
   * @param name     resource name.
   * @param resource the resource itself.
   * @return an object with properties configured for operation.
   * @throws IOException error with the settings file.
   */
  @NonNull
  @Override
  public PropertySource<?> createPropertySource(String name, EncodedResource resource)
      throws IOException {

    if (!resource.getResource().exists()) {
      return super.createPropertySource(name, resource);
    }

    var properties = getProperties(resource);
    final String resourceName = getResourceName(name, resource);

    return new PropertiesPropertySource(resourceName, properties);
  }

  /**
   * Checking and getting the name of the resource.
   *
   * @param name     resource name.
   * @param resource resource.
   * @return resource name.
   */
  private static String getResourceName(String name, EncodedResource resource) {
    final String resourceName;
    if (StringUtils.hasText(name)) {
      resourceName = name;
    } else {
      resourceName = resource.getResource().getDescription();
    }
    return resourceName;
  }

  /**
   * Getting properties from a resource.
   *
   * @param resource resource.
   * @return configuration properties.
   * @throws FileNotFoundException file not found.
   */
  @NonNull
  private static Properties getProperties(EncodedResource resource) throws FileNotFoundException {
    try {
      var factory = new YamlPropertiesFactoryBean();
      factory.setResources(resource.getResource());
      return Objects.requireNonNullElseGet(factory.getObject(), Properties::new);
    } catch (IllegalStateException e) {
      // for ignoreResourceNotFound
      Throwable cause = e.getCause();
      if (cause instanceof FileNotFoundException) {
        throw (FileNotFoundException) e.getCause();
      }
      throw e;
    }
  }
}