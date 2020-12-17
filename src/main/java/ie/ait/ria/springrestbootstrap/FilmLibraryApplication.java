package ie.ait.ria.springrestbootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FilmLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmLibraryApplication.class, args);
    }

}
