package com.company.menu.actions;

import com.company.service.ContactsService;

import java.util.Scanner;

public class LogOutMenuAction implements MenuActions{
    private Scanner scanner = new Scanner(System.in);
    @Override
    public void doAction(ContactsService contactsService) {
        contactsService.logOut();
    }

    @Override
    public String getName() {
        return "Log Out";
    }


    public boolean closeAfter() {
        System.out.println("You were disconnected from the server. You need to log in again to continue.");
        return true;
        }
    }

