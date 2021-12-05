package io.github.ninjaenterprise.configuration.persistance.model;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
   * Configuration property identifier.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "base_id", unique = true, nullable = false)
  private Long id;

  /**
   * Private field that stores information about the date the record was saved in the database. No
   * need to pre-assign.
   */
  @Column(name = "base_created_date", nullable = false)
  private LocalDateTime created;

  /**
   * Private field that stores information about the date the record was updated in the database.
   * No need to pre-assign.
   */
  @Column(name = "base_updated_date")
  private LocalDateTime updated;

  /**
   * Configuration primary key.
   */
  @Embedded
  @Valid
  private PropertyCk compoundId;

  /**
   * Configuration value.
   */
  private String value;

  /**
   * Configuration description.
   */
  private String description;

  /**
   * Configuration value type.
   */
  private String type;

  /**
   * Regex expression to validate configuration value.
   */
  private String pattern;

  /**
   * Setting the creation date of the configuration property before storing it in the database.
   */
  @PrePersist
  public void prePersist() {
    setCreated(LocalDateTime.now());
  }

  /**
   * Setting the update date of the configuration property before updating in the database.
   */
  @PreUpdate
  public void preUpdate() {
    setUpdated(LocalDateTime.now());
  }

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

    var that = (Property) o;
    return Objects.equals(getId(), that.getId())
        && Objects.equals(getCompoundId(), that.getCompoundId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getCompoundId());
  }
}