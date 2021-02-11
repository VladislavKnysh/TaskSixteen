package com.company.menu;

import com.company.dto.LoginRequest;
import com.company.dto.RegisterRequest;
import com.company.menu.actions.*;
import com.company.menu.actions.LogOutMenuAction;
import com.company.menu.actions.ReadAllUsersMenuActions;
import com.company.service.ContactsService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@RequiredArgsConstructor
public class StartScreen {
    private Scanner scanner = new Scanner(System.in);
    private List<MenuActions> actions = new ArrayList<>();

    private final ContactsService contactsService;

    public void start() {
        if (contactsService.getServiceType().equals(ContactsService.ServiceType.HTTP_PLUS_JSON)) {
            defineHttpMenuActions();
        } else {
            getNonHttpMenuOptions();
        }

        Menu menu = new Menu(actions, scanner, contactsService);
        menu.run();

    }

    private void defineHttpMenuActions() {
        System.out.println("LogIn or Register: 1 - LogIn, 2 - Register");
        switch (scanner.nextInt()) {
            case 1:
                scanner.nextLine();
                login();
                break;
            case 2:
                scanner.nextLine();
                if (register()) {

                    login();

                    break;
                } else {
                    System.out.println("You are entering as guest");
                    actions.add(new ReadAllUsersMenuActions());
                    break;
                }
            default:
                System.out.println("You are entering as guest");
                actions.add(new ReadAllUsersMenuActions());
                break;
        }
    }

    private void login() {
        try {
            if (Objects.nonNull(contactsService.login(askForLogin()).getError())) {
                System.out.println("Oh, no! Something went wrong.");
                System.out.println("You are entering as guest");
                actions.add(new ReadAllUsersMenuActions());
            } else {
                actions.add(new AddContactMenuAction());
                actions.add(new ReadAllContactsMenuActions());
                actions.add(new ReadAllUsersMenuActions());
                actions.add(new SearchContactMenuAction());
                actions.add(new LogOutMenuAction());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean register() {
        try {
            if (Objects.nonNull(contactsService.register(askForRegister()).getStatus())) {
                return true;
            } else return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    private RegisterRequest askForRegister() {
        System.out.println("Enter your login");
        String login = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();
        System.out.println("Enter your birth date");
        String birthDate = scanner.nextLine();
        return new RegisterRequest(login, password, birthDate);
    }

    private LoginRequest askForLogin() {
        System.out.println("Enter your login");
        String login = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();
        return new LoginRequest(login, password);
    }

    private void getNonHttpMenuOptions() {
        actions.add(new AddContactMenuAction());
        actions.add(new ReadAllContactsMenuActions());
        actions.add(new RemoveContactsMenuActions());
        actions.add(new SearchContactMenuAction());
        actions.add(new ExportContactsInFileMenuActions());
    }

}