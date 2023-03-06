package ru.ilka.app.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JsonSerializer {

    public static String convertToJson(Object obj) throws JsonProcessingException, IllegalAccessException {
        // Получаем список полей объекта и их соответствующих имен в JSON
        Map<String, String> fieldMap = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            // Получаем аннотацию JsonProperty для поля
            JsonProperty annotation = field.getAnnotation(JsonProperty.class);
            if (annotation != null) {
                // Если аннотация есть, то добавляем в мапу имя поля и имя свойства в JSON
                fieldMap.put(field.getName(), annotation.value());
            } else {
                // Иначе добавляем в мапу имя поля и его имя в JSON как есть
                fieldMap.put(field.getName(), field.getName());
            }
        }

        // Сериализуем объект в JSON с помощью Jackson.
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> serialized = new HashMap<>();
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            String objectFieldName = entry.getKey();
            String objectPropertyDisplayName = entry.getValue();
            Field field;

            try {
                // Получаем объект Field для поля по его имени
                field = obj.getClass().getDeclaredField(objectFieldName);
            } catch (NoSuchFieldException ex) {
                // Если такого поля нет, то пропускаем итерацию цикла
                continue;
            }

            // Делаем поле доступным для чтения
            field.setAccessible(true);
            // Получаем значение поля объекта
            Object value = field.get(obj);

            // Если мы сами задаем имя для поля JSON,
            // то оно и запишется в JSON,
            // иначе будет использоваться имя поля, как в классе.
            if (!entry.getValue().equals("")) {
                serialized.put(objectPropertyDisplayName, value);
            } else {
                serialized.put(objectFieldName, value);
            }
        }
        // Возвращаем объект, преобразованный в JSON
        return mapper.writeValueAsString(serialized);
    }
}