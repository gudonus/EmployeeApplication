package org.sbrf;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.logging.Logger;

public class EmployeeApplication {

    private static final Logger logger = Logger.getLogger(String.valueOf(EmployeeApplication.class));

    public static void main(String[] args) throws Exception {
        System.out.println("Start EmployeeApplication...");

//        org.apache.log4j.BasicConfigurator.configure(new NullAppender());

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("org.sbrf");

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/");

        Server server = new Server(9999);
        server.setHandler(handler);

        server.start();
//        System.out.println("Jetty server started!");
        logger.info("Jetty server started!");
        server.join();

        System.out.println("Stopped EmployeeApplication.");
    }
}
