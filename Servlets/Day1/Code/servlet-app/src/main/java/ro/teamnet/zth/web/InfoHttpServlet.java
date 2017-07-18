package ro.teamnet.zth.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by Ovidiu.Busuioc on 7/18/2017.
 */
public class InfoHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        String headerCreate = "<table border=\"1\"><tr>";
        Enumeration<String> enumeration = req.getHeaderNames();
        while(enumeration.hasMoreElements()){
            headerCreate+="<th>"+enumeration.nextElement()+"</th>";
        }
//        headerCreate=headerCreate+"</tr><tr>";
//        while(req.getHeaders().hasMoreElements()){
//            headerCreate+="<td>"+req.getHeaders().nextElement()+"</td>";
//        }
        headerCreate+="</tr></table>";


        resp.getWriter().write(headerCreate);
        resp.getWriter().write("\n");
        resp.getWriter().write(req.getMethod());
        resp.getWriter().write("\n");
        resp.getWriter().write(req.getQueryString());

    }
}
