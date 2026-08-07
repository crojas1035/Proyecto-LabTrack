package com.labtrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbCheck {
    public static void main(String[] args) {
        String url = "jdbc:mysql://mysql-3970deee-pavelmora21-b405.l.aivencloud.com:11320/labtrack";
        String user = "avnadmin";
        String password = "AVNS_2SGLwRwDOnA_1VEk0ZS";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            
            ResultSet rs = stmt.executeQuery("SELECT id_equipo, nombre FROM equipo");
            while(rs.next()) {
                System.out.println(rs.getInt(1) + ": " + rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
