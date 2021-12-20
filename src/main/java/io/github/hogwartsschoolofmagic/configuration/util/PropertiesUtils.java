package io.github.hogwartsschoolofmagic.configuration.util;

import io.github.hogwartsschoolofmagic.configuration.dto.PropertyDto;
import io.github.hogwartsschoolofmagic.configuration.enums.AvailableTypes;
import io.github.hogwartsschoolofmagic.configuration.exceptions.ConfigurationErrors;
import io.github.hogwartsschoolofmagic.configuration.exceptions.ConfigurationException;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class for performing side actions, for example, for validating configuration properties.
 *
 * @author Vladislav [SmithyVL] Kuznetsov.
 * @since 1.0.0.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesUtils {

  /**
   * Checking the value of a configuration property.
   *
   * @param propertyDto configuration property data.
   * @throws ConfigurationException error of matching value with available types and formats.
   */
  public static void checkingValue(@Valid PropertyDto propertyDto) throws ConfigurationException {
    final var value = propertyDto.getValue();
    final var typeStringValue = propertyDto.getType();
    if (typeStringValue != null) {
      final AvailableTypes typeValue = isAvailableType(typeStringValue);
      if (typeValue == null) {
        throw new ConfigurationException(
            String.format(ConfigurationErrors.PROPERTY_TYPE_NOT_AVAILABLE.getMsg(), value,
                typeStringValue)
        );
      }

      if (!typeValue.validated(value)) {
        throw new ConfigurationException(
            String.format(ConfigurationErrors.PROPERTY_INCORRECT_TYPE.getMsg(), value,
                typeStringValue)
        );
      }

      var pattern = propertyDto.getPattern();
      if (pattern != null && !pattern.trim().isEmpty() && !propertyPatternCheck(propertyDto)) {
        throw new ConfigurationException(
            String.format(ConfigurationErrors.PROPERTY_INCORRECT_FORMAT.getMsg(), value, pattern)
        );
      }
    }
  }

  /**
   * Checking the type for accessibility.
   *
   * @param type configuration property type.
   * @return {@code TRUE} if the type exists to store, otherwise {@code FALSE}.
   */
  private static AvailableTypes isAvailableType(String type) {
    return Arrays.stream(AvailableTypes.values())
        .filter(t -> t.getName().equals(type))
        .findAny()
        .orElse(null);
  }

  /**
   * Checking a value against a pattern.
   *
   * @param configurationProperty configuration property data.
   * @return {@code TRUE} if pattern validation was successful, otherwise false.
   */
  private static boolean propertyPatternCheck(PropertyDto configurationProperty) {
    var pattern = configurationProperty.getPattern();
    var patternRegex = Pattern.compile(pattern);
    return patternRegex.matcher(configurationProperty.getValue()).matches();
  }
}