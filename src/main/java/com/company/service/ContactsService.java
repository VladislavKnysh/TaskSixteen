package com.company.service;

import com.company.dto.*;

import java.util.List;

public interface ContactsService {
    List<User> getAllUsers();
    void add(Contact contact);
    List<Contact> search(String string, String type);
    List<Contact> getAllContacts();
    void logOut();
    LoginResponse login(LoginRequest loginRequest);
    String register(RegisterRequest registerRequest);
}