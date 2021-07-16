package ru.bangerok.enterprise.configuration.rest;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bangerok.enterprise.configuration.dto.ConfigurationProperty;
import ru.bangerok.enterprise.configuration.dto.NewConfigurationProperties;
import ru.bangerok.enterprise.configuration.dto.SearchConfigurationProperty;

/**
 * Feign is an interface containing methods for execution in other services and modules.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@FeignClient("${CONFIG_APPLICATION_NAME:configuration-server}")
@RequestMapping("/configuration")
public interface ConfigurationClient {

  /**
   * Search configuration properties by key, description, or value.
   *
   * @param searchConfigurationProperty data for searching configuration properties.
   * @param sortField                   field for sorting properties (for the first four fields
   *                                    mentioned above - you need to use the 'id' prefix).
   * @param sortType                    sort type: {@code 'asc' or 'desc'}.
   * @return list of found configuration properties.
   */
  @PostMapping("/get-all")
  List<ConfigurationProperty> findAll(
      @RequestBody SearchConfigurationProperty searchConfigurationProperty,
      @RequestParam(required = false, defaultValue = "id.application") String sortField,
      @RequestParam(required = false, defaultValue = "asc") String sortType
  );

  /**
   * Create configuration properties for individual application, profile and label.
   *
   * @param newPropertiesData configuration properties data.
   * @return list of generated configuration properties.
   */
  @PostMapping("/create-all")
  List<ConfigurationProperty> createAll(@RequestBody NewConfigurationProperties newPropertiesData);

  /**
   * Saving configuration properties for an individual application, profile, and label.
   *
   * @param newPropertiesData configuration properties data.
   * @return list of saved configuration properties.
   */
  @PostMapping("/save-all")
  List<ConfigurationProperty> saveAll(@RequestBody NewConfigurationProperties newPropertiesData);

  /**
   * Creating a configuration property.
   *
   * @param configurationProperty these properties.
   * @return the created config property.
   */
  @PostMapping("/create")
  ConfigurationProperty create(@RequestBody ConfigurationProperty configurationProperty);

  /**
   * Saving the configuration property.
   *
   * @param configurationProperty these properties.
   * @return saved configuration property.
   */
  @PostMapping("/save")
  ConfigurationProperty save(@RequestBody ConfigurationProperty configurationProperty);

  /**
   * Deleting a configuration property.
   *
   * @param configurationProperty удаляемое свойство.
   */
  @DeleteMapping("/delete")
  void delete(@RequestBody ConfigurationProperty configurationProperty);

  /**
   * Forced update of configurations.
   */
  @GetMapping("/refresh")
  void refresh();
}