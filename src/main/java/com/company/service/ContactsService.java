package com.company.service;

import com.company.dto.*;

import java.util.List;

public interface ContactsService {

    void add(Contact contact);

    List<Contact> search(String string, String type);

    List<Contact> getAllContacts();

    List<User> getAllUsers() throws IllegalAccessException;

    void logOut() throws IllegalAccessException;

    LoginResponse login(LoginRequest loginRequest) throws IllegalAccessException;

    RegisterResponse register(RegisterRequest registerRequest) throws IllegalAccessException;

    void remove(int index) throws IllegalAccessException;

    void printToFile() throws IllegalAccessException;

    ServiceType getServiceType();

    enum ServiceType {
        IN_MEMORY,
        NIO,
        HTTP_PLUS_JSON
    }
}