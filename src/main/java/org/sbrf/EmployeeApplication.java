package org.sbrf;

import org.apache.log4j.varia.NullAppender;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class EmployeeApplication {
    public static void main(String[] args) throws Exception {
        System.out.println("Start EmployeeApplication...");

        org.apache.log4j.BasicConfigurator.configure(new NullAppender());

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("org.sbrf");

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(new ServletHolder(new DispatcherServlet(context)), "/");

        Server server = new Server(9999);
        server.setHandler(handler);

        server.start();
        System.out.println("Jetty server started!");
        server.join();

        System.out.println("Stopped EmployeeApplication.");
    }
}
