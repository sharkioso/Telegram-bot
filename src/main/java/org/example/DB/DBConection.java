package org.example.DB;
import java.sql.*;


public class DBConection {

    private static final String url = System.getenv("urlDataBaseTG");
    private static final String user = System.getenv("userDataBaseTG");
    private static final String pasword = System.getenv("paswordDataBaseTG");


    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pasword);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void addPersonState(long id, String state) {
        String sql = "INSERT INTO public.\"FSM \"(\"ChatID\", \"State\") VALUES(?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(String.valueOf(id)));
            pstmt.setString(2, state);
            pstmt.executeUpdate();
            System.out.println("Person added to state");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Boolean isUserExist(long chatId) {
        String query = "SELECT 1 FROM \"FSM \" WHERE \"ChatID\" = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(String.valueOf(chatId)));
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public static void changeState(long chatId, String state) {
        String query = "UPDATE \"FSM \" SET \"State\" = ? WHERE \"ChatID\" = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, state);
            stmt.setInt(2, Integer.parseInt(String.valueOf(chatId)));
            stmt.executeUpdate();
            System.out.println("State changed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static String getState(long chatId) {
        String query = "SELECT \"State\" FROM \"FSM \" WHERE \"ChatID\" = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(String.valueOf(chatId)));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
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

    public static String sendPerson(int age) {
        String query = "SELECT * FROM person WHERE age = ?";
        String result = "";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(String.valueOf(age)));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                boolean gender = rs.getBoolean("gender");
                String id = rs.getString("id");
                String town = rs.getString("town");
                String description = rs.getString("description");

                result = String.format("ID: %s, Name: %s, Gender: %s, Age: %d, Town: %s, Description: %s",
                        id, name, gender, age, town, description);;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }
}
