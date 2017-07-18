/**
 * HelloWorldServlet.java
 * <p>
 * Copyright (c) 2014 Teamnet. All Rights Reserved.
 * <p>
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet.
 **/

package ro.teamnet.zth.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Hello World Servlet expose get method to say hello to input user
 */
public class HelloWorldServlet extends HttpServlet {

    /**
     * This method is used to map a GET request from the client side
     * @param request the HttpServletRequest instance
     * @param response the HttpServletResponse instance
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = "";

        // Set the response type
        response.setContentType("text/html");

        // Obtain the username from the request instance
        user = request.getParameter("user");


        // TODO Write the response content to the PrintWriter instance of the response instance
        response.getWriter().write("Hello, " + user);

        // TIP: use write() method
        // TIP: you can also use html tags to markup your text


    }

}
