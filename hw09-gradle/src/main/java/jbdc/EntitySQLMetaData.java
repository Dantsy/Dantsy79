package jbdc;

import java.util.Collections;
import java.util.stream.Collectors;

public class EntitySQLMetaData {
    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaData(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    public String getSelectAllSql() {
        return "SELECT * FROM " + entityClassMetaData.getName();
    }

    public String getSelectByIdSql() {
        return getSelectAllSql() + " WHERE id = ?";
    }

    public String getInsertSql() {
        String columns = String.join(", ", entityClassMetaData.getFields().keySet());
        String placeholders = String.join(", ", Collections.nCopies(entityClassMetaData.getFields().size(), "?"));
        return "INSERT INTO " + entityClassMetaData.getName() + " (" + columns + ") VALUES (" + placeholders + ")";
    }

    public String getUpdateSql() {
        String setClause = entityClassMetaData.getFields().keySet().stream()
                .map(field -> field + " = ?")
                .collect(Collectors.joining(", "));
        return "UPDATE " + entityClassMetaData.getName() + " SET " + setClause + " WHERE id = ?";
    }

    public EntityClassMetaData getEntityClassMetaData() {
        return entityClassMetaData;
    }
    public String getDeleteSql() {
        String tableName = entityClassMetaData.getName();

        String primaryKeyColumnName = entityClassMetaData.getIdField().getName();

        return "DELETE FROM " + tableName + " WHERE " + primaryKeyColumnName + " = ?";
    }
}