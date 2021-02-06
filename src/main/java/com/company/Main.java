package com.company;


import com.company.menu.StartScreen;
import com.company.service.ContactsNioService;
import com.company.service.ContactsService;
import com.company.service.HttpPlusJsonContactsService;
import com.company.service.InMemoryContactsService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main {


    public static void main(String[] args) {
        setProfile();
        ContactsService contactsService = chooseMode();
        StartScreen startScreen = new StartScreen(contactsService);
        startScreen.start();

    }
    private static void setProfile(){
        Properties propFromFile = new Properties();
        try {
            if (System.getProperty("contactbook.profile").equals("dev")) {
                propFromFile.load(new FileInputStream("app-dev.properties"));
            } else if (System.getProperty("contactbook.profile").equals("prod")) {
                propFromFile.load(new FileInputStream("app-prod.properties"));
            } else {
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setProperty("app.service.workmode", propFromFile.getProperty("app.service.workmode"));
        System.setProperty("api.base-uri", propFromFile.getProperty("api.base-uri"));
        System.setProperty("file.path", propFromFile.getProperty("file.path"));

    }
    private static ContactsService chooseMode(){
        switch (System.getProperty("app.service.workmode")){
            case "api":
                return new HttpPlusJsonContactsService();
            case "file":
                return new ContactsNioService();
            case "memory":
                return new InMemoryContactsService();
            default:
                System.exit(0);
                throw new RuntimeException();
        }

    }
}
