package ru.bangerok.enterprise.configuration.rest;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bangerok.enterprise.configuration.dto.NewPropertiesDto;
import ru.bangerok.enterprise.configuration.dto.PropertyDto;
import ru.bangerok.enterprise.configuration.dto.SearchPropertyDto;

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
   * Search configuration properties by key, description or value.
   *
   * @param searchPropertyDto data for searching configuration properties.
   * @param sortField         field for sorting properties (for the first four fields
   *                          mentioned above — you need to use the 'id' prefix).
   * @param sortType          sort type: {@code 'asc' or 'desc'}.
   * @return list of found configuration properties.
   */
  @PostMapping("/get-all")
  List<PropertyDto> findAll(
      @RequestBody SearchPropertyDto searchPropertyDto,
      @RequestParam(required = false, defaultValue = "id.application") String sortField,
      @RequestParam(required = false, defaultValue = "asc") String sortType
  );

  /**
   * Create configuration properties for individual application, profile and label.
   *
   * @param newPropertiesDtoData configuration properties data.
   * @return list of generated configuration properties.
   */
  @PostMapping("/create-all")
  List<PropertyDto> createAll(@RequestBody NewPropertiesDto newPropertiesDtoData);

  /**
   * Saving configuration properties for an individual application, profile and label.
   *
   * @param newPropertiesDtoData configuration properties data.
   * @return list of saved configuration properties.
   */
  @PostMapping("/save-all")
  List<PropertyDto> saveAll(@RequestBody NewPropertiesDto newPropertiesDtoData);

  /**
   * Creating a configuration property.
   *
   * @param propertyDto these properties.
   * @return the created config property.
   */
  @PostMapping("/create")
  PropertyDto create(@RequestBody PropertyDto propertyDto);

  /**
   * Saving the configuration property.
   *
   * @param propertyDto these properties.
   * @return saved configuration property.
   */
  @PostMapping("/save")
  PropertyDto save(@RequestBody PropertyDto propertyDto);

  /**
   * Deleting a configuration property.
   *
   * @param propertyDto удаляемое свойство.
   */
  @DeleteMapping("/delete")
  void delete(@RequestBody PropertyDto propertyDto);

  /**
   * Forced update of configurations.
   */
  @GetMapping("/refresh")
  void refresh();
}