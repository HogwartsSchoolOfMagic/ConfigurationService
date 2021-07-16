package ru.bangerok.enterprise.configuration.dto.service;

import java.util.List;
import ru.bangerok.enterprise.configuration.dto.ConfigurationProperty;
import ru.bangerok.enterprise.configuration.dto.NewConfigurationProperties;
import ru.bangerok.enterprise.configuration.dto.SearchConfigurationProperty;

/**
 * Интерфейс, предоставляющий методы для работы со свойствами конфигурации из провайдера базы
 * данных.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.1.
 */
public interface ConfigurationPropertiesService {

  /**
   * Поиск свойств конфигурации по ключу, описанию и значению.
   *
   * @param searchProperty данные для поиска.
   * @param sortField      поле для сортировки.
   * @param sortType       тип сортировки.
   * @return список найденных свойств конфигурации.
   */
  List<ConfigurationProperty> findAll(SearchConfigurationProperty searchProperty, String sortField,
                                      String sortType);

  /**
   * Создание свойства конфигурации.
   * Не обновляет конфигурацию, если она уже есть!
   *
   * @param configurationProperty данные свойства.
   * @return сохраненная конфигурация, либо текущая
   */
  ConfigurationProperty create(ConfigurationProperty configurationProperty);

  /**
   * Сохранение нескольких свойств конфигурации.
   * Не обновляет конфигурацию, если она уже есть!
   *
   * @param newConfigurationProperties данные свойств.
   * @return сохраненные свойства конфигурации.
   */
  List<ConfigurationProperty> createAll(NewConfigurationProperties newConfigurationProperties);

  /**
   * Сохранение/обновление свойства конфигурации.
   *
   * @param configurationProperty данные свойства.
   * @return сохраненное/обновленное свойство конфигурации.
   */
  ConfigurationProperty save(ConfigurationProperty configurationProperty);

  /**
   * Сохранение/обновление нескольких свойств конфигурации.
   *
   * @param newConfigurationProperties данные свойств.
   * @return сохраненные/обновленные свойства конфигурации.
   */
  List<ConfigurationProperty> saveAll(NewConfigurationProperties newConfigurationProperties);

  /**
   * Удаление существующего свойства конфигурации.
   *
   * @param configurationProperty удаляемое свойство.
   */
  void delete(ConfigurationProperty configurationProperty);
}