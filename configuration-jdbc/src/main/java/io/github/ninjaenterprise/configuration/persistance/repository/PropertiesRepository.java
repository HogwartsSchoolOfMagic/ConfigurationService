package io.github.ninjaenterprise.configuration.persistance.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
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
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Configuration properties repository.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@Repository
public interface PropertiesRepository extends ReactiveCrudRepository<Property, Long>,
    ReactiveQuerydslPredicateExecutor<Property> {

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
  default Flux<Property> getPage(Predicate predicate, String sortBy, Boolean descending,
                                       long page, int itemsPerPage) {
    return findAll(predicate, QProperty.property.created.desc())
        .skip(page * itemsPerPage)
        .take(itemsPerPage);
  }

  /**
   * Search for configuration by composite key (application + profile + label + key).
   * Values included in the key can be empty, but cannot be NULL.
   *
   * @param key configuration composite key.
   * @return configuration, if found.
   */
  default Mono<Property> findByKey(@NotNull PropertyCk key) {
    var property = QProperty.property;
    var predicate = property.compoundId.application.eq(key.getApplication())
        .and(property.compoundId.profile.eq(key.getProfile()))
        .and(property.compoundId.label.eq(key.getLabel()))
        .and(property.compoundId.key.eq(key.getKey()));
    return findOne(predicate);
  }
}