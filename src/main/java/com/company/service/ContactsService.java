package com.company.service;

import com.company.dto.*;

import java.util.List;

public abstract class ContactsService {

    public abstract void add(Contact contact);

    public abstract List<Contact> search(String string, String type);

    public abstract List<Contact> getAllContacts();

    public abstract List<User> getAllUsers() throws IllegalAccessException;

    public abstract void logOut() throws IllegalAccessException;

    public abstract LoginResponse login(LoginRequest loginRequest) throws IllegalAccessException;

    public abstract RegisterResponse register(RegisterRequest registerRequest) throws IllegalAccessException;

    public abstract void remove(int index) throws IllegalAccessException;

    public abstract void printToFile() throws IllegalAccessException;

    public abstract ServiceType getServiceType();

    public enum ServiceType {
        IN_MEMORY,
        NIO,
        HTTP_PLUS_JSON;
    }
}