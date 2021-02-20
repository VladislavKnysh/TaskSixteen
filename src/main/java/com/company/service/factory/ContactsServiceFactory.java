package com.company.service.factory;

import com.company.annotstions.CreateIfMode;
import com.company.service.ContactsService;
import com.company.service.implementation.ContactsNioService;
import com.company.service.implementation.JsonHttpContactsService;
import com.company.service.implementation.InMemoryContactsService;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ContactsServiceFactory {
    public ContactsNioService createNioService() {
        return new ContactsNioService();
    }

    public JsonHttpContactsService createJsonService() {
        return new JsonHttpContactsService();
    }

    public InMemoryContactsService createMemoryService() {
        return new InMemoryContactsService();
    }

    public ContactsService createPropertyService(String mode) {


        List<Class> classes = find("com.company.service.implementation")
                .stream()
                .filter(c -> ContactsService.class.isAssignableFrom(c))
                .filter(c -> c.isAnnotationPresent(CreateIfMode.class))
                .collect(Collectors.toList());
        for (Class aClass : classes) {
            CreateIfMode modeAnnotation =
                    (CreateIfMode) aClass.getAnnotation(CreateIfMode.class);
            String[] modes = modeAnnotation.value();
            if (Arrays.asList(modes).contains(mode)) {
                Object o = createObject(aClass);
                return (ContactsService) o;
            }
        }
        return null;
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

    private Object createObject(Class clazz) {
        try {
            Constructor constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (InstantiationException
                | InvocationTargetException
                | NoSuchMethodException
                | IllegalAccessException e) {
            throw new RuntimeException("NEED DEFAULT CONSTRUCTOR", e);
        }
    }
}
