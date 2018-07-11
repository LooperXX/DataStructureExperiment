<%--
  Created by IntelliJ IDEA.
  User: LooperXX
  Date: 2018/7/8
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="Bean.AdminBean,Bean.Config" %>
<%@ page import="java.sql.*" %>
<%
    String driver = "com.mysql.jdbc.Driver";
    String url = Config.getMysql_url();
    String user = Config.getMysql_username();
    String sqlPassword = Config.getMysql_password();
    try {

        String email = request.getParameter("email");
        String password = request.getParameter("psw");
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, sqlPassword);
        Statement statement = conn.createStatement();
        String sql = "select * from adminBean where email = '" + email+"' and psw = '"+password+"'";
        ResultSet rs = statement.executeQuery(sql);//执行查询
        while (rs.next()) {
            int UID = rs.getInt("UID");
            String message = rs.getString("message");
            AdminBean admin = new AdminBean(email,password);
            session.setAttribute("admin",admin);
            session.setAttribute("message",message);
            session.setAttribute("UID",UID);
            out.println("<script>window.location.href='"+"admin.jsp"+"'</script>");
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
%>
<html>
<head>
    <title>login</title>
</head>
<body>
    登录失败
</body>
</html>
