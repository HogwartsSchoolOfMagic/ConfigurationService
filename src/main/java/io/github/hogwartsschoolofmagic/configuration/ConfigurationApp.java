package io.github.hogwartsschoolofmagic.configuration;

import static java.lang.System.nanoTime;
import static org.springframework.boot.SpringApplication.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;

/**
 * A class to run the entire spring application. To run, just use the command -
 * {@code mvn spring-boot:run}.
 *
 * @author Vladislav [SmithyVL] Kuznetsov.
 * @since 0.0.1.
 */
@Slf4j
@EnableConfigServer
@ComponentScan({"io.github.hogwartsschoolofmagic"})
@SpringBootApplication
public class ConfigurationApp {
  /**
   * The Main method that launches the Spring context of the application - and outputs the duration
   * of the launch to the log.
   *
   * @param args launch arguments.
   */
  public static void main(String[] args) {
    var startTime = nanoTime();
    run(ConfigurationApp.class, args);
    log.info("ConfigurationApplication started for {} ms!", nanoTime() - startTime);
  }
}