package com.company;


import com.company.menu.StartScreen;
import com.company.properties.ProfileHandler;
import com.company.service.ContactsService;



public class Main {


    public static void main(String[] args) {



        ProfileHandler pm = new ProfileHandler();

        pm.setProfile();
        ContactsService contactsService = pm.chooseMode();

        StartScreen startScreen = new StartScreen(contactsService);
        startScreen.start();

    }

}
