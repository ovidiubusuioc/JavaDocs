package ro.teamnet.zth.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Ovidiu.Busuioc on 7/19/2017.
 */
public class HttpSessionLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = null;
        Cookie[] cookies = request.getCookies();
        if (user.equals("admin") && password.equals("admin")) {
            response.getWriter().write("Welcome back " + user);

            response.getWriter().write(request.getRequestedSessionId());
            for (Cookie i : cookies) {
                response.getWriter().write(i.toString());
            }
        }
        else{
                session.setAttribute("session", request.getSession());
                session.setAttribute("user", user);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/loginFail.jsp");
                requestDispatcher.forward(request,response);

            }

        }


}
