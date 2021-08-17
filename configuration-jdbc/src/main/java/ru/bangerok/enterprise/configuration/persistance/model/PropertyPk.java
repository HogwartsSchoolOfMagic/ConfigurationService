package ru.bangerok.enterprise.configuration.persistance.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Primary composite key for the configuration properties entity.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@Getter
@Setter
@ToString
@Embeddable
public class PropertyPk implements Serializable {

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PropertyPk that)) {
      return false;
    }
    return getApplication().equals(that.getApplication())
        && getProfile().equals(that.getProfile())
        && getLabel().equals(that.getLabel())
        && getKey().equals(that.getKey());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getApplication(), getProfile(), getLabel(), getKey());
  }
}