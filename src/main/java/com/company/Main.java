package com.company;


import com.company.menu.StartScreen;
import com.company.properties.ProfileHandler;
import com.company.properties.ProjectStarter;
import com.company.properties.PropertiesManager;
import com.company.service.ContactsService;



public class Main {


    public static void main(String[] args) {



        ProjectStarter pm = new ProjectStarter();

        ContactsService contactsService =  pm.startProject();


        StartScreen startScreen = new StartScreen(contactsService);
        startScreen.start();

    }

}
