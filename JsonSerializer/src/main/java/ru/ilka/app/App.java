package ru.ilka.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.ilka.app.model.Person;
import ru.ilka.app.utils.JsonSerializer;

import java.lang.reflect.InvocationTargetException;

/**
 *
 *
 */
public class App 
{
    public static void main( String[] args ) throws NoSuchFieldException, JsonProcessingException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Person Ilya = new Person("Ilya","Morin",19);
        String json = JsonSerializer.convertToJson(Ilya);
        System.out.println(json);
    }
}
