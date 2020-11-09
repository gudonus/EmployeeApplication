package org.sbrf;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class EmployeeApplication {

    private static final Logger logger = Logger.getLogger(EmployeeApplication.class);

    public static void main(String[] args) {

        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");

        try {
            startServer(setHandler());
        } catch (Exception exception) {
            logger.error("On start application exception: " + exception);
        }
    }

    private static ServletContextHandler setHandler() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("org.sbrf");

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/");

        return handler;
    }

    private static void startServer(ServletContextHandler handler) throws Exception {
        Server server = new Server(9999);
        server.setHandler(handler);

        server.start();
        logger.info("Jetty server started!");
        server.join();
    }
}
