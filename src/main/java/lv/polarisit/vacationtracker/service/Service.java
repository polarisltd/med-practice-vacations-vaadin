package lv.polarisit.vacationtracker.service;

import com.vaadin.flow.server.VaadinServlet;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Service
{
    public static <T> T get(Class<T> serviceType)
    {
        return WebApplicationContextUtils
                .getWebApplicationContext(VaadinServlet.getCurrent().getServletContext())
                .getBean(serviceType);
    }
}
