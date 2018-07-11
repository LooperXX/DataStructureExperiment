package servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import graph.Graph;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GraphSPathServlet")
public class GraphSPathServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("UTF-8");
        System.out.println("收到景区景点最短路线请求");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String[] path = Graph.servletSPath(start,end).split(",");
        String[] points = path[0].split("->");
        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        res.addProperty("dis",path[1]);
        JsonArray pointsArray = new JsonArray();
        for(String s : points){
            pointsArray.add(s);
        }
        res.addProperty("path", String.valueOf(pointsArray));
        out.print(res);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
