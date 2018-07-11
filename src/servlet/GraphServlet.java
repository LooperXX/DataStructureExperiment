package servlet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import graph.Graph;
import graph.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GraphServlet")
public class GraphServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("UTF-8");
            System.out.println("收到景区景点分布图请求");
            PrintWriter out = response.getWriter();
//            String data = "{\"data\":[{";
//            for(int i = 0; i < Graph.num_sceneicSpot; i++){
//                data += "\"name\":\"" + Graph.getName().get(i) + "\"},{";
//            }
//            data = data.substring(0,data.length()-2) + "],";
//            String links = "\"links\":[{";
//            for(String s : Graph.getEdges().keySet()){
//                links += "\"source\":\"" + s + "\",\"target\":\"" + Graph.getEdges().get(s).getPath() + "\"},{";
//            }
//            links = links.substring(0,links.length()-2) + "]}";
            JsonObject res = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            for(int i = 0; i < Graph.num_sceneicSpot; i++){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", Graph.getName().get(i));
                jsonObject.addProperty("description", Graph.getScene().get(Graph.getName().get(i)).getDescription());
                jsonArray.add(jsonObject);
            }
            res.addProperty("data", String.valueOf(jsonArray));
            JsonArray jsonArray1 = new JsonArray();
            for(String s : Graph.getEdges().keySet()){
                for(Path p : Graph.getEdges().get(s)){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("source", s);
                    jsonObject.addProperty("target",p.getPath());
                    jsonObject.addProperty("value",p.getDis());
                    jsonObject.addProperty("description",s + "<->" + p.getPath() + " : " + p.getDis());
                    jsonArray1.add(jsonObject);
                }
            }
            res.addProperty("links", String.valueOf(jsonArray1));
            out.print(res);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(404);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
