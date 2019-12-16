

package com.test.jetty;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BlockingServlet
 */
public class BlockingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SNMPHandler snmpHandler = new SNMPHandler();
        String resp = snmpHandler.snmpGet();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("{ \"response\": \""+resp+"\"}");
    }
    
}