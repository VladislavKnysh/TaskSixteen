package com.company.service;


import com.company.dto.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InMemoryContactsService extends ContactsService {
    private List<Contact> contactsList;

    public InMemoryContactsService() {
        contactsList = new ArrayList<Contact>();
    }

    public InMemoryContactsService(List<Contact> contactsList) {
        this.contactsList = contactsList;
    }

    public ServiceType getServiceType() {
        return ServiceType.IN_MEMORY;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactsList;
    }



    public void remove(int index) {
        contactsList.remove(index);

    }

    @Override
    public void add(Contact contact) {
        contactsList.add(contact);
    }

    @Override
    public List<Contact> search(String string, String type) {
        List<Contact> foundContacts = new ArrayList<>();
        Pattern pattern = Pattern.compile(string.toLowerCase());
        if (type.equalsIgnoreCase("value")) {
            for (Contact c : contactsList) {
                Matcher matcher = pattern.matcher(c.getInfo().toLowerCase());
                while (matcher.find()) {
                    foundContacts.add(c);
                }
            }
        } else {
            for (Contact c : contactsList) {
                Matcher matcher = pattern.matcher(c.getName().toLowerCase());
                while (matcher.find()) {
                    foundContacts.add(c);
                }
            }
        }

        return foundContacts;
    }

    public void printToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("contacts.txt"))) {
            for (int i = 0; i < contactsList.size(); i++) {
                String type;
                if (contactsList.get(i).getType().equalsIgnoreCase("email")) {
                    type = "Email";
                } else {
                    type = "Phone";
                }
                writer.write(contactsList.get(i).getName() +
                        "[" + type + ": " + contactsList.get(i).getInfo() + "]\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<User> getAllUsers() throws IllegalAccessException {
        throw new IllegalAccessException();

    }

    @Override
    public void logOut() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @Override
    public String register(RegisterRequest registerRequest) throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}