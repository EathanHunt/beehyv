package com.beehyv.assignment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
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
@OpenAPIDefinition(info = @Info(
        title = "BODMAS Evaluator",
        description = "This application allows a user to provide an expression containing arithmetic operations '+', '-', '*' & '/'." +
                " The expression can also contain brackets and the evaluated value will consider the operands precedence order as per BODMAS." +
                " A user can also retrieve his most used operand in a particular run."

))
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
