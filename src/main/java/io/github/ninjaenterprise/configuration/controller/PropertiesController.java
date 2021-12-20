package io.github.ninjaenterprise.configuration.controller;

import static io.github.ninjaenterprise.configuration.util.PropertiesUtils.checkingValue;
import static org.springframework.util.StringUtils.arrayToDelimitedString;

import io.github.ninjaenterprise.configuration.dto.PropertyDto;
import io.github.ninjaenterprise.configuration.dto.PropertyReturnDto;
import io.github.ninjaenterprise.configuration.exceptions.ConfigurationException;
import io.github.ninjaenterprise.configuration.service.PropertiesService;
import io.github.ninjaenterprise.search.model.SearchSettings;
import io.github.ninjaenterprise.search.model.SearchSettingsSimple;
import io.github.ninjaenterprise.search.model.TableResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.event.Destination.Factory;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/api/configurations")
@Tag(name = "PropertiesController", description = "API for working with application configurations")
public class PropertiesController {

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
  @Operation(
      summary = "Search for configuration properties by filtering options for results and paging"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Configuration properties found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PropertyReturnDto[].class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Missing or invalid request parameters",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Invalid request parameters",
          content = @Content
      )
  })
  @PostMapping("/page")
  public TableResult<PropertyReturnDto> getPage(
      @Parameter(description = "Search filters", required = true)
      @RequestBody SearchSettings filters
  ) throws ConfigurationException {
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
  @Operation(
      summary = "Create a configuration property if it doesn't already exist with that primary key"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Configuration property created",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PropertyReturnDto.class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Missing or invalid request parameters",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Attempting to create a duplicate",
          content = @Content
      )
  })
  @PostMapping
  public PropertyReturnDto create(
      @Parameter(
          description = "Information about the generated configuration property",
          required = true
      )
      @RequestBody @Valid PropertyDto propertyDto,
      @Parameter(description = "Client configuration properties update flag")
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
  @Operation(
      summary = "Create a configuration properties if it doesn't already exist with that "
          + "primary key"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Configuration properties created",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PropertyReturnDto.class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Missing or invalid request parameters",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Attempting to create a duplicate",
          content = @Content
      )
  })
  @PostMapping("/batch")
  public List<PropertyReturnDto> createAll(
      @Parameter(
          description = "Information about the generated configuration properties",
          required = true
      )
      @RequestBody @Valid List<PropertyDto> propertyDtoList,
      @Parameter(description = "Client configuration properties update flag")
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
  @Operation(summary = "Get configuration property by ID")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Configuration property found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PropertyReturnDto.class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Missing or invalid request parameters",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Configuration property not found",
          content = @Content
      )
  })
  @GetMapping("/{propertyId}")
  public PropertyReturnDto getById(
      @Parameter(
          description = "Identifier to search for the configuration property",
          required = true
      )
      @PathVariable Long propertyId
  ) throws ConfigurationException {
    return service.findById(propertyId);
  }

  /**
   * Saving/Updating configuration property.
   *
   * @param propertyDto these properties.
   * @param refresh     flag refresh properties.
   * @return saved configuration, or the current one.
   * @throws ConfigurationException property saving error.
   */
  @Operation(summary = "Saving/Updating configuration property")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Configuration property saved",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PropertyReturnDto.class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Missing or invalid request parameters",
          content = @Content
      )
  })
  @PutMapping
  public PropertyReturnDto save(
      @Parameter(
          description = "Information about the saved configuration property",
          required = true
      )
      @RequestBody @Valid PropertyDto propertyDto,
      @Parameter(description = "Client configuration properties update flag")
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
  @Operation(summary = "Saving/Updating configuration property")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Configuration properties saved",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PropertyReturnDto[].class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Missing or invalid request parameters",
          content = @Content
      )
  })
  @PutMapping("/batch")
  public List<PropertyReturnDto> saveAll(
      @Parameter(
          description = "Information about the saved configuration properties",
          required = true
      )
      @RequestBody @Valid List<PropertyDto> propertyDtoList,
      @Parameter(description = "Client configuration properties update flag")
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
  @Operation(summary = "Removing configuration properties")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Configuration properties deleted",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PropertyReturnDto[].class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Missing or invalid request parameters",
          content = @Content
      )
  })
  @DeleteMapping("/batch")
  public List<PropertyReturnDto> deleteAll(
      @Parameter(
          description = "Information about the deleted configuration properties",
          required = true
      )
      @RequestBody SearchSettingsSimple filters,
      @Parameter(description = "Client configuration properties update flag")
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
  @Operation(summary = "Removing an existing configuration property by identifier")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Configuration property deleted",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PropertyReturnDto.class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Missing or invalid request parameters",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Configuration property not found",
          content = @Content
      )
  })
  @DeleteMapping("/{propertyId}")
  public PropertyReturnDto deleteById(
      @Parameter(
          description = "Identifier to search for the configuration property",
          required = true
      )
      @PathVariable Long propertyId,
      @Parameter(description = "Client configuration properties update flag")
      @RequestParam(defaultValue = "false", required = false) boolean refresh
  ) throws ConfigurationException {
    var deletedProperty = service.deleteById(propertyId);
    refreshCloudConfigs(Collections.singletonList(deletedProperty.getApplication()), refresh);
    return deletedProperty;
  }

  /**
   * Forced update of configurations in one application.
   *
   * @param destination target application name for refresh.
   */
  @Operation(summary = "Forced update of configurations in one application")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Destination service configuration properties updated",
          content = @Content
      )
  })
  @PostMapping("/refresh/{destination}")
  public String refresh(
      @Parameter(
          description = "The name of the service whose configuration properties need to be updated"
      )
      @PathVariable String destination
  ) {
    return refreshCloudConfigs(Collections.singletonList(destination), true);
  }

  /**
   * Forced update of configurations in more applications.
   *
   * @param destinations target application names for refresh.
   */
  @Operation(summary = "Forced update of configurations in more applications")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Destination services configuration properties updated",
          content = @Content
      )
  })
  @PostMapping("/refresh")
  public String refreshAll(
      @Parameter(description =
          "The names of the services whose configuration properties need to be updated")
      @RequestBody List<String> destinations
  ) {
    return refreshCloudConfigs(
        destinations.isEmpty()
            ? discoveryClient.getServices()
            : destinations,
        true
    );
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

  /**
   * Updating configuration properties on clients.
   *
   * @param destinations applications name for refresh.
   * @param refresh      flag refreshing properties.
   */
  private String refreshCloudConfigs(List<String> destinations, boolean refresh) {
    String destination = null;
    if (refresh) {
      destination = arrayToDelimitedString(
          discoveryClient.getServices().stream()
              .filter(serviceName ->
                  destinations.contains(serviceName) && !applicationName.equals(serviceName)
              )
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

    return destination;
  }
}