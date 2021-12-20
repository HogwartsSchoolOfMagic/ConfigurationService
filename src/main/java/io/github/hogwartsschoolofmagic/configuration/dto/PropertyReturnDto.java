package io.github.hogwartsschoolofmagic.configuration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Dto for config property with appended id.
 *
 * @author Vladislav [SmithyVL] Kuznetsov.
 * @since 1.0.0.
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Dto for config property with appended id")
public class PropertyReturnDto extends PropertyDto {

  /**
   * The identifier for the configuration property.
   */
  @Schema(description = "The identifier for the configuration property")
  private Long id;
}