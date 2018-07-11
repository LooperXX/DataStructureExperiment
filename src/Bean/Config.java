package Bean;

public class Config {
    private static String mysql_url = "jdbc:mysql://localhost:3306/mysql?useSSL=false";//need to be modified
    private static String mysql_username = "root";
    private static String mysql_password = "password";

    public static String getMysql_url() {
        return mysql_url;
    }

    public static void setMysql_url(String mysql_url) {
        Config.mysql_url = mysql_url;
    }

    public static String getMysql_username() {
        return mysql_username;
    }

    public static void setMysql_username(String mysql_username) {
        Config.mysql_username = mysql_username;
    }

    public static String getMysql_password() {
        return mysql_password;
    }

    public static void setMysql_password(String mysql_password) {
        Config.mysql_password = mysql_password;
    }
}
