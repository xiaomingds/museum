package com.example.museum;



import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@EnableSwaggerBootstrapUI
@SpringBootApplication
@ServletComponentScan
public class MuseumApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MuseumApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MuseumApplication.class);
    }
}
