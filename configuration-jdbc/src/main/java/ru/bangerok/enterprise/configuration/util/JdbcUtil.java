package ru.bangerok.enterprise.configuration.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

/**
 * Util class for working database.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JdbcUtil {

  /**
   * Getting sort settings.
   *
   * @param sortField sorting field.
   * @param sortType  sorting type.
   * @return sorting settings.
   */
  public static Sort getOrder(String sortField, String sortType) {
    final var direction = Sort.Direction.valueOf(sortType.toUpperCase());
    return Sort.by(direction, sortField);
  }
}