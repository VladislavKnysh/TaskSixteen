package com.company.service.implementation;

import com.company.annotstions.CreateIfMode;
import com.company.dto.*;
import com.company.exceptions.IllegalContactsServiceMethodAccessException;
import com.company.exceptions.IllegalContactSearchExceptions;
import com.company.service.ContactsService;
import com.company.service.helper.sql.HikariDataSourceFactory;
import com.company.service.helper.sql.JdbcTemplate;
import com.company.service.helper.sql.mappers.ContactMapper;
import com.company.service.helper.sql.mappers.UserMapper;
import lombok.Data;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;


@Data
@CreateIfMode("database")
public class DataBaseContactsService implements ContactsService {

    private DataSource dataSource;
    private DbUser dbUser;
    private final DbUser DEFAULT_USER = new DbUser("default_user");
    private JdbcTemplate jdbcTemplate;

    public DataBaseContactsService() {
        HikariDataSourceFactory hikariDataSourceFactory = new HikariDataSourceFactory();
        dataSource = hikariDataSourceFactory.create();
        dbUser = DEFAULT_USER;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Contact contact) {
        String sql = "INSERT INTO contacts (contact_name, contact_info, contact_type, contact_user_id) " +
                "VALUES (?,?," +
                "(SELECT contact_type_id FROM contact_type WHERE contact_type_name = ?)," +
                "(SELECT contacts_user_id FROM contacts_users WHERE contacts_user_login = ?));";
        jdbcTemplate.update(sql, new Object[]{contact.getName(), contact.getInfo(), contact.getType(), dbUser.getName()});
    }

    @Override
    public List<Contact> search(String string, String type) {
        String searchString = getSearchString(string, type);
        String sql = "SELECT contact_name, contact_info, contact_type_name FROM contacts c inner join " +
                "contact_type ct on c.contact_type = ct.contact_type_id " +
                "WHERE contact_user_id = " +
                "(SELECT contacts_user_id FROM contacts_users WHERE contacts_user_login =  ?)" +
                "AND " + searchString;
        return jdbcTemplate.query(sql, new Object[]{dbUser.getName()}, new ContactMapper());

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
        String sql = "SELECT contact_name, contact_info, contact_type_name FROM contacts c inner join" +
                " contact_type ct on  c.contact_type = ct.contact_type_id WHERE contact_user_id =" +
                " (SELECT contacts_user_id FROM contacts_users WHERE contacts_user_login =  ?);";

        return jdbcTemplate.query(sql, new Object[]{dbUser.getName()}, new ContactMapper());

    }

    @Override
    public void remove(int index) {
        List<Contact> contacts = getAllContacts();
        Contact contact = contacts.get(index - 1);
        String sql = "DELETE FROM contacts WHERE contact_name= ? " +
                "AND contact_info = ? " +
                "AND contact_type = (SELECT contact_type_id FROM contact_type WHERE contact_type_name = ?) " +
                "AND contact_user_id =" +
                "(SELECT contacts_user_id FROM contacts_users " +
                "WHERE contacts_user_login = ?);";
        jdbcTemplate.update(sql, new Object[]{contact.getName(), contact.getInfo(), contact.getType(), dbUser.getName()});
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT contacts_user_login,contacts_user_dateborn FROM contacts_users;";

        return jdbcTemplate.query(sql, new UserMapper());
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
        String sql = "INSERT INTO contacts_users " +
                "(contacts_user_login, contacts_user_password, contacts_user_dateborn) " +
                "VALUES (?,?,?);";
        jdbcTemplate.update(sql, new Object[]
                {registerRequest.getLogin(),
                        registerRequest.getPassword(),
                        registerRequest.getDateBorn()});


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
        String sql = "SELECT contacts_user_login,contacts_user_dateborn FROM contacts_users WHERE contacts_user_login = ? AND contacts_user_password = ?;";
        return Objects.nonNull(jdbcTemplate.queryOne(sql, new Object[]{login, password}, new UserMapper()));
    }

    @Override
    public void printToFile()
            throws IllegalContactsServiceMethodAccessException {
        throw new IllegalContactsServiceMethodAccessException();
    }
}
