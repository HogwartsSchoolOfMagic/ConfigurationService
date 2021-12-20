package io.github.hogwartsschoolofmagic.configuration.exceptions;

/**
 * Errors caused in the configuration service.
 *
 * @author Vladislav [SmithyVL] Kuznetsov.
 * @since 1.0.0.
 */
public class ConfigurationException extends Exception {

  public ConfigurationException(String message) {
    super(message);
  }

  public ConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }
}