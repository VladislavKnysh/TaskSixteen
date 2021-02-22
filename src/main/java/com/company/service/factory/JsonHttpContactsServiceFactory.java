package com.company.service.factory;

import com.company.service.ContactsService;
import com.company.service.implementation.JsonHttpContactsService;

public class JsonHttpContactsServiceFactory implements ContactsServiceFactory {
    @Override
    public ContactsService create() {
        return new JsonHttpContactsService();
    }
}
