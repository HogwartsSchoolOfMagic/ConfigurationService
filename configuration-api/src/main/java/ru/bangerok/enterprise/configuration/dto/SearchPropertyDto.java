package ru.bangerok.enterprise.configuration.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.bangerok.enterprise.configuration.base.AbstractBaseProperty;

/**
 * DTO to find configuration properties.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class SearchPropertyDto extends AbstractBaseProperty {

  /**
   * Configuration key.
   */
  @EqualsAndHashCode.Include
  private String key;

  /**
   * Configuration property value.
   */
  private String value;

  /**
   * Description of the configuration property.
   */
  private String description;
}