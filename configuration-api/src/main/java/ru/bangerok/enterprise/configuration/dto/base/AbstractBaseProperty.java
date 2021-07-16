package ru.bangerok.enterprise.configuration.dto.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Base abstract class for DTO inheritance.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class AbstractBaseProperty {

  /**
   * The name of the application to which the configuration belongs.
   */
  private String application = "";

  /**
   * The profile with which the application was launched.
   */
  private String profile = "default";

  /**
   * Label.
   */
  private String label = "master";
}