package ru.bangerok.enterprise.configuration.persistance.model;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity for working with the configuration properties table.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@Setter
@Getter
@ToString
@Entity
@Table(name = "properties")
public class Property {

  /**
   * Configuration primary key.
   */
  @Valid
  @EmbeddedId
  private PropertyPk id;

  /**
   * Configuration value.
   */
  private String value;

  /**
   * Configuration description.
   */
  private String description;

  /**
   * Configuration creation date.
   */
  private LocalDateTime creation;

  /**
   * Configuration change date.
   */
  private LocalDateTime modify;

  /**
   * Setting the creation date of a configuration property before storing it in the database.
   */
  @PrePersist
  public void prePersist() {
    setCreation(LocalDateTime.now());
  }

  /**
   * Setting the update date of the configuration property before updating in the database.
   */
  @PreUpdate
  public void preUpdate() {
    setModify(LocalDateTime.now());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Property that)) {
      return false;
    }

    return getId().equals(that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}