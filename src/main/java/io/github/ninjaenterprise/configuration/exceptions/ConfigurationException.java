package io.github.ninjaenterprise.configuration.exceptions;

/**
 * Errors caused in the configuration service.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
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