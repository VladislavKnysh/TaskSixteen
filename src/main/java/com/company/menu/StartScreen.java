package com.company.menu;

import com.company.exceptions.IllegalRegistrarMethodAccessException;
import com.company.menu.actions.*;
import com.company.menu.registrar.DataBaseRegistrar;
import com.company.menu.registrar.JsonRegistrar;
import com.company.menu.registrar.MemoryAndNioRegistrar;
import com.company.menu.registrar.Registrar;
import com.company.service.ContactsService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class StartScreen {

    private List<MenuActions> actions = new ArrayList<>();

    private final ContactsService contactsService;

    public void start() {
        getActions();
        Menu menu = new Menu(actions, new Scanner(System.in), contactsService);
        menu.run();

    }

    private void getActions() {
        try {
            if (contactsService.getServiceType().equals(ContactsService.ServiceType.HTTP_PLUS_JSON)) {
                Registrar jsonRegistrar = new JsonRegistrar();

                actions = jsonRegistrar.defineMenuActions(contactsService);

            } else if (contactsService.getServiceType().equals(ContactsService.ServiceType.DATABASE)) {
                Registrar dataBaseRegistrar = new DataBaseRegistrar();
                actions = dataBaseRegistrar.defineMenuActions(contactsService);
            } else {
                Registrar memoryAndNioRegistrar = new MemoryAndNioRegistrar();
                actions = memoryAndNioRegistrar.defineMenuActions();
            }
        } catch (IllegalRegistrarMethodAccessException e) {
            e.printStackTrace();
        }
    }
}