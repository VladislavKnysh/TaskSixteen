package com.company.menu.actions;



import com.company.exceptions.IllegalContactsServiceMethodAccessException;
import com.company.service.ContactsService;

import java.util.Scanner;

public class RemoveContactsMenuActions implements MenuActions {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void doAction(ContactsService contactsService) {
        System.out.println("Enter index to delete");
        int index = scanner.nextInt();

        scanner.nextLine();

        try {


        if (contactsService.getAllContacts().isEmpty()) {
            System.out.println("There is no contacts to delete.");
        } else if (index >= contactsService.getAllContacts().size()) {
            contactsService.remove(contactsService.getAllContacts().size() - 1);
        } else {
            contactsService.remove(index);
        }

    } catch (IllegalContactsServiceMethodAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "Remove contact";
    }

    @Override
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
        return !contactsService.getServiceType().equals(ContactsService.ServiceType.HTTP_PLUS_JSON);
    }
}
