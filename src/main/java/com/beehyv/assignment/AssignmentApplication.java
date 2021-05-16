package com.beehyv.assignment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class AssignmentApplication {

    @Value("${springdoc.swagger-ui.path}")
    private String swaggerEndPoint;

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }


    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
        Logger logger = Logger.getLogger(AssignmentApplication.class.getName());
        logger.log(Level.INFO, "Application started. Launching browser now");
        browse("http://localhost:8080" + swaggerEndPoint);
    }

    public void browse(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
