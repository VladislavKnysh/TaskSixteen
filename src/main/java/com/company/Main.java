package com.company;


import com.company.menu.StartScreen;
import com.company.properties.ProjectStarter;
import com.company.service.ContactsService;
import com.company.service.factory.ContactsServiceFactory;


public class Main {


    public static void main(String[] args) {
        ContactsService contactsService = setContactService();

        StartScreen startScreen = new StartScreen(contactsService);
        startScreen.start();
    }


    public static ContactsService setContactService() {
        ProjectStarter pm = new ProjectStarter(ContactsServiceFactory.FactoryType.PROP);
        return pm.startProject();
    }


}