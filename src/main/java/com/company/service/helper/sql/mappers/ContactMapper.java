package com.company.service.helper.sql.mappers;

import com.company.dto.Contact;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        contact.setName(resultSet.getString("contact_name"));
        contact.setInfo(resultSet.getString("contact_info"));
        contact.setType(resultSet.getString("contact_type_name"));
        return contact;
    }
}
