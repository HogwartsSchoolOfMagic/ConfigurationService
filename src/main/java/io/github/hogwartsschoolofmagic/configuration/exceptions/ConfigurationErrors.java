package io.github.hogwartsschoolofmagic.configuration.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Errors in working with configuration properties.
 *
 * @author Vladislav [SmithyVL] Kuznetsov.
 * @since 1.0.0.
 */
@Getter
@RequiredArgsConstructor
public enum ConfigurationErrors {

  PROPERTY_TYPE_NOT_AVAILABLE("Storing value '%s' with unavailable type '%s'."),
  PROPERTY_INCORRECT_TYPE("Value '%s' does not match type '%s'."),
  PROPERTY_INCORRECT_FORMAT("Value '%s' does not match the format '%s'."),
  PROPERTY_ID_FOR_CRUD_INCORRECT("Configuration property must not be null to remove/find."),
  PROPERTY_BY_ID_NOT_FOUND("Property not found with id - %s."),
  PROPERTY_DUPLICATE("Duplicate configuration - %s.");

  /**
   * Error message.
   */
  private final String msg;
}