package io.github.ninjaenterprise.configuration;

import static org.springframework.boot.SpringApplication.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;

/**
 * A class to run the entire spring application. To run, just use the command -
 * {@code mvn spring-boot:run}.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.0.
 */
@Slf4j
@EnableConfigServer
@ComponentScan({"io.github.ninjaenterprise"})
@SpringBootApplication
public class ConfigurationServiceApplication {

  public static void main(String[] args) {
    run(ConfigurationServiceApplication.class, args);
    log.info("ConfigurationService started!!!");
  }
}