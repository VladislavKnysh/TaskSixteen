package com.company.menu.actions;

import com.company.service.ContactsService;

import java.util.Scanner;

public class SearchContactMenuAction implements MenuActions {
    private Scanner scanner = new Scanner(System.in);


    public void doAction(ContactsService contactsService) {
        String userString =  getUsersString();
        String type = askForTypeOfSearch();
        System.out.println(contactsService.search(userString, type));
    }


    public String getName() {
        return "Perform search";
    }


    public boolean closeAfter() {
        {
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

    private String askForTypeOfSearch() {
        System.out.println("Enter type of your search: 1 - Value, 2 - Name");
        switch (scanner.nextInt()) {
            case 1:
                return "value";
            case 2:
                return "name";
            default:
                System.out.println("Oh, no! Something went wrong.");
                System.out.println("Emergency termination of the program");
                return null;
        }}

    private String getUsersString() {
        System.out.println("Enter your search request: ");
        return scanner.nextLine();
    }


}
