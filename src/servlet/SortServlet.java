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

@WebServlet(name = "SortServlet")
public class SortServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("UTF-8");
        String sortBy = request.getParameter("sortBy");
        System.out.println("收到景区景点按" + sortBy +  "排序请求");
        Graph.sortScenicSpotBy(sortBy);
        String[] names= Graph.getSortName();
        JsonArray name = new JsonArray();
        JsonArray welcomeDegree = new JsonArray();
        JsonArray degree = new JsonArray();
        for(int i = 0; i < names.length; i++){
            name.add(names[i]);
            welcomeDegree.add(Graph.getScene().get(names[i]).getWelcome_degree());
            degree.add(Graph.getScene().get(names[i]).getLinked().size());
        }
        JsonObject res = new JsonObject();
        res.addProperty("name", String.valueOf(name));
        res.addProperty("welcomeDegree", String.valueOf(welcomeDegree));
        res.addProperty("degree", String.valueOf(degree));
        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
        out.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
