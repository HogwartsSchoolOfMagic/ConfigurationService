package io.github.ninjaenterprise.configuration.persistance.repository;

import com.querydsl.core.types.Predicate;
import io.github.ninjaenterprise.configuration.persistance.model.Property;
import io.github.ninjaenterprise.configuration.persistance.model.PropertyCk;
import io.github.ninjaenterprise.configuration.persistance.model.QProperty;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * Configuration properties repository.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@Repository
public interface PropertiesRepository extends JpaRepository<Property, Long>,
    QuerydslPredicateExecutor<Property> {

  /**
   * Getting a page-by-page selection of configurations.
   *
   * @param predicate    sampling condition.
   * @param sortBy       sort by field.
   * @param descending   sorting direction.
   * @param page         page number
   * @param itemsPerPage the number of items on the page.
   * @return sample with found configurations.
   */
  default Page<Property> getPage(Predicate predicate, String sortBy, Boolean descending,
                                 long page, int itemsPerPage) {
    final List<Sort.Order> orders;
    if (!StringUtils.hasText(sortBy)) {
      var defaultSort = QProperty.property.created.getMetadata().getName();
      orders = List.of(Sort.Order.desc(defaultSort));
    } else if ((descending == null) || Boolean.TRUE.equals(descending)) {
      orders = List.of(Sort.Order.desc(sortBy));
    } else {
      orders = List.of(Sort.Order.asc(sortBy));
    }

    return findAll(
        predicate,
        PageRequest.of(Math.toIntExact(page - 1), itemsPerPage, Sort.by(orders))
    );
  }

  /**
   * Search for configuration by composite key (application + profile + label + key).
   * Values included in the key can be empty, but cannot be NULL.
   *
   * @param key configuration composite key.
   * @return configuration, if found.
   */
  default Optional<Property> findByKey(@NotNull PropertyCk key) {
    var property = QProperty.property;
    var predicate = property.compoundId.application.eq(key.getApplication())
        .and(property.compoundId.profile.eq(key.getProfile()))
        .and(property.compoundId.label.eq(key.getLabel()))
        .and(property.compoundId.key.eq(key.getKey()));
    return findOne(predicate);
  }
}