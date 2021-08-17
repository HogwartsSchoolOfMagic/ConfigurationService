package ru.bangerok.enterprise.configuration.dto;

import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.bangerok.enterprise.configuration.base.AbstractBaseProperty;

/**
 * DTO to save/update configuration properties.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NewPropertiesDto extends AbstractBaseProperty {

  /**
   * List of keys with values of configuration properties.
   */
  private Map<String, String> keysValues;
}