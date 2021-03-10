package com.company.service.implementation;

import com.company.annotstions.CreateIfMode;
import com.company.dto.*;
import com.company.exceptions.IllegalContactsServiceMethodAccessException;
import com.company.exceptions.IllegalContactSearchExceptions;
import com.company.service.ContactsService;
import com.company.service.helper.sql.HikariDataSourceFactory;
import lombok.Data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Data
@CreateIfMode("database")
public class DataBaseContactsService implements ContactsService {

    private DataSource dataSource;
    private DbUser dbUser;
    private final DbUser DEFAULT_USER = new DbUser("default_user");

    public DataBaseContactsService() {
        HikariDataSourceFactory hikariDataSourceFactory = new HikariDataSourceFactory();
        dataSource = hikariDataSourceFactory.create();
        dbUser = DEFAULT_USER;
    }

    @Override
    public void add(Contact contact) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO contacts (contact_name, contact_info, contact_type, contact_user_id) " +
                             "VALUES (?,?," +
                             "(SELECT contact_type_id FROM contact_type WHERE contact_type_name = ?)," +
                             "(SELECT contacts_user_id FROM contacts_users WHERE contacts_user_login = ?));")) {

            statement.setString(1, contact.getName());
            statement.setString(2, contact.getInfo());
            statement.setString(3, contact.getType());
            statement.setString(4, dbUser.getName());
            statement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    @Override
    public List<Contact> search(String string, String type) {
        String searchString = getSearchString(string, type);
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT contact_name, contact_info, contact_type FROM contacts WHERE contact_user_id = " +
                             "(SELECT contacts_user_id FROM contacts_users WHERE contacts_user_login =  ?)" +
                             "AND " + searchString)) {
            statement.setString(1, dbUser.getName());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setName(resultSet.getString("contact_name"));
                contact.setInfo(resultSet.getString("contact_info"));
                contact.setType(resultSet.getString("contact_type"));
                contacts.add(contact);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return contacts;
    }

    private String getSearchString(String string, String type) {
        try {
            if (type.equals("name")) {
                return "contact_name ILIKE '%" + string + "%';";
            } else if (type.equals("value")) {
                return "contact_info ILIKE '%" + string + "%';";
            }
            throw new IllegalContactSearchExceptions();
        } catch (IllegalContactSearchExceptions e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT contact_name,contact_type , contact_info  FROM contacts WHERE contact_user_id =" +
                             " (SELECT contacts_user_id FROM contacts_users WHERE contacts_user_login =  ? );")) {
            statement.setString(1, dbUser.getName());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setName(resultSet.getString("contact_name"));
                contact.setInfo(resultSet.getString("contact_info"));

                contact.setType(getTypeName((resultSet.getString("contact_type"))));
                contacts.add(contact);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return contacts;
    }


    @Override
    public void remove(int index) {
        List<Contact> contacts = getAllContacts();
        Contact contact = contacts.get(index - 1);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM contacts WHERE contact_name= ? " +
                             "AND contact_info = ? AND contact_type = ? AND contact_user_id =" +
                             "(SELECT contacts_user_id FROM contacts_users WHERE contacts_user_login = ?);"
             )) {
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getInfo());
            statement.setInt(3, getTypeIndex(contact.getType()));
            statement.setString(4, dbUser.getName());
            statement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT contacts_user_login,contacts_user_dateborn FROM contacts_users;")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();

                user.setLogin(resultSet.getString("contacts_user_login"));
                user.setDateBorn(resultSet.getString("contacts_user_dateborn"));
                userList.add(user);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return userList;
    }

    @Override
    public void logOut() {
        dbUser = DEFAULT_USER;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        if (searchForDbUser(loginRequest.getLogin(), loginRequest.getPassword())) {
            loginResponse.setStatus("ok");
            loginResponse.setToken(loginRequest.getLogin());
            dbUser.setName(loginResponse.getToken());
        } else loginResponse.setError("error");
        return loginResponse;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO contacts_users " +
                             "(contacts_user_login, contacts_user_password, contacts_user_dateborn) " +
                             "VALUES (?,?,?);")) {

            statement.setString(1, registerRequest.getLogin());
            statement.setString(2, registerRequest.getPassword());
            statement.setString(3, registerRequest.getDateBorn());
            statement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        RegisterResponse registerResponse = new RegisterResponse();
        if (searchForDbUser(registerRequest.getLogin(), registerRequest.getPassword())) {
            registerResponse.setStatus("ok");
        } else {
            registerResponse.setError("error");
        }
        return registerResponse;

    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.DATABASE;
    }

    private boolean searchForDbUser(String login, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT contacts_user_login,contacts_user_password FROM contacts_users " +
                             "WHERE contacts_user_login = ? AND contacts_user_password = ?;")) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException sqlException) {
            sqlException.getStackTrace();
        }
        return false;

    }

    private int getTypeIndex(String type) {
        if (type.equalsIgnoreCase("phone")) {
            return 1;
        } else if (type.equalsIgnoreCase("email")) {
            return 2;
        } else throw new IllegalStateException();
    }

    private String getTypeName(String type) {
        if (type.equals("1")) {
            return "phone";
        } else if (type.equals("2")) {
            return "email";
        } else throw new IllegalStateException();
    }


    @Override
    public void printToFile()
            throws IllegalContactsServiceMethodAccessException {
        throw new IllegalContactsServiceMethodAccessException();
    }
}
