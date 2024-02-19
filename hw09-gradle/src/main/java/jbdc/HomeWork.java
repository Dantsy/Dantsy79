package jbdc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class HomeWork {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/jbdc";
        String user = "user";
        String password = "pwd";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            JdbcMapper jdbcMapper = new JdbcMapper();

            EntityClassMetaData entityClassMetaData = jdbcMapper.getEntityClassMetaData(Entity.class);

            DataTemplateJdbc dataTemplateJdbc = new DataTemplateJdbc(jdbcMapper);

            Optional<Entity> entity = dataTemplateJdbc.findById(connection, Entity.class, 1L, rs -> {
                try {
                    return new Entity(rs.getLong("id"), rs.getString("name"));
                } catch (SQLException e) {
                    throw new DataTemplateException(e);
                }
            });

            entity.ifPresent(System.out::println);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}