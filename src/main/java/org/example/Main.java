package app;

import db.DBconnection;

import java.sql.Connection;
public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DBconnection.getConnection();
            if (conn != null) {
                System.out.println("✅ Connected to MySQL successfully!");
            } else {
                System.out.println("❌ Connection failed.");
            }
        } catch (Exception e) {
            System.out.println("❌ Unexpected error.");
            e.printStackTrace();
        }
    }
}