package com.company.service.factory;

import com.company.service.ContactsService;

public interface ContactsServiceFactory {
    ContactsService create();
    enum FactoryType {
        MEMORY,
        NIO,
        HTTP_PLUS_JSON,
        PROP
    }
}
