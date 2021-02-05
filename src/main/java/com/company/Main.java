package com.company;


import com.company.dto.Contact;
import com.company.dto.LoginRequest;
import com.company.dto.RegisterRequest;

import com.company.menu.StartScreen;
import com.company.service.ContactsService;
import com.company.service.HttpPlusJsonContactsService;
import com.fasterxml.jackson.databind.ObjectMapper;





public class Main {


    public static void main(String[] args) {
        ContactsService contactsService = new HttpPlusJsonContactsService();
        StartScreen startScreen = new StartScreen(contactsService);
        startScreen.start();

    }

    }
