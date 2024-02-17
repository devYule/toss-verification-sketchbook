package my.test.tosssketchbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class TossSketchbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(TossSketchbookApplication.class, args);
    }

}
