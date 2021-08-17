package ru.bangerok.enterprise.configuration.dto;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for config property.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PropertyDto extends SearchPropertyDto {

  /**
   * Property creation date.
   */
  private LocalDateTime creation;

  /**
   * Property modification date.
   */
  private LocalDateTime modify;
}