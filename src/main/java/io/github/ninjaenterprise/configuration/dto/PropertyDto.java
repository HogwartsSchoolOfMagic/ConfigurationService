package io.github.ninjaenterprise.configuration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for configuration property.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Schema(description = "DTO for configuration property")
public class PropertyDto {

  /**
   * The name of the application to which the configuration belongs.
   */
  @NotNull
  @Pattern(regexp = "^(.){0,255}+$", message = "The allowed field length is 255 characters")
  @Pattern(
      regexp = "^[\\p{Graph}\\p{Space}]+$",
      message = "Invalid language of entered characters. 'English' expected"
  )
  @EqualsAndHashCode.Include
  @ToString.Include
  @Schema(description = "The name of the application to which the configuration belongs")
  private String application;

  /**
   * The profile with which the application was launched.
   */
  @NotNull
  @Pattern(regexp = "^(.){0,255}+$", message = "The allowed field length is 255 characters")
  @Pattern(
      regexp = "^[\\p{Graph}\\p{Space}]+$",
      message = "Invalid language of entered characters. 'English' expected"
  )
  @EqualsAndHashCode.Include
  @ToString.Include
  @Schema(description = "The profile with which the application was launched")
  private String profile = "default";

  /**
   * Label.
   */
  @NotNull
  @Pattern(regexp = "^(.){0,255}+$", message = "The allowed field length is 255 characters")
  @Pattern(
      regexp = "^[\\p{Graph}\\p{Space}]+$",
      message = "Invalid language of entered characters. 'English' expected"
  )
  @EqualsAndHashCode.Include
  @ToString.Include
  @Schema(description = "Label")
  private String label = "master";

  /**
   * Configuration key.
   */
  @NotNull
  @Pattern(regexp = "^(.){0,255}+$", message = "The allowed field length is 255 characters")
  @Pattern(
      regexp = "^[\\p{Graph}\\p{Space}]+$",
      message = "Invalid language of entered characters. 'English' expected"
  )
  @EqualsAndHashCode.Include
  @ToString.Include
  @Schema(description = "Configuration key")
  private String key;

  /**
   * Configuration property value.
   */
  @NotNull
  @Pattern(regexp = "^(.){0,1024}+$", message = "The allowed field length is 1024 characters")
  @Schema(description = "Configuration property value")
  private String value;

  /**
   * Description of the configuration property.
   */
  @Pattern(regexp = "^(.){0,1024}+$", message = "The allowed field length is 1024 characters")
  @Schema(description = "Description of the configuration property")
  private String description;

  /**
   * Type of the configuration property.
   */
  @Pattern(regexp = "^(.){0,255}+$", message = "The allowed field length is 255 characters")
  @Schema(description = "Type of the configuration property")
  private String type = "String";

  /**
   * Regex expression to check the configuration value.
   */
  @Pattern(regexp = "^(.){0,255}+$", message = "The allowed field length is 255 characters")
  @Schema(description = "Regex expression to check the configuration value")
  private String pattern;

  /**
   * Property creation date.
   */
  @Schema(description = "Property creation date")
  private LocalDateTime created;

  /**
   * Property updated date.
   */
  @Schema(description = "Property updated date")
  private LocalDateTime updated;
}