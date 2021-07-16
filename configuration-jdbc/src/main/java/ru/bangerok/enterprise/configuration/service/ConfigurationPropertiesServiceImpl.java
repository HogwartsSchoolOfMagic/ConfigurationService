package ru.bangerok.enterprise.configuration.service;

import static ru.bangerok.enterprise.configuration.util.JdbcUtil.getOrder;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bangerok.enterprise.configuration.dto.ConfigurationProperty;
import ru.bangerok.enterprise.configuration.dto.NewConfigurationProperties;
import ru.bangerok.enterprise.configuration.dto.SearchConfigurationProperty;
import ru.bangerok.enterprise.configuration.dto.service.ConfigurationPropertiesService;
import ru.bangerok.enterprise.configuration.persistance.repository.ConfigurationPropertiesRepository;
import ru.bangerok.enterprise.configuration.service.mapper.PropertiesMapper;

/**
 * Implementing an interface that provides methods for working with the configuration properties
 * repository.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
@RequiredArgsConstructor
@Service
@Transactional
@PropertySource(value = "classpath:configuration-provider.properties", encoding = "UTF-8")
public class ConfigurationPropertiesServiceImpl implements ConfigurationPropertiesService {

  private final ConfigurationPropertiesRepository repository;
  private final PropertiesMapper mapper;

  @Override
  public List<ConfigurationProperty> findAll(
      SearchConfigurationProperty searchProperty, String sortField, String sortType
  ) {
    return mapper.toDto(repository.findByMultiplyParams(
        searchProperty.getApplication(),
        searchProperty.getProfile(),
        searchProperty.getLabel(),
        searchProperty.getKey(),
        searchProperty.getDescription(),
        searchProperty.getValue(),
        getOrder(sortField, sortType)
    ));
  }

  @Override
  public ConfigurationProperty create(ConfigurationProperty configurationProperty) {
    var configurationPropertyEntity = mapper.toEntity(configurationProperty);
    var id = configurationPropertyEntity.getId();
    var byId = repository.findById(id);
    if (byId.isPresent()) {
      return mapper.toDto(byId.get());
    }
    return mapper.toDto(repository.save(configurationPropertyEntity));
  }

  @Override
  public List<ConfigurationProperty> createAll(
      NewConfigurationProperties newConfigurationProperties) {
    List<ConfigurationProperty> configurationPropertyEntities =
        mapper.toDto(newConfigurationProperties);
    return configurationPropertyEntities.stream().map(this::create).collect(Collectors.toList());
  }

  @Override
  public ConfigurationProperty save(ConfigurationProperty configurationProperty) {
    var configurationPropertyEntity = mapper.toEntity(configurationProperty);
    return mapper.toDto(repository.save(configurationPropertyEntity));
  }

  @Override
  public List<ConfigurationProperty> saveAll(
      NewConfigurationProperties newConfigurationProperties) {
    List<ConfigurationProperty> configurationPropertyEntities =
        mapper.toDto(newConfigurationProperties);
    return configurationPropertyEntities.stream().map(this::save).collect(Collectors.toList());
  }

  @Override
  public void delete(ConfigurationProperty configurationProperty) {
    repository.deleteById(mapper.toEntity(configurationProperty).getId());
  }
}