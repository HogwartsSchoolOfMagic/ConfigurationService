package ru.bangerok.enterprise.template;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A class to run the entire spring application. To run, just use the command -
 * {@code mvn spring-boot:run}.
 *
 * @author Vladislav [Bangerok] Kuznetsov.
 * @since 0.0.0.
 */
@SpringBootApplication
public class TemplateApplication {

  public static void main(String[] args) {
    run(TemplateApplication.class, args);
  }
}