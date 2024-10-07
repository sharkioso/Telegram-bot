package org.example.DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBConection {

    private static final String url = System.getenv("urlDataBaseTG");
    private static final String user = System.getenv("userDataBaseTG");
    private static final String pasword = System.getenv("paswordDataBaseTG");


    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pasword);
            System.out.println("connection is OK");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public static void insertPerson(String id, String name, Boolean gender, int age, String town, String description) {
        String sql = "INSERT INTO person(id, name, gender, age, town, description) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setBoolean(3, gender);
            pstmt.setInt(4, age);
            pstmt.setString(5, town);
            pstmt.setString(6, description);
            pstmt.executeUpdate();
            System.out.println("Person added to the database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


