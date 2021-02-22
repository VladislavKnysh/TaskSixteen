package com.company.service.implementation;


import com.company.annotstions.CreateIfMode;
import com.company.dto.*;
import com.company.exceptions.IllegalContactsServiceMethodAccessException;
import com.company.service.ContactsService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@CreateIfMode("memory")
public class InMemoryContactsService implements ContactsService {
    private List<Contact> contactsList = new ArrayList<>();

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
            for (Contact contact : contactsList) {
                String type;
                if (contact.getType().equalsIgnoreCase("email")) {
                    type = "Email";
                } else {
                    type = "Phone";
                }
                writer.write(contact.getName() +
                        "[" + type + ": " + contact.getInfo() + "]\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() throws IllegalContactsServiceMethodAccessException {
        throw new IllegalContactsServiceMethodAccessException();

    }

    @Override
    public void logOut() throws IllegalContactsServiceMethodAccessException {
        throw new IllegalContactsServiceMethodAccessException();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws IllegalContactsServiceMethodAccessException {
        throw new IllegalContactsServiceMethodAccessException();
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws IllegalContactsServiceMethodAccessException {
        throw new IllegalContactsServiceMethodAccessException();
    }
}