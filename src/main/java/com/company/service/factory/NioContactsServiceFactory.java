package com.company.service.factory;

import com.company.service.ContactsService;
import com.company.service.implementation.NioContactsService;

public class NioContactsServiceFactory implements ContactsServiceFactory {
    @Override
    public ContactsService create() {
        return new NioContactsService();
    }
}
