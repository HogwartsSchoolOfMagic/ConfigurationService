package ru.bangerok.enterprise.configuration;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * A class to run the entire spring application. To run, just use the command -
 * {@code mvn spring-boot:run}.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.0.
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigurationServerApplication {

  public static void main(String[] args) {
    run(ConfigurationServerApplication.class, args);
  }
}