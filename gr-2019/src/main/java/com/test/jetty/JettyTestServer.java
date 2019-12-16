
package com.test.jetty;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * JettyTestServer
 */
public class JettyTestServer {
    private Server server;

    public void start() throws Exception {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.setConnectors(new Connector[]{connector});

        ResourceHandler resourceHandler = new ResourceHandler();
        ServletHandler handler = new ServletHandler();

        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(".");
        handler.addServletWithMapping(BlockingServlet.class, "/hello");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, handler});

        server.setHandler(handlerList);
        server.start();
    }

    public static void main(String[] args) throws Exception {
        JettyTestServer jettyTestServer = new JettyTestServer();
        jettyTestServer.start();
    }

}