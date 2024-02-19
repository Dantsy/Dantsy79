package jbdc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JdbcMapper {
    private final Map<Class<?>, EntityClassMetaData> entityMetaDataMap;

    public JdbcMapper() {
        this.entityMetaDataMap = new HashMap<>();
    }

    public EntityClassMetaData getEntityClassMetaData(Class<?> clazz) {
        return entityMetaDataMap.computeIfAbsent(clazz, this::createEntityClassMetaData);
    }

    private EntityClassMetaData createEntityClassMetaData(Class<?> clazz) {
        EntityClassMetaData entityClassMetaData = new EntityClassMetaData(clazz.getSimpleName());
        for (Field field : clazz.getDeclaredFields()) {
            entityClassMetaData.addField(field);
        }
        return entityClassMetaData;
    }
}