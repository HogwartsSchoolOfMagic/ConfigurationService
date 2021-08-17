package ru.bangerok.enterprise.configuration.service;

import static ru.bangerok.enterprise.configuration.util.JdbcUtil.getOrder;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bangerok.enterprise.configuration.dto.NewPropertiesDto;
import ru.bangerok.enterprise.configuration.dto.PropertyDto;
import ru.bangerok.enterprise.configuration.dto.SearchPropertyDto;
import ru.bangerok.enterprise.configuration.persistance.repository.PropertiesRepository;
import ru.bangerok.enterprise.configuration.service.mapper.PropertiesMapper;

/**
 * Implementing an interface that provides methods for working with the configuration properties
 * repository.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
@PropertySource(value = "classpath:configuration-provider.properties", encoding = "UTF-8")
public class PropertiesServiceImpl implements PropertiesService {

  /**
   * Repository for working with an entity - configuration properties.
   */
  private final PropertiesRepository repository;

  /**
   * Mapper for converting DTOs to and from a property entity.
   */
  private final PropertiesMapper mapper;

  /**
   * Search for configuration properties by key, description and value.
   *
   * @param searchPropertyDto search data.
   * @param sortField         sorting field.
   * @param sortType          sorting type.
   * @return list of found configuration properties.
   */
  @Override
  public List<PropertyDto> findAll(SearchPropertyDto searchPropertyDto, String sortField,
                                   String sortType) {
    return mapper.toDto(repository.findByMultiplyParams(
        searchPropertyDto.getApplication(),
        searchPropertyDto.getProfile(),
        searchPropertyDto.getLabel(),
        searchPropertyDto.getKey(),
        searchPropertyDto.getDescription(),
        searchPropertyDto.getValue(),
        getOrder(sortField, sortType)
    ));
  }

  /**
   * Creating a configuration property. Doesn't update the configuration if it already exists!
   *
   * @param propertyDto these properties.
   * @return the saved configuration, or the current one.
   */
  @Override
  public PropertyDto create(PropertyDto propertyDto) {
    var configurationPropertyEntity = mapper.toEntity(propertyDto);
    var id = configurationPropertyEntity.getId();
    var byId = repository.findById(id);
    if (byId.isPresent()) {
      return mapper.toDto(byId.get());
    }
    return mapper.toDto(repository.save(configurationPropertyEntity));
  }

  /**
   * Saving multiple configuration properties. Doesn't update the configuration if it already
   * exists!
   *
   * @param newPropertiesDto property data.
   * @return saved configuration properties.
   */
  @Override
  public List<PropertyDto> createAll(NewPropertiesDto newPropertiesDto) {
    List<PropertyDto> propertyDtoEntities = mapper.toDto(newPropertiesDto);
    return propertyDtoEntities.stream().map(this::create).toList();
  }

  /**
   * Saving/updating configuration properties.
   *
   * @param propertyDto these properties.
   * @return saved/updated configuration property.
   */
  @Override
  public PropertyDto save(PropertyDto propertyDto) {
    var configurationPropertyEntity = mapper.toEntity(propertyDto);
    return mapper.toDto(repository.save(configurationPropertyEntity));
  }

  /**
   * Save/update multiple configuration properties.
   *
   * @param newPropertiesDto property data.
   * @return saved/updated configuration properties.
   */
  @Override
  public List<PropertyDto> saveAll(NewPropertiesDto newPropertiesDto) {
    List<PropertyDto> propertyDtoEntities = mapper.toDto(newPropertiesDto);
    return propertyDtoEntities.stream().map(this::save).toList();
  }

  /**
   * Removing an existing configuration property.
   *
   * @param propertyDto property to be removed.
   */
  @Override
  public void delete(PropertyDto propertyDto) {
    repository.deleteById(mapper.toEntity(propertyDto).getId());
  }
}