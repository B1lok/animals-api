package com.example.animalsapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "Animals API", version = "0.1",
        license = @License(name = "Apache-2.0 license",
            url = "https://www.apache.org/licenses/LICENSE-2.0"),
        description = """
            An API for the application implementing abilities to upload and animals data"""
    )
)
public class AnimalsApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(AnimalsApiApplication.class, args);
  }

}
