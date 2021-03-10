package com.company.service;

import com.company.dto.*;
import com.company.exceptions.IllegalContactsServiceMethodAccessException;

import java.util.List;

public interface ContactsService {

    void add(Contact contact);

    List<Contact> search(String string, String type);

    List<Contact> getAllContacts();

    List<User> getAllUsers() throws IllegalContactsServiceMethodAccessException;

    void logOut() throws IllegalContactsServiceMethodAccessException;

    LoginResponse login(LoginRequest loginRequest) throws IllegalContactsServiceMethodAccessException;

    RegisterResponse register(RegisterRequest registerRequest) throws IllegalContactsServiceMethodAccessException;

    void remove(int index) throws IllegalContactsServiceMethodAccessException;

    void printToFile() throws IllegalContactsServiceMethodAccessException;

    ServiceType getServiceType();

    enum ServiceType {
        IN_MEMORY,
        NIO,
        HTTP_PLUS_JSON,
        DATABASE
    }
}