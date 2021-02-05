package com.company.service;

import com.company.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class HttpPlusJsonContactsService implements ContactsService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClientHelper httpClientHelper = new HttpClientHelper();
    private String token;

    public String register(RegisterRequest registerRequest) {
        try {
            String req = objectMapper.writeValueAsString(registerRequest);
            String uri = "https://mag-contacts-api.herokuapp.com/register";
            HttpResponse<String> response = httpClientHelper.sendPostRequest
                    (uri, req);
            RegisterResponse registerResponse = objectMapper.readValue(response.body(),
                    RegisterResponse.class);
            return registerResponse.toString();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            String req = objectMapper.writeValueAsString(loginRequest);
            String uri = "https://mag-contacts-api.herokuapp.com/login";
            HttpResponse<String> response = httpClientHelper.sendPostRequest(uri, req);
            LoginResponse loginResponse = objectMapper.readValue(response.body(),
                    LoginResponse.class);
            token = loginResponse.getToken();
            return loginResponse;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;


    }


    public List<User> getAllUsers() {
        HttpResponse<String> response;
        try {
            if (Objects.nonNull(token)) {
                response = httpClientHelper.sendTokenGetRequest
                        ("https://mag-contacts-api.herokuapp.com/users2", token);
            } else {
                response = httpClientHelper.sendGetRequest
                        ("https://mag-contacts-api.herokuapp.com/users2");
            }
            UserResponse userResponse = objectMapper.readValue(response.body(),
                    UserResponse.class);
            return userResponse.getUsers();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void add(Contact contact) {
        try {
            String req = objectMapper.writeValueAsString(contact);
            String uri = "https://mag-contacts-api.herokuapp.com/contacts/add";
            HttpResponse<String> response = httpClientHelper.sendTokenPostRequest(uri, req, token);
            ContactResponse contactResponse = objectMapper.readValue(response.body(),
                    ContactResponse.class);
            System.out.println(contactResponse.getStatus());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    public List<Contact> search(String string, String type) {

        try {
            String req = objectMapper.writeValueAsString(new ContactRequest(string, type));
            String uri = "https://mag-contacts-api.herokuapp.com/contacts/find";
            HttpResponse<String> response = httpClientHelper.sendTokenPostRequest(uri, req, token);
            ContactResponse contactResponse = objectMapper.readValue(response.body(),
                    ContactResponse.class);
            return contactResponse.getContacts();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public List<Contact> getAllContacts() {

        try {
            HttpResponse<String> response = httpClientHelper.sendTokenGetRequest
                    ("https://mag-contacts-api.herokuapp.com/contacts", token);
            ContactResponse contactResponse = objectMapper.readValue(response.body(),
                    ContactResponse.class);
            return contactResponse.getContacts();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void logOut() {
        token = null;
    }

}
