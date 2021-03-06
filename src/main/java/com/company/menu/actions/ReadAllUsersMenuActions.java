package com.company.menu.actions;

import com.company.exceptions.IllegalContactsServiceMethodAccessException;
import com.company.menu.actions.MenuActions;
import com.company.service.ContactsService;
import lombok.RequiredArgsConstructor;


import java.util.Scanner;

@RequiredArgsConstructor
public class ReadAllUsersMenuActions implements MenuActions {
    private Scanner scanner = new Scanner(System.in);


    public void doAction(ContactsService contactsService) {
        try {
            System.out.println(contactsService.getAllUsers());
        } catch (IllegalContactsServiceMethodAccessException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return "Read all Users";
    }


    public boolean closeAfter() {
        System.out.println("Do you want to close program?(1 - yes,2 - no)");

        switch (scanner.nextInt()) {
            case 1:
                return true;
            case 2:
                return false;
            default:
                System.out.println("Oh, no! Something went wrong.");
                System.out.println("Emergency termination of the program");
                return true;
        }
    }

    @Override
    public boolean isVisible(ContactsService contactsService) {
        if (contactsService.getServiceType().equals(ContactsService.ServiceType.HTTP_PLUS_JSON)) {
            return true;
        } else return contactsService.getServiceType().equals(ContactsService.ServiceType.DATABASE);
    }
}