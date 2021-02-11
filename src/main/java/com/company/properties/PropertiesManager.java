package com.company.properties;

import com.company.annotstions.SystemProp;
import lombok.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Properties;

@Data
public class PropertiesManager {

    private Object createObject(Class clazz){
        try {
            Constructor constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException("NEED DEFAULT CONSTRUCTOR", e);
        }
    }

    public <E> E getSystemProp(Class<E> clazz ) {
        Object object = createObject(clazz);
        extractedProps(object, System.getProperties());
        return (E) object;
    }

    public <E> E getFileProp(Class<E> clazz, String file) {
        Object object = createObject(clazz);
        try (InputStream is = new FileInputStream(file)) {
            Properties props = new Properties();
            props.load(is);

            extractedProps(object, props);
            return (E) object;
        } catch (IOException e) {
           throw new RuntimeException("FAIL LOAD FROM "+ file, e);
        }
    }

    private void extractedProps(Object object, Properties props) {
        Class clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(SystemProp.class)) {
                SystemProp annotation = field.getAnnotation(SystemProp.class);
                String propName = annotation.value();

                String value = props.getProperty(propName);
                field.setAccessible(true);
                try {
                    field.set(object, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
