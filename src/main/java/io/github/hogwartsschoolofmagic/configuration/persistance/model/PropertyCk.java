package io.github.hogwartsschoolofmagic.configuration.persistance.model;

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
 * @author Vladislav [SmithyVL] Kuznetsov.
 * @since 1.0.0.
 */
@Getter
@Setter
@ToString
@Embeddable
public class PropertyCk implements Serializable {

  /**
   * The name of the application to which the configuration belongs.
   */
  @NotNull
  private String application;

  /**
   * The profile with which the application was launched.
   */
  @NotNull
  private String profile;

  /**
   * Label.
   */
  @NotNull
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

    if (o == null) {
      return false;
    }

    if (o.getClass() != this.getClass()) {
      return false;
    }

    var that = (PropertyCk) o;
    return Objects.equals(getApplication(), that.getApplication())
        && Objects.equals(getProfile(), that.getProfile())
        && Objects.equals(getLabel(), that.getLabel())
        && Objects.equals(getKey(), that.getKey());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getApplication(), getProfile(), getLabel(), getKey());
  }
}