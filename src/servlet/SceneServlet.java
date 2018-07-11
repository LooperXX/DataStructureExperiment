package servlet;

import com.google.gson.Gson;
import graph.Graph;
import netscape.javascript.JSObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SceneServlet")
public class SceneServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    	    response.setCharacterEncoding("UTF-8");
    	    response.setContentType("UTF-8");
    	    String sceneName = request.getParameter("sceneName");
    		System.out.println("收到景点信息查询请求:" + sceneName);
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            Map<String,String> resMap = Graph.searchScenicSpot(sceneName);
            if(resMap == null){
                resMap = new HashMap<>();
                String res = "";
                for(int i = 0; i < 5; i++){
                    res += "查询失败,";
                }
                res = res.substring(0,res.length() - 1);
                resMap.put(sceneName,res);
            }
            out.print(gson.toJson(resMap));
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
