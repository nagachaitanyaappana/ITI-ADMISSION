package com.server.backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DbDump {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/itiap";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "mKnic123");

        try (Connection conn = DriverManager.getConnection(url, props);
             Statement stmt = conn.createStatement()) {
            
            System.out.println("--- DB EXAMPLES ---");
            printFirst(stmt, "Year", "SELECT year_of_admission FROM admissions.iti_admissions WHERE year_of_admission IS NOT NULL LIMIT 1");
            printFirst(stmt, "DistCode", "SELECT dist_code FROM public.dist_mst LIMIT 1");
            printFirst(stmt, "ItiCode", "SELECT iti_code FROM public.iti LIMIT 1");
            printFirst(stmt, "TradeCode", "SELECT trade_code FROM public.ititrade_master LIMIT 1");
            printFirst(stmt, "Phase", "SELECT phase FROM admissions.iti_admissions WHERE phase IS NOT NULL LIMIT 1");
            printFirst(stmt, "ModeAdm", "SELECT mode_adm FROM admissions.iti_admissions WHERE mode_adm IS NOT NULL AND mode_adm != '' LIMIT 1");
            printFirst(stmt, "Caste", "SELECT res_category FROM admissions.iti_admissions WHERE res_category IS NOT NULL LIMIT 1");
            printFirst(stmt, "Gender", "SELECT gender FROM admissions.iti_admissions WHERE gender IS NOT NULL LIMIT 1");
            printFirst(stmt, "Govt", "SELECT govt FROM public.iti WHERE govt IS NOT NULL LIMIT 1");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printFirst(Statement stmt, String name, String query) {
        try (ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                System.out.println(name + ": " + rs.getString(1));
            } else {
                System.out.println(name + ": NO DATA");
            }
        } catch (Exception e) {
            System.out.println(name + ": ERROR - " + e.getMessage());
        }
    }
}
