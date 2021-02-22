package com.company.service.factory;

import com.company.service.ContactsService;
import com.company.service.implementation.InMemoryContactsService;

public class InMemoryContactsServiceFactory implements ContactsServiceFactory {
    @Override
    public ContactsService create() {
        return new InMemoryContactsService();
    }
}
