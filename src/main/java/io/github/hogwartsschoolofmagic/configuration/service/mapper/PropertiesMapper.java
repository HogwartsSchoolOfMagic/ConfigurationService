package io.github.hogwartsschoolofmagic.configuration.service.mapper;

import io.github.hogwartsschoolofmagic.configuration.dto.PropertyDto;
import io.github.hogwartsschoolofmagic.configuration.dto.PropertyReturnDto;
import io.github.hogwartsschoolofmagic.configuration.persistance.model.Property;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapping for configuration properties.
 *
 * @author Vladislav [SmithyVL] Kuznetsov.
 * @since 1.0.0.
 */
@Mapper
public interface PropertiesMapper {

  /**
   * Getting a DTO from an entity.
   *
   * @param entity configuration property.
   * @return DTO for config property.
   */
  @Mapping(source = "compoundId.application", target = "application")
  @Mapping(source = "compoundId.profile", target = "profile")
  @Mapping(source = "compoundId.label", target = "label")
  @Mapping(source = "compoundId.key", target = "key")
  PropertyReturnDto toDto(Property entity);

  /**
   * Converting a list of entities to a list of DTO objects.
   *
   * @param entities list of entities.
   * @return the resulting DTO with id list.
   */
  default List<PropertyReturnDto> toDto(Collection<Property> entities) {
    return entities.stream().map(this::toDto).toList();
  }

  /**
   * Getting an entity from a DTO.
   *
   * @param dto DTO configuration properties.
   * @return configuration property.
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "compoundId", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "updated", ignore = true)
  @Mapping(source = "application", target = "compoundId.application")
  @Mapping(source = "profile", target = "compoundId.profile")
  @Mapping(source = "label", target = "compoundId.label")
  @Mapping(source = "key", target = "compoundId.key")
  Property toEntity(PropertyDto dto);

  /**
   * Updating the current state of an entity.
   *
   * @param from initial configuration data.
   * @param to   target configuration data.
   * @return updated configuration property.
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "updated", ignore = true)
  Property updateFrom(Property from, @MappingTarget Property to);
}