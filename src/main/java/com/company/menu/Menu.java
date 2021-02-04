package com.company.menu;


import com.company.menu.actions.ReadAllUsersMenuActions;
import com.company.service.ContactsService;
import com.company.menu.actions.MenuActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.http.HttpClient;
import java.util.Scanner;

@Data
@AllArgsConstructor
public class Menu {
    private MenuActions[] actions;
    private final Scanner scanner;
    private final ContactsService contactsService;




    public void run() {
        while (true) {
            showMenu();
            int choice = getMenuIndexFromUser();
            if (!validateMenuIndex(choice)) {
                System.out.println("Invalid input");
                continue;
            }


            actions[choice].doAction(contactsService);
            if (actions[choice].closeAfter()) break;
        }
    }


    private boolean validateMenuIndex(int choice) {
        return choice >= 0 && choice < actions.length;
    }

    private int getMenuIndexFromUser() {
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice - 1;
    }


    private void showMenu() {
        for (int i = 0; i < actions.length; i++) {

            System.out.printf("%d - %s\n", (i + 1), actions[i].getName());
        }

    }

}

