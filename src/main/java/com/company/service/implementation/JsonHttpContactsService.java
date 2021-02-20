package com.company.service.implementation;

import com.company.annotstions.CreateIfMode;
import com.company.dto.*;

import com.company.service.ContactsService;
import com.company.service.helper.http.HttpRequestFactory;
import com.company.service.helper.http.JsonHttpRequestFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor
@Data
@CreateIfMode("api")
public class JsonHttpContactsService implements ContactsService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpRequestFactory jsonRequestFactory = new JsonHttpRequestFactory();
    private String token;

    private String  baseUri = System.getProperty("api.base-uri");

    public RegisterResponse register(RegisterRequest registerRequest) {
        try {
            String req = objectMapper.writeValueAsString(registerRequest);
            String uri =baseUri+ "/register";
            HttpResponse<String> response = jsonRequestFactory.sendPostRequest
                    (uri, req);
            RegisterResponse registerResponse = objectMapper.readValue(response.body(),
                    RegisterResponse.class);
            return registerResponse;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    public ServiceType getServiceType() {
        return ServiceType.HTTP_PLUS_JSON;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            String req = objectMapper.writeValueAsString(loginRequest);
            String uri = baseUri+ "/login";
            HttpResponse<String> response = jsonRequestFactory.sendPostRequest(uri, req);
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
                response = jsonRequestFactory.sendTokenGetRequest
                        (baseUri+ "/users2", token);
            } else {
                response = jsonRequestFactory.sendGetRequest
                        (baseUri+ "/users2");
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
            String uri = baseUri+ "/contacts/add";
            HttpResponse<String> response = jsonRequestFactory.sendTokenPostRequest(uri, req, token);
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
            String uri = baseUri+ "/contacts/find";
            HttpResponse<String> response = jsonRequestFactory.sendTokenPostRequest(uri, req, token);
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
            HttpResponse<String> response = jsonRequestFactory.sendTokenGetRequest
                    (baseUri+ "/contacts", token);
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

    @Override
    public void remove(int index) throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @Override
    public void printToFile() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}



