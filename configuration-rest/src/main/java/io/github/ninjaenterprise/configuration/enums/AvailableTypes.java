package io.github.ninjaenterprise.configuration.enums;

import java.util.regex.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Available data types for configuration property values.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@Getter
@RequiredArgsConstructor
public enum AvailableTypes {

  TYPE_INTEGER("Integer", "^\\d+$"),
  TYPE_BOOLEAN("Boolean", "^(?mi)(true|false)$"),
  TYPE_STRING("String", null);

  /**
   * String representation of the data type.
   */
  private final String name;

  /**
   * Pattern for checking the value of a specific data type.
   */
  private final String pattern;

  /**
   * Checking a value for an accessible type.
   *
   * @param value value to check.
   * @return {@code TRUE} if the value is correct, otherwise {@code FALSE}.
   */
  public boolean validated(String value) {
    if (pattern == null) {
      return true;
    }

    return Pattern.compile(pattern).matcher(value).matches();
  }
}