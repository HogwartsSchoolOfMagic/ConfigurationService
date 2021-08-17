package ru.bangerok.enterprise.configuration.service.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bangerok.enterprise.configuration.dto.NewPropertiesDto;
import ru.bangerok.enterprise.configuration.dto.PropertyDto;
import ru.bangerok.enterprise.configuration.persistance.model.Property;

/**
 * Mapping for configuration properties.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@Mapper
public interface PropertiesMapper {

  /**
   * Getting a DTO from an entity.
   *
   * @param entity configuration property.
   * @return DTO for config property.
   */
  @Mapping(source = "id.application", target = "application")
  @Mapping(source = "id.profile", target = "profile")
  @Mapping(source = "id.label", target = "label")
  @Mapping(source = "id.key", target = "key")
  PropertyDto toDto(Property entity);

  /**
   * Converting a list of entities to a list of DTO objects.
   *
   * @param entities list of entities.
   * @return the resulting DTO list.
   */
  default List<PropertyDto> toDto(Collection<Property> entities) {
    return entities.stream().map(this::toDto).toList();
  }

  /**
   * Converting a DTO list to a list of entities.
   *
   * @param newPropertiesDto data for conversion.
   * @return the resulting list of entities.
   */
  default List<PropertyDto> toDto(NewPropertiesDto newPropertiesDto) {
    return newPropertiesDto.getKeysValues().entrySet().stream()
        .map((Map.Entry<String, String> entry) -> {
          var property = new PropertyDto();
          property.setApplication(newPropertiesDto.getApplication());
          property.setProfile(newPropertiesDto.getProfile());
          property.setLabel(newPropertiesDto.getLabel());
          property.setKey(entry.getKey());
          property.setValue(entry.getValue());
          return property;
        }).toList();
  }

  /**
   * Getting an entity from a DTO.
   *
   * @param dto DTO configuration properties.
   * @return configuration property.
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "application", target = "id.application")
  @Mapping(source = "profile", target = "id.profile")
  @Mapping(source = "label", target = "id.label")
  @Mapping(source = "key", target = "id.key")
  Property toEntity(PropertyDto dto);
}