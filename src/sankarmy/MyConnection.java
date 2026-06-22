 package sankarmy;
import java.sql.Connection;
import java.sql.DriverManager;
public class MyConnection {
    public static Connection getConnection() {
        Connection con = null;
    try {
    // बिना किसी पाथ के ड्राइवर को लोड करना
    Class.forName("com.mysql.cj.jdbc.Driver");
    
    String url = "jdbc:mysql://localhost:3306/sankar";
    java.util.Properties props = new java.util.Properties();
    props.setProperty("user", "root");
    props.setProperty("password", "");
    
    con = DriverManager.getConnection(url, props);
    System.out.println("Database Connected Successfully!");
} catch (Exception e) {
    System.out.println("Connection Error: " + e.getMessage());
}
    return con;
    }
    
    public static void main(String[] args) {
        getConnection(); // टेस्ट करने के लिए
    }
}
