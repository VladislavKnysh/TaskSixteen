package com.company.service.helper.sql.mappers;

import com.company.dto.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setLogin(resultSet.getString("contacts_user_login"));
        user.setDateBorn(resultSet.getString("contacts_user_dateborn"));
        return user;
    }
}
