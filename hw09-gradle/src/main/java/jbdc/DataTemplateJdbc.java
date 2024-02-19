package jbdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Function;

public class DataTemplateJdbc {
    private final JdbcMapper jdbcMapper;

    public DataTemplateJdbc(JdbcMapper jdbcMapper) {
        this.jdbcMapper = jdbcMapper;
    }

    public <T> Optional<T> findById(Connection connection, Class<T> clazz, long id, Function<ResultSet, T> rsHandler) {
        EntityClassMetaData entityClassMetaData = jdbcMapper.getEntityClassMetaData(clazz);
        String selectSql = getSelectByIdSql(entityClassMetaData);

        try (PreparedStatement pst = connection.prepareStatement(selectSql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? Optional.of(rsHandler.apply(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataTemplateException(e);
        }
    }

    private String getSelectByIdSql(EntityClassMetaData entityClassMetaData) {
        String tableName = entityClassMetaData.getName();
        String idColumnName = entityClassMetaData.getIdField().getName();
        return "SELECT * FROM " + tableName + " WHERE " + idColumnName + " = ?";
    }
}