package com.company.menu.actions;

import com.company.dto.Contact;
import com.company.service.ContactsService;

import java.util.Scanner;

public class AddContactMenuAction implements MenuActions {
    private Scanner scanner = new Scanner(System.in);

    public void doAction(ContactsService contactsService) {
        String name = askForName();
        String info = askForInfo();
        String type = askForType();
    contactsService.add(new Contact(name, info, type));
    }
    private String askForName() {
        System.out.println("Enter name of contact");
        return scanner.nextLine();
    }



    private String askForInfo() {
        System.out.println("Enter info for contact");
        return scanner.nextLine();
    }

    private String askForType() {
        System.out.println("Enter type of your contact: 1 - Phone, 2 - Email");
        switch (scanner.nextInt()) {
            case 1:
                return "phone";
            case 2:
                return "email";
            default:
                System.out.println("Oh, no! Something went wrong.");
                System.out.println("Emergency termination of the program");
                return null;
    }}

    public String getName() {
        return "Add contact";
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
        return true;
    }
}

