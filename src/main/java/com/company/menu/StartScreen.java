package com.company.menu;

import com.company.dto.LoginRequest;
import com.company.dto.RegisterRequest;
import com.company.menu.actions.*;
import com.company.service.ContactsService;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Scanner;

@RequiredArgsConstructor
public class StartScreen {
    private Scanner scanner = new Scanner(System.in);
    private MenuActions[] actions;

    private final ContactsService contactsService;

    public void start() {
        defineMenuActions();


        Menu menu = new Menu(actions, scanner, contactsService);
        menu.run();
    }

    private void defineMenuActions() {
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
                    actions = new MenuActions[]{new ReadAllUsersMenuActions()};
                    break;
                }
            default:
                System.out.println("You are entering as guest");
                actions = new MenuActions[]{new ReadAllUsersMenuActions()};
                break;
        }
    }

    private void login() {
        if (Objects.nonNull(contactsService.login(askForLogin()).getError())) {
            System.out.println("Oh, no! Something went wrong.");
            System.out.println("You are entering as guest");
            actions = new MenuActions[]{new ReadAllUsersMenuActions()};
        } else {
            actions = new MenuActions[]{new AddContactMenuAction(),
                    new ReadAllContactsMenuActions(),
                    new ReadAllUsersMenuActions(),
                    new SearchContactMenuAction(),
                    new LogOutMenuAction()};

        }
    }

    private boolean register() {
        if (Objects.nonNull(contactsService.register(askForRegister()))) {
            return true;
        } else return false;

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

}