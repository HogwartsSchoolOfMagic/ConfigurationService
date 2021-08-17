package ru.bangerok.enterprise.configuration.service;

import java.util.List;
import ru.bangerok.enterprise.configuration.dto.NewPropertiesDto;
import ru.bangerok.enterprise.configuration.dto.PropertyDto;
import ru.bangerok.enterprise.configuration.dto.SearchPropertyDto;

/**
 * Interface that provides methods for working with configuration properties from a database
 * provider.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
public interface PropertiesService {

  /**
   * Search for configuration properties by key, description and value.
   *
   * @param searchPropertyDto search data.
   * @param sortField         sorting field.
   * @param sortType          sorting type.
   * @return list of found configuration properties.
   */
  List<PropertyDto> findAll(SearchPropertyDto searchPropertyDto, String sortField,
                            String sortType);

  /**
   * Creating a configuration property. Doesn't update the configuration if it already exists!
   *
   * @param propertyDto these properties.
   * @return the saved configuration, or the current one.
   */
  PropertyDto create(PropertyDto propertyDto);

  /**
   * Saving multiple configuration properties. Doesn't update the configuration if it already
   * exists!
   *
   * @param newPropertiesDto property data.
   * @return saved configuration properties.
   */
  List<PropertyDto> createAll(NewPropertiesDto newPropertiesDto);

  /**
   * Saving/updating configuration properties.
   *
   * @param propertyDto these properties.
   * @return saved/updated configuration property.
   */
  PropertyDto save(PropertyDto propertyDto);

  /**
   * Save/update multiple configuration properties.
   *
   * @param newPropertiesDto property data.
   * @return saved/updated configuration properties.
   */
  List<PropertyDto> saveAll(NewPropertiesDto newPropertiesDto);

  /**
   * Removing an existing configuration property.
   *
   * @param propertyDto property to be removed.
   */
  void delete(PropertyDto propertyDto);
}