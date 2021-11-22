package io.github.ninjaenterprise.configuration.controller;

import static io.github.ninjaenterprise.configuration.util.PropertiesUtils.checkingValue;

import io.github.ninjaenterprise.configuration.dto.PropertyDto;
import io.github.ninjaenterprise.configuration.dto.PropertyReturnDto;
import io.github.ninjaenterprise.configuration.exceptions.ConfigurationException;
import io.github.ninjaenterprise.configuration.service.PropertiesService;
import io.github.ninjaenterprise.search.model.SearchSettings;
import io.github.ninjaenterprise.search.model.SearchSettingsSimple;
import io.github.ninjaenterprise.search.model.TableResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.Destination.Factory;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST is a controller for CRUD operations with configuration properties.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@Slf4j
@RequiredArgsConstructor
@RefreshScope
@RestController
public class PropertiesController implements ConfigurationClient {

  /**
   * Service for working with the external module of the database provider.
   */
  private final PropertiesService service;
  /**
   * Bus properties for the configuration of the update event of the configuration properties on
   * clients.
   */
  private final BusProperties busProperties;
  /**
   * Factory for receiving target services for updating configurations.
   */
  private final Factory destinationFactory;
  /**
   * Factory for receiving target services for updating configurations.
   */
  private final DiscoveryClient discoveryClient;
  /**
   * Publisher for events that update configuration properties on clients.
   */
  private final ApplicationEventPublisher publisher;
  /**
   * The name of the configuration service for Spring Cloud.
   */
  @Value("${spring.application.name}")
  private String applicationName;

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
    return service.getPage(filters);
  }

  /**
   * Create a configuration property if it doesn't already exist with that primary key.
   *
   * @param propertyDto these properties.
   * @param refresh     flag refresh properties.
   * @return created configuration, or the current one.
   * @throws ConfigurationException property creating error.
   */
  @Override
  public PropertyReturnDto create(
      @RequestBody @Valid PropertyDto propertyDto,
      @RequestParam(defaultValue = "false", required = false) boolean refresh
  ) throws ConfigurationException {
    var createdProperty = saveOrCreateProperty(propertyDto, true);
    refreshCloudConfigs(Collections.singletonList(createdProperty.getApplication()), refresh);
    return createdProperty;
  }

  /**
   * Create a configuration properties if it doesn't already exist with that primary key.
   *
   * @param propertyDtoList these properties.
   * @param refresh         flag refresh properties.
   * @return created configurations.
   * @throws ConfigurationException properties creating error.
   */
  @Override
  public List<PropertyReturnDto> createAll(
      @RequestBody @Valid List<PropertyDto> propertyDtoList,
      @RequestParam(defaultValue = "false", required = false) boolean refresh
  ) throws ConfigurationException {
    var createdProperties = new ArrayList<PropertyReturnDto>();
    for (var property : propertyDtoList) {
      createdProperties.add(saveOrCreateProperty(property, true));
    }

    if (refresh) {
      refreshCloudConfigs(
          createdProperties.stream().map(PropertyReturnDto::getApplication).toList(),
          true
      );
    }

    return createdProperties;
  }

  /**
   * Get configuration property by ID.
   *
   * @param propertyId property identifier.
   * @return configuration property.
   * @throws ConfigurationException getting property error by id.
   */
  @Override
  public PropertyReturnDto getById(Long propertyId) throws ConfigurationException {
    return service.findById(propertyId);
  }

  /**
   * Saving/Updating configuration properties.
   *
   * @param propertyDto these properties.
   * @param refresh     flag refresh properties.
   * @return saved configuration, or the current one.
   * @throws ConfigurationException property saving error.
   */
  @Override
  public PropertyReturnDto save(
      @RequestBody @Valid PropertyDto propertyDto,
      @RequestParam(defaultValue = "false", required = false) boolean refresh
  ) throws ConfigurationException {
    var savedProperty = saveOrCreateProperty(propertyDto, false);
    refreshCloudConfigs(Collections.singletonList(savedProperty.getApplication()), refresh);
    return savedProperty;
  }

  /**
   * Saving configuration properties for an individual application, profile, and label.
   *
   * @param propertyDtoList configuration properties data.
   * @param refresh         flag refresh properties.
   * @return list of saved configuration properties.
   * @throws ConfigurationException properties saving error.
   */
  @Override
  public List<PropertyReturnDto> saveAll(
      @RequestBody @Valid List<PropertyDto> propertyDtoList,
      @RequestParam(defaultValue = "false", required = false) boolean refresh
  ) throws ConfigurationException {
    var updatedProperties = new ArrayList<PropertyReturnDto>();
    for (var propertyDto : propertyDtoList) {
      updatedProperties.add(saveOrCreateProperty(propertyDto, false));
    }

    if (refresh) {
      refreshCloudConfigs(
          updatedProperties.stream().map(PropertyReturnDto::getApplication).toList(),
          true
      );
    }

    return updatedProperties;
  }

  /**
   * Removing configuration properties.
   *
   * @param filters filtering options for properties to be removed.
   * @param refresh flag refresh properties.
   * @return list of removed configuration properties.
   * @throws ConfigurationException configuration properties deletion errors.
   */
  @Override
  public List<PropertyReturnDto> deleteAll(
      @RequestBody SearchSettingsSimple filters,
      @RequestParam(defaultValue = "false", required = false) boolean refresh
  ) throws ConfigurationException {
    var deletedProperties = new ArrayList<PropertyReturnDto>();
    for (var property : service.findAll(filters)) {
      deletedProperties.add(service.deleteById(property.getId()));
    }

    if (refresh) {
      refreshCloudConfigs(
          deletedProperties.stream().map(PropertyReturnDto::getApplication).toList(),
          true
      );
    }

    return deletedProperties;
  }

  /**
   * Removing an existing configuration property by identifier.
   *
   * @param propertyId property identifier.
   * @param refresh    flag refresh properties.
   * @return deleted configuration property.
   * @throws ConfigurationException property delete error by id.
   */
  @Override
  public PropertyReturnDto deleteById(
      @PathVariable Long propertyId,
      @RequestParam(defaultValue = "false", required = false) boolean refresh
  ) throws ConfigurationException {
    var deletedProperty = service.deleteById(propertyId);
    refreshCloudConfigs(Collections.singletonList(deletedProperty.getApplication()), refresh);
    return deletedProperty;
  }

  /**
   * Forced update of configurations in applications.
   *
   * @param application application name for refresh.
   */
  @Override
  public void refresh(@PathVariable String application) {
    refreshCloudConfigs(Collections.singletonList(application), true);
  }

  /**
   * Updating configuration properties on clients.
   *
   * @param destinations applications name for refresh.
   * @param refresh      flag refreshing properties.
   */
  private void refreshCloudConfigs(List<String> destinations, boolean refresh) {
    if (refresh) {
      destinations.remove(applicationName);
      var destination = StringUtils.arrayToDelimitedString(
          discoveryClient.getServices().stream()
              .filter(destinations::contains)
              .toArray(),
          ":");

      if (!destination.isEmpty()) {
        publisher.publishEvent(new RefreshRemoteApplicationEvent(
            applicationName,
            busProperties.getId(),
            destinationFactory.getDestination(destination)
        ));
      }
    }
  }

  /**
   * Saving/Updating configuration properties.
   *
   * @param propertyDto configuration property data.
   * @param isCreate    config property create/update flag.
   * @return saved configuration property.
   */
  private PropertyReturnDto saveOrCreateProperty(PropertyDto propertyDto, boolean isCreate)
      throws ConfigurationException {
    checkingValue(propertyDto);
    return isCreate ? service.create(propertyDto) : service.save(propertyDto);
  }
}