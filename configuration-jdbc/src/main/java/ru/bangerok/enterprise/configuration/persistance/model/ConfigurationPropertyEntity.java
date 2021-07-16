package ru.bangerok.enterprise.configuration.persistance.model;

import java.time.LocalDateTime;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity for working with the configuration properties table.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "properties")
public class ConfigurationPropertyEntity {

  /**
   * Configuration primary key.
   */
  @Valid
  @EmbeddedId
  @EqualsAndHashCode.Include
  private ConfigurationPropertyPk id;

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
   * Setting the update date of the configuration property before updating in the databaseх.
   */
  @PreUpdate
  public void preUpdate() {
    setModify(LocalDateTime.now());
  }
}