package ru.bangerok.enterprise.configuration.persistance.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Primary composite key for the configuration properties entity.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Embeddable
public class ConfigurationPropertyPk implements Serializable {

  /**
   * The name of the application to which the configuration belongs.
   */
  private String application;

  /**
   * The profile with which the application was launched.
   */
  private String profile;

  /**
   * Label.
   */
  private String label;

  /**
   * Configuration key.
   */
  @NotNull
  private String key;
}