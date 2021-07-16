package ru.bangerok.enterprise.configuration.persistance.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.bangerok.enterprise.configuration.persistance.model.ConfigurationPropertyEntity;
import ru.bangerok.enterprise.configuration.persistance.model.ConfigurationPropertyPk;

/**
 * Configuration properties repository.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@Repository
public interface ConfigurationPropertiesRepository
    extends JpaRepository<ConfigurationPropertyEntity, ConfigurationPropertyPk> {

  /**
   * Getting a configuration property by key, description, or value.
   *
   * @param application application.
   * @param profile     profile.
   * @param label       label.
   * @param key         key.
   * @param description description.
   * @param value       value.
   * @param sort        sorting setting.
   * @return configuration property list.
   */
  @Query("""
             from ConfigurationPropertyEntity cpe
             where (:application is null or cpe.id.application like %:application%) and
                   (:profile is null or cpe.id.profile like %:profile%) and
                   (:label is null or cpe.id.label like %:label%) and
                   (:key is null or cpe.id.key like %:key%) and
                   (:description is null or cpe.description like %:description%) and
                   (:value is null or cpe.value like %:value%)
      """
  )
  List<ConfigurationPropertyEntity> findByMultiplyParams(
      String application, String profile, String label, String key, String description,
      String value, Sort sort
  );

  /**
   * Getting a configuration property by a composite key value.
   *
   * @param configurationPropertyPk composite key.
   * @return {@code Optional<Property>} with information about the found configuration property.
   */
  @Override
  @NonNull
  Optional<ConfigurationPropertyEntity> findById(
      @NonNull ConfigurationPropertyPk configurationPropertyPk);
}