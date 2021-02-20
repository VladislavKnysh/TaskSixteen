package com.company.properties;

import com.company.annotstions.CreateIfMode;
import com.company.config.AppProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class ModeHandler {

    Map<Class, Object> instances = new HashMap<>();
    private String mode;

    private void chooseMode() {

        PropertiesManager propertiesManager = new PropertiesManager();
        mode = propertiesManager.getSystemProp(AppProperties.class).getWorkMode();

    }

    private List<Class> find(String packageName) {
        String basePath = packageName.replaceAll("\\.", "/");
        URL res = getClass().getClassLoader().getResource(basePath);
        try {
            File dir = new File(res.toURI());
            return Arrays.stream(dir.listFiles())
                    .map(File::getName)
                    .filter(name -> name.endsWith(".class"))
                    .map(name -> packageName + "." + name)
                    .map(name -> name.substring(0, name.length() - 6))
                    .map(className -> {
                        try {
                            return Class.forName(className);
                        } catch (ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getService(Class<T> serviceClass) {

        chooseMode();

        if (instances.containsKey(serviceClass)) {
            return (T) instances.get(serviceClass);
        }

        List<Class> classes = find("com.company.service.implementation")
                .stream()
                .filter(c->serviceClass.isAssignableFrom(c))
                .filter(c-> c.isAnnotationPresent(CreateIfMode.class))
                .collect(Collectors.toList());
        for (Class aClass : classes) {
            CreateIfMode modeAnnotation =
                    (CreateIfMode) aClass.getAnnotation(CreateIfMode.class);
            String[] modes = modeAnnotation.value();
            if(Arrays.asList(modes).contains(mode)){
                Object o = createObject(aClass);
                return (T) o;
            }
        }
        return null;
    }


    private Object createObject(Class clazz){
        try {
            Constructor constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException("NEED DEFAULT CONSTRUCTOR", e);
        }
    }
}