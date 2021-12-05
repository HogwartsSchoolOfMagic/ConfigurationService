package io.github.ninjaenterprise.configuration.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Dto for config property with appended id.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PropertyReturnDto extends PropertyDto {

  /**
   * The identifier for the configuration property.
   */
  private Long id;
}