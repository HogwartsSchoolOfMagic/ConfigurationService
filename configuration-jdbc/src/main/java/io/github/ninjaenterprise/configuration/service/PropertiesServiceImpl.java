package io.github.ninjaenterprise.configuration.service;

import com.querydsl.core.types.Predicate;
import io.github.ninjaenterprise.configuration.dto.PropertyDto;
import io.github.ninjaenterprise.configuration.dto.PropertyReturnDto;
import io.github.ninjaenterprise.configuration.exceptions.ConfigurationErrors;
import io.github.ninjaenterprise.configuration.exceptions.ConfigurationException;
import io.github.ninjaenterprise.configuration.persistance.model.QProperty;
import io.github.ninjaenterprise.configuration.persistance.repository.PropertiesRepository;
import io.github.ninjaenterprise.configuration.service.mapper.PropertiesMapper;
import io.github.ninjaenterprise.configuration.util.YamlPropertySourceFactory;
import io.github.ninjaenterprise.search.model.SearchException;
import io.github.ninjaenterprise.search.model.SearchSettings;
import io.github.ninjaenterprise.search.model.SearchSettingsSimple;
import io.github.ninjaenterprise.search.model.TableResult;
import io.github.ninjaenterprise.search.querydsl.filter.QueryDslFilterService;
import io.github.ninjaenterprise.search.querydsl.paging.PagingService;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Implementing an interface that provides methods for working with the configuration properties
 * repository.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
@PropertySource(
    value = "classpath:configuration-jdbc.yml",
    encoding = "UTF-8",
    factory = YamlPropertySourceFactory.class
)
public class PropertiesServiceImpl implements PropertiesService {

  /**
   * Repository for working with an entity — configuration properties.
   */
  private final PropertiesRepository repository;

  /**
   * Mapper for converting DTOs to and from a property entity.
   */
  private final PropertiesMapper mapper;

  /**
   * Service for getting pagination parameters.
   */
  private final PagingService pagingService;

  /**
   * Service for generating parameters for database queries.
   */
  private final QueryDslFilterService filterService;

  /**
   * Search for configuration properties by filtering options for results and paging.
   *
   * @param filters search data.
   * @return page with list of found configuration properties.
   * @throws ConfigurationException page properties by filters not found.
   */
  @Override
  public TableResult<PropertyReturnDto> getPage(SearchSettings filters)
      throws ConfigurationException {
    var page = filters.getPage();
    page = (page < 1) ? 1 : page;
    var limit = pagingService.getLimit(filters.getItemsPerPage());
    var sortBy = filters.getSortBy();
    final String sort;
    final boolean asc;
    if (!StringUtils.hasText(sortBy)) {
      sort = QProperty.property.created.getMetadata().getName();
      asc = false;
    } else {
      sort = sortBy;
      asc = filters.isAscending();
    }
    var criteriaList = filters.getCriterionList();
    Predicate byCriteria;
    try {
      byCriteria = filterService.getDslPredicate(QProperty.property, criteriaList);
    } catch (SearchException e) {
      throw new ConfigurationException(e.getMessage(), e);
    }
    var pageResult = repository.getPage(byCriteria, sort, asc, page, limit);
    return new TableResult<>(
        mapper.toDto(pageResult.getContent()),
        pageResult.getTotalPages(),
        pageResult.getTotalElements()
    );
  }

  /**
   * Search for configuration properties by filtering options.
   *
   * @param filters search data.
   * @return list of found configuration properties.
   * @throws ConfigurationException properties by filters not found.
   */
  @Override
  public List<PropertyReturnDto> findAll(SearchSettingsSimple filters)
      throws ConfigurationException {
    var criteriaList = filters.getCriterionList();
    Predicate byCriteria;
    try {
      byCriteria = filterService.getDslPredicate(QProperty.property, criteriaList);
    } catch (SearchException e) {
      throw new ConfigurationException(e.getMessage(), e);
    }
    return mapper.toDto(
        StreamSupport.stream(repository.findAll(byCriteria).spliterator(), false)
            .toList()
    );
  }

  /**
   * Create a configuration property if it doesn't already exist with that primary key.
   *
   * @param propertyDto these properties.
   * @return the saved configuration, or the current one.
   * @throws ConfigurationException property creating error.
   */
  @Override
  public PropertyReturnDto create(PropertyDto propertyDto) throws ConfigurationException {
    var property = mapper.toEntity(propertyDto);
    var byProp = repository.findByKey(property.getCompoundId());
    if (byProp.isPresent()) {
      throw new ConfigurationException(
          String.format(ConfigurationErrors.PROPERTY_DUPLICATE.getMsg(), propertyDto));
    } else {
      return mapper.toDto(repository.saveAndFlush(property));
    }
  }

  /**
   * Saving/Updating configuration properties.
   *
   * @param propertyDto these properties.
   * @return the saved configuration, or the current one.
   */
  @Override
  public PropertyReturnDto save(PropertyDto propertyDto) {
    var dataToUpdate = mapper.toEntity(propertyDto);
    var byKey = repository.findByKey(dataToUpdate.getCompoundId());
    if (byKey.isPresent()) {
      dataToUpdate = mapper.updateFrom(dataToUpdate, byKey.get());
    }

    return mapper.toDto(repository.saveAndFlush(dataToUpdate));
  }

  /**
   * Removing an existing configuration property by identifier.
   *
   * @param id property identifier.
   * @throws ConfigurationException property delete error by id.
   */
  @Override
  public PropertyReturnDto deleteById(Long id) throws ConfigurationException {
    try {
      var propertyReturnDto = findById(id);
      repository.deleteById(propertyReturnDto.getId());
      return propertyReturnDto;
    } catch (IllegalArgumentException e) {
      throw new ConfigurationException(ConfigurationErrors.PROPERTY_ID_FOR_CRUD_INCORRECT.getMsg(),
          e);
    } catch (ConfigurationException e) {
      throw new ConfigurationException(e.getMessage(), e);
    }
  }

  /**
   * Get configuration property by ID.
   *
   * @param propertyId property identifier.
   * @throws ConfigurationException getting property error by id.
   */
  @Override
  public PropertyReturnDto findById(Long propertyId) throws ConfigurationException {
    return mapper.toDto(repository.findById(propertyId)
        .orElseThrow(() ->
            new ConfigurationException(
                String.format(ConfigurationErrors.PROPERTY_BY_ID_NOT_FOUND.getMsg(), propertyId))
        )
    );
  }
}