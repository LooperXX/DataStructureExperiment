package graph;

import Bean.AdminBean;
import Bean.Config;
import java.sql.*;
import java.util.Calendar;
import java.util.Scanner;

public class menu {

    private static int StartSystem = 0;

    private static void serviceAddS(){
        System.out.println("----------------------------------------");
        System.out.println("\t请先输入新景点名称及其相邻景点数,再输入相邻的景点及距离,每次输入都需要换行。");
        System.out.println("\t样例如下：");
        System.out.println("\t\t" + "老虎滩,2");
        System.out.println("\t\t" + "北门,9");
        System.out.println("\t\t" + "狮子山,22");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine().trim();
        String[] operation = input.split(",");
        String newNode = operation[0];
        Edge[] linkedEdge = new Edge[Integer.parseInt(operation[1])];
        String[] linked = new String[Integer.parseInt(operation[1])];
        int index = 0;
        while(index < Integer.parseInt(operation[1])){
            input = in.nextLine().trim();
            String[] temp = input.split(",");
            linked[index] = temp[0];
            if(!Graph.name.contains(temp[0])){
                System.out.println("[Wrong]您输入的相邻节点不在本景区中,操作失败:<");
                return;
            }
            linkedEdge[index++] = new Edge(Integer.parseInt(temp[1]));
        }
        Graph.addScenicSpot(newNode,linked,linkedEdge);
        System.out.println("[Success]添加成功:>");
        Graph.showGraph();
        Graph.showAllPath();
    }

    private static void serviceDeleteS() {
        System.out.println("----------------------------------------");
        System.out.println("\t请输入预删除的景点名称：");
        Scanner in = new Scanner(System.in);
        if(in.hasNextLine()){
            String input = in.nextLine().trim();
            if(!Graph.name.contains(input)){
                System.out.println("[Wrong]您输入的相邻节点不在本景区中,操作失败:<");
                return;
            }else{
                Graph.deleteScenicSpot(input);
                System.out.println("[Susscess]操作成功:>");
                Graph.showGraph();
                Graph.showAllPath();
            }
        }
    }

    private static void serviceRecoverS(){
        System.out.println("----------------------------------------");
        if(Graph.getSceneRecover().size() == 0){
            System.out.println("[Wrong]无待恢复景点:<");
            return;
        }
        System.out.println("\t以下是待恢复景点列表：");
        System.out.println("\t\t");
        for(String s : Graph.getSceneRecover().keySet()){
            System.out.print(s + "|");
        }
        System.out.println("\t请输入预恢复的景点名称：");
        Scanner in = new Scanner(System.in);
        if(in.hasNextLine()){
            String input = in.nextLine().trim();
            if(!Graph.getSceneRecover().containsKey(input)){
                System.out.println("[Wrong]您输入的节点不在待恢复景点列表中,操作失败:<");
                return;
            }else{
                Graph.recoverScenicSpot(input);
                System.out.println("[Success]操作成功:>");
                Graph.showGraph();
                Graph.showAllPath();
            }
        }
    }

    private static void serviceRecoverE(){
        System.out.println("----------------------------------------");
        if(Graph.getPathRecover().size() == 0){
            System.out.println("[Wrong]无待恢复道路:<");
            return;
        }
        System.out.println("\t以下是待恢复道路列表：");
        for(String s : Graph.getPathRecover().keySet()){
            System.out.println("\t\t" + s + "->" + Graph.getPathRecover().get(s).getPath());
        }
        System.out.println("\t请输入预恢复的道路名称: ");
        System.out.println("\t\t样例如下: ");
        System.out.println("\t\t" + "仙武湖,碧水潭");
        Scanner in = new Scanner(System.in);
        if(in.hasNextLine()){
            String input = in.nextLine().trim();
            String[] newEdge = input.split(",");
            if(!Graph.getPathRecover().containsKey(newEdge[0]) && !Graph.getPathRecover().containsKey(newEdge[1])){
                System.out.println("[Wrong]您输入的节点不在待恢复道路列表中,操作失败:<");
            }else{
                Graph.recoverEdge(newEdge);
                System.out.println("[Success]操作成功:>");
                Graph.showGraph();
                Graph.showAllPath();
            }
        }
    }

    private static void showMenu(){
        printMenuHead();
        System.out.println("1、用户界面");
        System.out.println("2、管理员登录");
        System.out.println("0、退出本系统");
        Scanner in  = new Scanner(System.in);
        if(in.hasNextLine()){
            int op = in.nextInt();
            in.nextLine();
            switch (op){
                case 0:
                    break;
                case 1:
                    showUserMenu();
                    break;
                case 2:
                    validateA();
                    break;
                default:
                    System.out.println("您的输入有误,请重新输入!");
                    showMenu();
                    break;
            }
        }
    }

    private static void printMenuHead() {
        System.out.println("=============================");
        System.out.println("    欢迎使用景区信息管理系统   ");
        System.out.println("     *****请选择菜单*****     ");
        System.out.print("      ");
        getCurTime();
        System.out.println("=============================");
    }

    private static void validateA() {
        Scanner in = new Scanner(System.in);
        String email = "";
        String password = "";
        System.out.println("请输入邮箱地址：");
        if(in.hasNextLine()){
            email = in.nextLine().trim();
        }
        System.out.println("请输入密码：");
        if(in.hasNextLine()){
            password = in.nextLine().trim();
        }
        int UID = 0;
        String driver = "com.mysql.jdbc.Driver";
        String url = Config.getMysql_url();
        String user = Config.getMysql_username();
        String sqlPassword = Config.getMysql_password();
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, sqlPassword);
            Statement statement = conn.createStatement();
            String sql = "select * from adminBean where email = '" + email+"' and psw = '"+password+"'";
            ResultSet rs = statement.executeQuery(sql);//执行查询
            while (rs.next()) {
                UID = rs.getInt("UID");
            }
            rs.close();
            statement.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(UID != 0){
            showAdminMenu();
            showMenu();
        }else{
            System.out.println("[Wrong]用户名或密码错误:<");
            showMenu();
        }
    }

    private static void showAdminMenu() {
        printMenuHead();
        Scanner in = new Scanner(System.in);
        System.out.println("1、添加景点及其邻近景点信息");
        System.out.println("2、删除景点");
        System.out.println("3、恢复景点");
        System.out.println("4、添加道路");
        System.out.println("5、删除道路");
        System.out.println("6、恢复道路");
        System.out.println("7、查看停车场历史记录信息");
        System.out.println("8、发布新公告");
        System.out.println("0、返回主菜单");
        int op = -1;
        if(in.hasNextLine()){
            op = in.nextInt();
            in.nextLine();
            switch (op){
                case 1:
                    serviceAddS();
                    showAdminMenu();
                    break;
                case 2:
                    serviceDeleteS();
                    showAdminMenu();
                    break;
                case 3:
                    serviceRecoverS();
                    showAdminMenu();
                    break;
                case 4:
                    serviceAddE();
                    showAdminMenu();
                    break;
                case 5:
                    serviceDeleteE();
                    showAdminMenu();
                    break;
                case 6:
                    serviceRecoverE();
                    showAdminMenu();
                    break;
                case 7:
                    Parking.printRecord();
                    showAdminMenu();
                    break;
                case 8:
                    System.out.println("----------------------------------------");
                    System.out.println("请输入您想要的新公告: ");
                    if(in.hasNextLine()){
                        setMessage(in.nextLine().trim());
                        System.out.println("[Success]修改成功:>");
                        System.out.println("新公告: " + getMessage());
                    }
                    showAdminMenu();
                    break;
                case 0:
                    break;
                default:
                    showMenu();
                    break;
            }
        }
    }

    private static void serviceDeleteE() {
        System.out.println("----------------------------------------");
        System.out.println("\t请输入想要删除的景区道路: ");
        System.out.println("\t样例如下: ");
        System.out.println("\t\t" + "仙武湖,碧水潭");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine().trim();
        String[] newEdge = input.split(",");
        if(Graph.name.contains(newEdge[0]) && Graph.name.contains(newEdge[1])){
            if(Graph.getScene().get(newEdge[0]).getLinked().containsKey(newEdge[1]))
                Graph.deleteEdge(newEdge);
        }else{
            System.out.println("[Wrong]您的输入有误:<");
        }
    }

    private static void serviceAddE() {
        System.out.println("----------------------------------------");
        System.out.println("\t请输入新路的起点与终点即路线长度：");
        System.out.println("\t样例如下：");
        System.out.println("\t\t" + "花卉园,北门,9");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine().trim();
        String[] newEdge = input.split(",");
        if(Graph.name.contains(newEdge[0]) && Graph.name.contains(newEdge[1])){
            Graph.addEdge(newEdge);
        }else{
            System.out.println("[Wrong]您的输入有误:<");
        }
    }

    private static void showUserMenu() {
        printMenuHead();
        System.out.println(getMessage());
        System.out.println("1、景区景点分布图");
        System.out.println("2、景点查找");
        System.out.println("3、景点排序");
        System.out.println("4、输出导游线路图(近似解)");
        System.out.println("5、输出导游线路图(精确解)");
        System.out.println("6、景点间最短路线");
        System.out.println("7、停车场车辆进出信息记录");
        System.out.println("0、返回上级菜单");
        Scanner in  = new Scanner(System.in);
        if(in.hasNextLine()){
            int op = in.nextInt();
            in.nextLine();
            switch (op){
                case 0:
                    showMenu();
                    break;
                case 1:
                    Graph.showGraph();
                    showUserMenu();
                    break;
                case 2:
                    System.out.println("景区景点列表如下：");
                    System.out.println(Graph.name.toString());
                    System.out.println("请输入您想要查询的景点关键字：");
                    if(in.hasNextLine()){
                        String input = in.nextLine().trim();
                        Graph.searchScenicSpot(input);
                    }
                    showUserMenu();
                    break;
                case 3:
                    System.out.println("请输入您想要选择的景点排序模式：岔路数 or 受欢迎度优先");
                    System.out.println("样例如下：");
                    System.out.println("\t岔路数");
                    if(in.hasNextLine()){
                        String mode = in.nextLine().trim();
                        Graph.sortScenicSpotBy(mode);
                    }
                    showUserMenu();
                    break;
                case 4:
                    System.out.println("景区景点列表如下：");
                    System.out.println(Graph.name.toString());
                    System.out.println("请输入您想要选择的线路起点：");
                    System.out.println("样例如下：");
                    System.out.println("\t北门");
                    if(in.hasNextLine()){
                        String input = in.nextLine().trim();
                        if(!Graph.name.contains(input)){
                            System.out.println("[Wrong]您输入的景点不在本景区中,操作失败:<");
                        }else{
                            Graph.outGuideMap(input);
                        }
                    }
                    showUserMenu();
                    break;
                case 5:
                    System.out.println("景区景点列表如下：");
                    System.out.println(Graph.name.toString());
                    System.out.println("请输入您想要选择的线路起点：");
                    System.out.println("样例如下：");
                    System.out.println("\t北门");
                    if(in.hasNextLine()){
                        String input = in.nextLine().trim();
                        if(!Graph.name.contains(input)){
                            System.out.println("[Wrong]您输入的景点不在本景区中,操作失败:<");
                        }else{
                            Graph.tsp(input);
                        }
                    }
                    showUserMenu();
                    break;
                case 6:
                    System.out.println("请输入您想要选择的线路起点与终点：");
                    System.out.println("样例如下：");
                    System.out.println("\t北门,狮子山");
                    if(in.hasNextLine()){
                        String input = in.nextLine().trim();
                        String[] line = input.split(",");
                        if(!Graph.name.contains(line[0]) || !Graph.name.contains(line[1])){
                            System.out.println("[Wrong]您输入的景点不在本景区中,操作失败:<");
                        }else{
                            Graph.showPath(line[0],line[1]);
                        }
                    }
                    showUserMenu();
                    break;
                case 7:
                    System.out.println("依次输入0/1（分别表示进入/离开）,车牌号,进入/离开时间（离开时间不必填写）");
                    System.out.println("样例如下：");
                    System.out.println("\t0,苏H12345,13:00");
                    System.out.println("\t1,苏H12345");
                    if(in.hasNextLine()){
                        String input = in.nextLine().trim();
                        String[] res = input.split(",");
                        Parking.recordUpdate(res);
                    }
                    showUserMenu();
                    break;
                default:
                    System.out.println("[Wrong]您的输入有误:<");
                    showUserMenu();
                    break;
            }
        }
    }

    private static String getCurTime() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String res = year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second;
        System.out.println(res);
        return res;
    }

    private static String getMessage(){
        String driver = "com.mysql.jdbc.Driver";
        String url = Config.getMysql_url();
        String user = Config.getMysql_username();
        String sqlPassword = Config.getMysql_password();
        String message = null;
        int UID = 1;
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, sqlPassword);
            Statement statement = conn.createStatement();
            String sql = "select * from adminBean where UID = '" + UID+"'";
            ResultSet rs = statement.executeQuery(sql);//执行查询
            while (rs.next()) {
                message = rs.getString("message");
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static String setMessage(String message){
        String driver = "com.mysql.jdbc.Driver";
        String url = Config.getMysql_url();
        String user = Config.getMysql_username();
        String sqlPassword = Config.getMysql_password();
        int UID = 1;
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, sqlPassword);
            Statement statement = conn.createStatement();
            String sql = "update adminbean SET message = '" + message + "' WHERE UID = '" + UID + "' ";
            statement.executeUpdate(sql);//执行查询
            statement.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void initSystem(){
        if(StartSystem != 0){
            return;
        }
        StartSystem++;
        Graph.initGraph();
        Graph.initName();
        Graph.initSPath();
        Graph.computeSPath();
        //Graph.showAllPath();
    }

    public static void saveInDB(){
        String driver = "com.mysql.jdbc.Driver";
        String url = Config.getMysql_url();
        String user = Config.getMysql_username();
        String sqlPassword = Config.getMysql_password();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, sqlPassword);
            String sql = "insert into edges (start,end,dis) values(?,?,?)";
            PreparedStatement pstmt;
            try {
                pstmt = (PreparedStatement) conn.prepareStatement(sql);
                int num = 0;
                for(String s : Graph.getEdges().keySet()){
                    num += Graph.getEdges().size();
                }
                    for(String s : Graph.getEdges().keySet()){
                        for(Path p : Graph.getEdges().get(s)){
                            pstmt.setString(1, s);
                            pstmt.setString(2, p.getPath());
                            pstmt.setInt(3, p.getDis());
                            pstmt.executeUpdate();
                        }
                    }
                pstmt.setString(1, "更新");
                pstmt.setString(2, getCurTime());
                pstmt.setInt(3, 1);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        // TODO:
        /*----------------------------------------*/
        // FIXME:
        /*----------------------------------------*/
        initSystem();
        showMenu();
    }

}
