package com.company.service.factory;

import com.company.service.ContactsService;
import com.company.service.implementation.DataBaseContactsService;

public class DataBaseContactsServiceFactory implements ContactsServiceFactory{
    @Override
    public ContactsService create() {
        return new DataBaseContactsService();
    }
}
