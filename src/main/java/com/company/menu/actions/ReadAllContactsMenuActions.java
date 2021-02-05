package com.company.menu.actions;

import com.company.service.ContactsService;

import java.util.Scanner;

public class ReadAllContactsMenuActions implements MenuActions{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void doAction(ContactsService contactsService) {
        System.out.println(contactsService.getAllContacts());
    }

    @Override
    public String getName() {
        return "Read all Contacts";
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
}
