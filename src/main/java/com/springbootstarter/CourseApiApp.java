package com.springbootstarter;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseApiApp {

    // Create and start a server
    public static void main(final String[] args) {
        final SpringApplication app = new SpringApplication(CourseApiApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
