package com.company.menu.registrar;

import com.company.dto.LoginRequest;
import com.company.dto.RegisterRequest;
import com.company.exceptions.IllegalRegistrarMethodAccessException;
import com.company.menu.actions.*;
import com.company.service.ContactsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class DataBaseRegistrar implements Registrar{
    private final Scanner scanner = new Scanner(System.in);
    private final List<MenuActions> actions = new ArrayList<>();
    private final String guestString = "You are entering as guest";

    @Override
    public List<MenuActions> defineMenuActions()
            throws IllegalRegistrarMethodAccessException {
        throw new IllegalRegistrarMethodAccessException();
    }

    public List<MenuActions> defineMenuActions(ContactsService contactsService) {
        System.out.println("LogIn or Register: 1 - LogIn, 2 - Register");
        switch (scanner.nextInt()) {
            case 1:
                scanner.nextLine();
                login(contactsService);
                break;
            case 2:
                scanner.nextLine();
                if (register(contactsService)) {

                    login(contactsService);

                } else {
                    System.out.println(guestString);
                    actions.add(new ReadAllUsersMenuActions());
                }
                break;
            default:
                System.out.println(guestString);
                actions.add(new ReadAllUsersMenuActions());
                break;

        }

        return actions;
    }

    private void login(ContactsService contactsService) {
        try {
            if (Objects.nonNull(contactsService.login(askForLogin()).getError())) {
                System.out.println("Oh, no! Something went wrong.");
                System.out.println(guestString);
                actions.add(new ReadAllUsersMenuActions());
            } else {
                actions.add(new AddContactMenuAction());
                actions.add(new ReadAllContactsMenuActions());
                actions.add(new ReadAllUsersMenuActions());
                actions.add(new RemoveContactsMenuActions());
                actions.add(new SearchContactMenuAction());
                actions.add(new LogOutMenuAction());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean register(ContactsService contactsService) {
        try {
            return Objects.nonNull(contactsService.register(askForRegister()).getStatus());
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
}
