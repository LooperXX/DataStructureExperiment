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
import java.util.ArrayList;

@WebServlet(name = "SPathServlet")
public class SPathServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("UTF-8");
        String start = request.getParameter("start");
        System.out.println("收到景区导游路线请求: " + start);
        Graph.tsp(start);
        String[] path = Graph.getTspPath();
        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        res.addProperty("dis",Graph.getSPathDis());
        JsonArray pointsArray = new JsonArray();
        for(String s : path){
            pointsArray.add(s);
        }
        res.addProperty("path", String.valueOf(pointsArray));
        System.out.println(res);
        out.print(res);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
