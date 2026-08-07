package com.labtrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbFix {
    public static void main(String[] args) {
        String url = "jdbc:mysql://mysql-3970deee-pavelmora21-b405.l.aivencloud.com:11320/labtrack";
        String user = "avnadmin";
        String password = "AVNS_2SGLwRwDOnA_1VEk0ZS";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            
            // Delete equipos to avoid foreign key errors on update
            stmt.executeUpdate("DELETE FROM detalle_prestamo");
            stmt.executeUpdate("DELETE FROM prestamo");
            stmt.executeUpdate("DELETE FROM reporte_falla");
            stmt.executeUpdate("DELETE FROM equipo");
            
            System.out.println("Data cleaned successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
