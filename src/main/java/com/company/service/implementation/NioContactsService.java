package com.company.service.implementation;

import com.company.annotstions.CreateIfMode;
import com.company.dto.*;
import com.company.exceptions.IllegalContactsServiceMethodAccessException;
import com.company.service.ContactsService;
import com.company.service.helper.ContactParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
@CreateIfMode("file")
public class NioContactsService implements ContactsService {
    private Path path = Paths.get(System.getProperty("file.path"));

    public ServiceType getServiceType() {
        return ServiceType.NIO;
    }


    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contactsList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(path);
            ContactParser contactParser = new ContactParser();

            for (String line : lines) {
                contactsList.add(contactParser.parse(line));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return contactsList;
    }


    public void remove(int index) {
        try {
            int i = 1;
            StringBuilder stringBuilder = new StringBuilder();
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (i != index) {
                    stringBuilder.append(line).append("\n");

                }
                i++;
            }
            Files.write(path, stringBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void add(Contact contact) {
        String type;
        if (contact.getType().equalsIgnoreCase("Email")) {
            type = "Email";
        } else {
            type = "Phone";
        }
        String contactString = (contact.getName() +
                "[" + type + ": " + contact.getInfo() + "]\n");
        try {
            Files.write(path, contactString.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Contact> search(String string, String type) {
        ContactParser contactParser = new ContactParser();
        List<Contact> foundContacts = new ArrayList<>();
        String patternString;
        if (type.equalsIgnoreCase("value")) {
            patternString = ("((?<=:)).*" + string);
        } else {
            patternString = (string + ".*(?=[\\[])");
        }
        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {

                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {

                    foundContacts.add(contactParser.parse(line));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foundContacts;
    }

    public void printToFile() {
        try {
            Files.copy(path, new File("contacts.txt").toPath());
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
