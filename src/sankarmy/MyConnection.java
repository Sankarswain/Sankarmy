package sankarmy;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            // 1. MySQL JDBC Driver लोड करें
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. आपका पुराना सीधा लोकलहोस्ट URL
            String url = "jdbc:mysql://localhost:3306/sankar";
            String user = "root";
            String password = "";

            // 3. कनेक्शन स्थापित करें
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Local Database Connected Successfully!");

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return con;
    }

    // यह रहा आपका मुख्य मेन मेथड (main method) जो छूट गया था
    public static void main(String[] args) {
        getConnection();
    }
}