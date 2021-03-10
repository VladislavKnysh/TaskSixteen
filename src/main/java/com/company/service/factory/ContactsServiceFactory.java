package com.company.service.factory;

import com.company.service.ContactsService;
import lombok.Getter;

public interface ContactsServiceFactory {
    ContactsService create();

    @Getter
    enum FactoryType {
        MEMORY(new InMemoryContactsServiceFactory()),
        NIO(new NioContactsServiceFactory()),
        HTTP_PLUS_JSON(new JsonHttpContactsServiceFactory()),
        DATABASE(new DataBaseContactsServiceFactory()),
        PROP(new PropertyContactsServiceFactory());

        ContactsServiceFactory contactsServiceFactory;

        FactoryType(ContactsServiceFactory contactsServiceFactory) {
            this.contactsServiceFactory = contactsServiceFactory;
        }
    }

}
