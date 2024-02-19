package jbdc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQueryExample {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/jbdc";
        String user = "user";
        String password = "pwd";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM mytable");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the PostgreSQL server.");
            e.printStackTrace();
        }
    }
}