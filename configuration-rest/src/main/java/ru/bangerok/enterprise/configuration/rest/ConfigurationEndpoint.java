package ru.bangerok.enterprise.configuration.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bangerok.enterprise.configuration.dto.ConfigurationProperty;
import ru.bangerok.enterprise.configuration.dto.NewConfigurationProperties;
import ru.bangerok.enterprise.configuration.dto.SearchConfigurationProperty;
import ru.bangerok.enterprise.configuration.dto.service.ConfigurationPropertiesService;

/**
 * REST is a controller for CRUD operations with configuration properties.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Transactional(rollbackFor = Exception.class)
public class ConfigurationEndpoint implements ConfigurationClient {

  /**
   * The name of the configuration service for Spring Cloud.
   */
  @Value("${spring.application.name}")
  private String applicationName;

  /**
   * Service for working with the external module of the database provider.
   */
  private final ConfigurationPropertiesService service;

  /**
   * Bus properties for the configuration of the update event of the configuration properties on
   * clients.
   */
  private final BusProperties busProperties;

  /**
   * Publisher for events that update configuration properties on clients.
   */
  private final ApplicationEventPublisher publisher;

  @Override
  public List<ConfigurationProperty> findAll(
      @RequestBody SearchConfigurationProperty searchConfigurationProperty,
      @RequestParam(required = false, defaultValue = "id.application") String sortField,
      @RequestParam(required = false, defaultValue = "asc") String sortType) {
    return service.findAll(searchConfigurationProperty, sortField, sortType);
  }

  @Override
  public List<ConfigurationProperty> createAll(NewConfigurationProperties newPropertiesData) {
    final List<ConfigurationProperty> savedProperties = service.createAll(newPropertiesData);
    refreshCloudConfigs();
    return savedProperties;
  }

  @Override
  public List<ConfigurationProperty> saveAll(
      @RequestBody NewConfigurationProperties newPropertiesData) {
    final List<ConfigurationProperty> savedProperties = service.saveAll(newPropertiesData);
    refreshCloudConfigs();
    return savedProperties;
  }

  @Override
  public ConfigurationProperty create(ConfigurationProperty configurationProperty) {
    final var created = service.create(configurationProperty);
    refreshCloudConfigs();
    return created;
  }

  @Override
  public ConfigurationProperty save(@RequestBody ConfigurationProperty configurationProperty) {
    final var savedProperty = service.save(configurationProperty);
    refreshCloudConfigs();
    return savedProperty;
  }

  @Override
  public void delete(@RequestBody ConfigurationProperty configurationProperty) {
    service.delete(configurationProperty);
    refreshCloudConfigs();
  }

  @Override
  public void refresh() {
    refreshCloudConfigs();
  }

  /**
   * Updating configuration properties on clients.
   */
  public void refreshCloudConfigs() {
    String instanceId = busProperties.getId();
    var event = new RefreshRemoteApplicationEvent(applicationName, instanceId, () -> "*:**");
    publisher.publishEvent(event);
  }
}