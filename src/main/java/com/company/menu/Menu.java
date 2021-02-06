package com.company.menu;


import com.company.service.ContactsService;
import com.company.menu.actions.MenuActions;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
@AllArgsConstructor
public class Menu {
    private List<MenuActions> actions;
    private final Scanner scanner;
    private final ContactsService contactsService;


    public void run() {
        ArrayList<MenuActions> actionsToOperate = getVisibleOptions();
        while (true) {
            showMenu(actionsToOperate);
            int choice = getMenuIndexFromUser();
            if (!validateMenuIndex(choice)) {
                System.out.println("Invalid input");
                continue;
            }


            actionsToOperate.get(choice).doAction(contactsService);
            if (actionsToOperate.get(choice).closeAfter()) break;
        }
    }


    private boolean validateMenuIndex(int choice) {
        return choice >= 0 && choice < actions.size();
    }

    private int getMenuIndexFromUser() {
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice - 1;
    }


    private void showMenu(List<MenuActions> actions) {
        for (int i = 0; i < actions.size(); i++) {

            System.out.printf("%d - %s\n", (i + 1), actions.get(i).getName());
        }

    }

    private ArrayList<MenuActions> getVisibleOptions() {
        ArrayList<MenuActions> visibleList = new ArrayList<>();
        for (MenuActions m : actions) {
            if (m.isVisible(contactsService)) {
                visibleList.add(m);
            }

        }

        return visibleList;

    }
}

