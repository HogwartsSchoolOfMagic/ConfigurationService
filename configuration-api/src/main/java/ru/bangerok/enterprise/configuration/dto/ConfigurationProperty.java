package ru.bangerok.enterprise.configuration.dto;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.bangerok.enterprise.configuration.dto.base.AbstractBaseProperty;

/**
 * DTO for config property.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ConfigurationProperty extends SearchConfigurationProperty {

  /**
   * Configuration property creation date.
   */
  private LocalDateTime creation;

  /**
   * Configuration property modification date.
   */
  private LocalDateTime modify;
}