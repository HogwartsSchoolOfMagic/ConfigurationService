package io.github.ninjaenterprise.configuration.controller;

import io.github.ninjaenterprise.configuration.dto.PropertyDto;
import io.github.ninjaenterprise.configuration.dto.PropertyReturnDto;
import io.github.ninjaenterprise.configuration.exceptions.ConfigurationException;
import io.github.ninjaenterprise.search.model.SearchSettings;
import io.github.ninjaenterprise.search.model.SearchSettingsSimple;
import io.github.ninjaenterprise.search.model.TableResult;
import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign is an interface containing methods for execution in other services and modules.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@FeignClient("${CONFIG_APPLICATION_NAME:configuration-service}")
@RequestMapping("/configuration")
public interface ConfigurationClient {

  /**
   * Search for configuration properties by filtering options for results and paging.
   *
   * @param filters search data.
   * @return page with list of found configuration properties.
   * @throws ConfigurationException page properties by filters not found.
   */
  @PostMapping("/page")
  TableResult<PropertyReturnDto> getPage(@RequestBody SearchSettings filters)
      throws ConfigurationException;

  /**
   * Create a configuration property if it doesn't already exist with that primary key.
   *
   * @param propertyDto these properties.
   * @param refresh     flag refresh properties.
   * @return created configuration, or the current one.
   * @throws ConfigurationException property creating error.
   */
  @PostMapping
  PropertyReturnDto create(
      @RequestBody @Valid PropertyDto propertyDto,
      @RequestParam(defaultValue = "false") boolean refresh
  ) throws ConfigurationException;

  /**
   * Create a configuration properties if it doesn't already exist with that primary key.
   *
   * @param propertyDtoList these properties.
   * @param refresh         flag refresh properties.
   * @return created configurations.
   * @throws ConfigurationException properties creating error.
   */
  @PostMapping("/batch")
  List<PropertyReturnDto> createAll(
      @RequestBody @Valid List<PropertyDto> propertyDtoList,
      @RequestParam(defaultValue = "false") boolean refresh
  ) throws ConfigurationException;

  /**
   * Get configuration property by ID.
   *
   * @param propertyId property identifier.
   * @return configuration property.
   * @throws ConfigurationException getting property error by id.
   */
  @GetMapping("/{propertyId}")
  PropertyReturnDto getById(@PathVariable Long propertyId) throws ConfigurationException;

  /**
   * Saving/Updating configuration properties.
   *
   * @param propertyDto these properties.
   * @param refresh     flag refresh properties.
   * @return saved configuration, or the current one.
   * @throws ConfigurationException property saving error.
   */
  @PutMapping
  PropertyReturnDto save(
      @RequestBody @Valid PropertyDto propertyDto,
      @RequestParam(defaultValue = "false") boolean refresh
  ) throws ConfigurationException;

  /**
   * Saving configuration properties for an individual application, profile, and label.
   *
   * @param propertyDtoList configuration properties data.
   * @param refresh         flag refresh properties.
   * @return list of saved configuration properties.
   * @throws ConfigurationException properties saving error.
   */
  @PutMapping("/batch")
  List<PropertyReturnDto> saveAll(
      @RequestBody @Valid List<PropertyDto> propertyDtoList,
      @RequestParam(defaultValue = "false") boolean refresh
  ) throws ConfigurationException;

  /**
   * Removing configuration properties.
   *
   * @param filters filtering options for properties to be removed.
   * @param refresh flag refresh properties.
   * @return list of removed configuration properties.
   * @throws ConfigurationException configuration properties deletion errors.
   */
  @DeleteMapping("/batch")
  List<PropertyReturnDto> deleteAll(
      @RequestBody SearchSettingsSimple filters,
      @RequestParam(defaultValue = "false") boolean refresh
  ) throws ConfigurationException;

  /**
   * Removing an existing configuration property by identifier.
   *
   * @param propertyId property identifier.
   * @param refresh    flag refresh properties.
   * @return deleted configuration property.
   * @throws ConfigurationException property delete error by id.
   */
  @DeleteMapping("/{propertyId}")
  PropertyReturnDto deleteById(
      @PathVariable Long propertyId,
      @RequestParam(defaultValue = "false") boolean refresh
  ) throws ConfigurationException;

  /**
   * Forced update of configurations in applications.
   *
   * @param application application name for refresh.
   */
  @GetMapping("/refresh/{application}")
  void refresh(@PathVariable String application);
}