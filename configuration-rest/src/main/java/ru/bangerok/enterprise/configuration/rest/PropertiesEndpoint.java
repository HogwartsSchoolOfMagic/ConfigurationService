package ru.bangerok.enterprise.configuration.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bangerok.enterprise.configuration.dto.NewPropertiesDto;
import ru.bangerok.enterprise.configuration.dto.PropertyDto;
import ru.bangerok.enterprise.configuration.dto.SearchPropertyDto;
import ru.bangerok.enterprise.configuration.service.PropertiesService;

/**
 * REST is a controller for CRUD operations with configuration properties.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 1.0.0.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class PropertiesEndpoint implements ConfigurationClient {

  /**
   * The name of the configuration service for Spring Cloud.
   */
  @Value("${spring.application.name}")
  private String applicationName;

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
   * Publisher for events that update configuration properties on clients.
   */
  private final ApplicationEventPublisher publisher;

  /**
   * Search configuration properties by key, description or value.
   *
   * @param searchPropertyDto data for searching configuration properties.
   * @param sortField         field for sorting properties (for the first four fields
   *                          mentioned above — you need to use the 'id' prefix).
   * @param sortType          sort type: {@code 'asc' or 'desc'}.
   * @return list of found configuration properties.
   */
  @Override
  public List<PropertyDto> findAll(
      @RequestBody SearchPropertyDto searchPropertyDto,
      @RequestParam(required = false, defaultValue = "id.application") String sortField,
      @RequestParam(required = false, defaultValue = "asc") String sortType) {
    return service.findAll(searchPropertyDto, sortField, sortType);
  }

  /**
   * Create configuration properties for individual application, profile and label.
   *
   * @param newPropertiesDtoData configuration properties data.
   * @return list of generated configuration properties.
   */
  @Override
  public List<PropertyDto> createAll(NewPropertiesDto newPropertiesDtoData) {
    final List<PropertyDto> savedProperties = service.createAll(newPropertiesDtoData);
    refreshCloudConfigs();
    return savedProperties;
  }

  /**
   * Saving configuration properties for an individual application, profile and label.
   *
   * @param newPropertiesDtoData configuration properties data.
   * @return list of saved configuration properties.
   */
  @Override
  public List<PropertyDto> saveAll(
      @RequestBody NewPropertiesDto newPropertiesDtoData) {
    final List<PropertyDto> savedProperties = service.saveAll(newPropertiesDtoData);
    refreshCloudConfigs();
    return savedProperties;
  }

  /**
   * Creating a configuration property.
   *
   * @param propertyDto these properties.
   * @return the created config property.
   */
  @Override
  public PropertyDto create(PropertyDto propertyDto) {
    final var created = service.create(propertyDto);
    refreshCloudConfigs();
    return created;
  }

  /**
   * Saving the configuration property.
   *
   * @param propertyDto these properties.
   * @return saved configuration property.
   */
  @Override
  public PropertyDto save(@RequestBody PropertyDto propertyDto) {
    final var savedProperty = service.save(propertyDto);
    refreshCloudConfigs();
    return savedProperty;
  }

  /**
   * Deleting a configuration property.
   *
   * @param propertyDto удаляемое свойство.
   */
  @Override
  public void delete(@RequestBody PropertyDto propertyDto) {
    service.delete(propertyDto);
    refreshCloudConfigs();
  }

  /**
   * Forced update of configurations.
   */
  @Override
  public void refresh() {
    refreshCloudConfigs();
  }

  /**
   * Updating configuration properties on clients.
   */
  private void refreshCloudConfigs() {
    String instanceId = busProperties.getId();
    var event = new RefreshRemoteApplicationEvent(applicationName, instanceId, () -> "*:**");
    publisher.publishEvent(event);
  }
}