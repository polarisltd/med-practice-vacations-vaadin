package lv.polarisit.vacationtracker;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@EnableVaadin({"com.vaadin", "org.vaadin", "dev.hilla", "lv.polarisit.vacationtracker.ui.view"})
@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }
}
