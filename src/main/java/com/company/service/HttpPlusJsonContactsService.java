package com.company.service;

import com.company.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class HttpPlusJsonContactsService implements ContactsService {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private String token;

    public String register(RegisterRequest registerRequest) {
        try {
            String req = objectMapper.writeValueAsString(registerRequest);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://mag-contacts-api.herokuapp.com/register"))
                    .POST(HttpRequest.BodyPublishers.ofString(req))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://mag-contacts-api.herokuapp.com/login"))
                    .POST(HttpRequest.BodyPublishers.ofString(req))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
        HttpRequest request;
        if (Objects.nonNull(token)) {
            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://mag-contacts-api.herokuapp.com/users2"))
                    .GET()
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .build();
        } else {
            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://mag-contacts-api.herokuapp.com/users"))
                    .GET()
                    .header("Accept", "application/json")
                    .build();
        }
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://mag-contacts-api.herokuapp.com/contacts/add"))
                    .POST(HttpRequest.BodyPublishers.ofString(req))
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://mag-contacts-api.herokuapp.com/contacts/find"))
                    .POST(HttpRequest.BodyPublishers.ofString(req))
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://mag-contacts-api.herokuapp.com/contacts"))
                .GET()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ContactResponse contactResponse = objectMapper.readValue(response.body(),
                    ContactResponse.class);
            return contactResponse.getContacts();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void logOut(){
        token = null;
    }

}
