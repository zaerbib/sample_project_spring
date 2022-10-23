package com.apress.calendar.web.configuration;

import com.apress.calendar.configuration.JavaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory
            .getLogger(WebAppInitializer.class);

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { JavaConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebMvcConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" }; // or *.html
    }

    @Override
    public void onStartup(final ServletContext servletContext)
            throws ServletException {

        // Register DispatcherServlet
        super.onStartup(servletContext);

        // Register H2 Admin console:
        ServletRegistration.Dynamic h2WebServlet = servletContext.addServlet("h2WebServlet",
                new org.h2.server.web.WebServlet());
        h2WebServlet.addMapping("/admin/h2/*");
        h2WebServlet.setInitParameter("webAllowOthers", "true");

    }
}
