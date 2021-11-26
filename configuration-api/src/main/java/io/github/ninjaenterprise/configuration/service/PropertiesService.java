package io.github.ninjaenterprise.configuration.service;

import io.github.ninjaenterprise.configuration.dto.PropertyDto;
import io.github.ninjaenterprise.configuration.dto.PropertyReturnDto;
import io.github.ninjaenterprise.configuration.exceptions.ConfigurationException;
import io.github.ninjaenterprise.search.model.SearchSettings;
import io.github.ninjaenterprise.search.model.SearchSettingsSimple;
import io.github.ninjaenterprise.search.model.TableResult;
import java.util.List;
import reactor.core.publisher.Flux;

/**
 * Interface that provides methods for working with configuration properties from a database
 * provider.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
public interface PropertiesService {

  /**
   * Search for configuration properties by filtering options for results and paging.
   *
   * @param filters search data.
   * @return page with list of found configuration properties.
   * @throws ConfigurationException page properties by filters not found.
   */
  Flux<TableResult<PropertyReturnDto>> getPage(SearchSettings filters)
      throws ConfigurationException;

  /**
   * Search for configuration properties by filtering options.
   *
   * @param filters search data.
   * @return list of found configuration properties.
   * @throws ConfigurationException properties by filters not found.
   */
  List<PropertyReturnDto> findAll(SearchSettingsSimple filters) throws ConfigurationException;

  /**
   * Create a configuration property if it doesn't already exist with that primary key.
   *
   * @param propertyDto these properties.
   * @return the saved configuration, or the current one.
   * @throws ConfigurationException property creating error.
   */
  PropertyReturnDto create(PropertyDto propertyDto) throws ConfigurationException;

  /**
   * Saving/Updating configuration properties.
   *
   * @param propertyDto these properties.
   * @return the saved configuration, or the current one.
   */
  PropertyReturnDto save(PropertyDto propertyDto);

  /**
   * Removing an existing configuration property by identifier.
   *
   * @param propertyId property identifier.
   * @throws ConfigurationException property delete error by id.
   */
  PropertyReturnDto deleteById(Long propertyId) throws ConfigurationException;

  /**
   * Get configuration property by ID.
   *
   * @param propertyId property identifier.
   * @throws ConfigurationException getting property error by id.
   */
  PropertyReturnDto findById(Long propertyId) throws ConfigurationException;
}