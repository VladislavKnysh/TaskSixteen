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
        ProjectStarter pm = new ProjectStarter();
        return pm.startProject();
    }

    public static ContactsService setContactService(ContactsService.ServiceType serviceType) {
        ContactsServiceFactory contactsServiceFactory = new ContactsServiceFactory();
        switch (serviceType) {
            case IN_MEMORY:
                return contactsServiceFactory.createMemoryService();
            case NIO:
                return contactsServiceFactory.createNioService();
            case HTTP_PLUS_JSON:
                return contactsServiceFactory.createJsonService();
            default:
                throw new IllegalStateException("Unexpected value: " + serviceType);
        }

    }
}