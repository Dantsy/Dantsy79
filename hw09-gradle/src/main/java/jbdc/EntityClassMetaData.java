package jbdc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityClassMetaData {
    private final String name;
    private final Map<String, Field> fields;
    private Field idField;

    public EntityClassMetaData(String name) {
        this.name = name;
        this.fields = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, Field> getFields() {
        return fields;
    }

    public Field getIdField() {
        return idField;
    }

    public void addField(Field field) {
        fields.put(field.getName(), field);
        if (field.isAnnotationPresent(Id.class)) {
            idField = field;
        }
    }
}