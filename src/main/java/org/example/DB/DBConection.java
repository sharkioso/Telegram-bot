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

    public static void deletePerson(long id) {
        String sql = "DElETE FROM public.\"FSM \" WHERE \"ChatID\" = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(String.valueOf(id)));
            pstmt.executeUpdate();
            System.out.println("Person delete");
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

    public static String sendPerson(int chatId) {
        String query = "SELECT * FROM person WHERE id = ?";
        String result = "";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(chatId));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                boolean gender = rs.getBoolean("gender");
                String age = rs.getString("age");
                String town = rs.getString("town");
                String description = rs.getString("description");

//                result = String.format("Name: %s, Age: %s, Town: %s, Description: %s",
//                        name, age, town, description);
                result += name + "\n" + age + "\n" + town + "\n" + description;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }


    public static String getPersonForNumber(int num) {
        String query = "SELECT id FROM person LIMIT 1 OFFSET ?";
        String chatId = "";

        try(Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, num);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                chatId = rs.getString("id");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return chatId;
    }


    public static int getNumPerson(long chatId) {
        int num = -1;
        String query = "SELECT count_look FROM person WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, String.valueOf(chatId));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                num = rs.getInt("count_look");
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return num;
    }


    public static void changeNumPerson(long chatId, int new_num) {
        String query = "UPDATE person SET count_look = ? WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, new_num);
            stmt.setString(2, String.valueOf(chatId));
            stmt.executeUpdate();
            System.out.println("Count num changed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void addFilterPerson(long chatId) {
        String sql = "INSERT INTO filter(id) VALUES(?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(String.valueOf(chatId)));
            pstmt.executeUpdate();
            System.out.println("Person added to filter");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void changeGender(long chatId, Boolean gender) {
        String query = "UPDATE filter SET gender = ? WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, gender);
            stmt.setInt(2, Integer.parseInt(String.valueOf(chatId)));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void changeMaxAge(long chatId, int age) {
        String query = "UPDATE filter SET age_max = ? WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, age);
            stmt.setInt(2, Integer.parseInt(String.valueOf(chatId)));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void changeMinAge(long chatId, int age) {
        String query = "UPDATE filter SET age_min = ? WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, age);
            stmt.setInt(2, Integer.parseInt(String.valueOf(chatId)));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void changeGeo(long chatId, double latitude, double longitude) {
        String query = "UPDATE filter SET geo = POINT(?, ?) WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, latitude);
            stmt.setDouble(2, longitude);
            stmt.setInt(3, Integer.parseInt(String.valueOf(chatId)));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static int getAgeMin(long chatId) {
        String query = "SELECT age_min FROM filter WHERE id = ?";
        int age = -1;

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(String.valueOf(chatId)));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                age = rs.getInt("age_min");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return age;
    }


    public static int getAgeMax(long chatId) {
        String query = "SELECT age_max FROM filter WHERE id = ?";
        int age = 123;

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(String.valueOf(chatId)));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                age = rs.getInt("age_max");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return age;
    }


    public static int getAgePerson(long chatId) {
        String query = "SELECT age FROM person WHERE id = ?";
        int age = -1;

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(chatId));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                age = rs.getInt("age");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return age;
    }


    public static String getGenderLike(long chatId) {
        String query = "SELECT gender FROM filter WHERE id = ?";
        String ans = "";
        Boolean gender;

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(String.valueOf(chatId)));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                gender = rs.getBoolean("gender");
                if (gender) {
                    ans = "m";
                } else {
                    ans = "w";
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ans;
    }


    public static String getGenderPerson(long chatId) {
        String query = "SELECT gender FROM person WHERE id = ?";
        String ans = "";
        Boolean gender;

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(chatId));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                gender = rs.getBoolean("gender");
                if (gender) {
                    ans = "m";
                } else {
                    ans = "w";
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ans;
    }


    public static String getGeo(long chatId) {
        String query = "SELECT geo FROM filter WHERE id = ?";
        String ans = "";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(String.valueOf(chatId)));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ans = rs.getString("geo");
                System.out.println(ans);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ans;
    }
}
