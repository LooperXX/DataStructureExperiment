package servlet;

import Bean.Config;
import graph.Edge;
import graph.Graph;
import graph.menu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "ManageServlet")
public class ManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("UTF-8");
        String type = request.getParameter("type");
        String input = request.getParameter("input");
        System.out.println("收到管理景点请求: " + type);
        String res = "ok";
        switch (type){
            case "addS":
                System.out.println("input" + input);
                String[] op = input.split(";");
                if(op.length != 3){
                    res = "no";
                    System.out.println("[Wrong]您的输入有误:<");
                }else{
                    String[] add = op[0].split(",");
                    int num = Integer.parseInt(add[1]);
                    System.out.println("num: " + num);
                    Edge[] linkedEdge = new Edge[num];
                    String[] linked = new String[num];
                    for(int i = 1; i < 3; i++){
                        String[] temp = op[i].split(",");
                        linked[i-1] = temp[0];
                        linkedEdge[i-1] = new Edge(Integer.parseInt(temp[1]));
                    }
                    Graph.addScenicSpot(add[0],linked,linkedEdge);
                    System.out.println("[Success]" + type + ":>");
                }
                break;
            case "addE":
                String[] op2 = input.split(",");
                if(Graph.name.contains(op2[0]) && Graph.name.contains(op2[1])){
                    Graph.addEdge(op2);
                    System.out.println("[Success]" + type + ":>");
                }else{
                    System.out.println("[Wrong]您的输入有误:<");
                    res = "no";
                }
                break;
            case "deleteS":
                if(!Graph.name.contains(input)){
                    System.out.println("[Wrong]您输入的相邻节点不在本景区中,操作失败:<");
                    res = "no";
                }else {
                    Graph.deleteScenicSpot(input);
                    System.out.println("[Success]" + type + ":>");
                }
                break;
            case "deleteE":
                String[] op4 = input.split(",");
                if(Graph.name.contains(op4[0]) && Graph.name.contains(op4[1])){
                    if(Graph.getScene().get(op4[0]).getLinked().containsKey(op4[1])){
                        Graph.deleteEdge(op4);
                        System.out.println("[Success]" + type + ":>");
                    }else{
                        res = "no";
                        System.out.println("[Wrong]您的输入有误:<");
                    }
                }else{
                    res = "no";
                    System.out.println("[Wrong]您的输入有误:<");
                }
                break;
            case "recoverS":
                if(!Graph.getSceneRecover().containsKey(input)){
                    System.out.println("[Wrong]您输入的节点不在待恢复景点列表中,操作失败:<");
                    res = "no";
                }else {
                    Graph.recoverScenicSpot(input);
                    System.out.println("[Success]" + type + ":>");
                }
                break;
            case "recoverE":
                String[] op6 = input.split(",");
                if(Graph.getPathRecover().size() == 0){
                    System.out.println("[Wrong]无待恢复道路:<");
                    res = "no";
                    break;
                }
                if(!Graph.getPathRecover().containsKey(op6[0]) && !Graph.getPathRecover().containsKey(op6[1])){
                    res = "no";
                    System.out.println("[Wrong]您输入的节点不在待恢复道路列表中,操作失败:<");
                }else{
                    Graph.recoverEdge(op6);
                    System.out.println("[Success]" + type + ":>");
                }
                break;
            case "message":
                menu.setMessage(input);
                break;
            default:
                res = "no";
        }
        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
