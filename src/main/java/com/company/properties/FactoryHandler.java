package com.company.properties;

import com.company.exceptions.UnknownContactsServiceFactoryTypeException;
import com.company.service.factory.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class FactoryHandler {

    public ContactsServiceFactory determineFactory
            (ContactsServiceFactory.FactoryType factoryType) {
        switch (factoryType) {
            case MEMORY:
                return new InMemoryContactsServiceFactory();
            case NIO:
                return new NioContactsServiceFactory();
            case HTTP_PLUS_JSON:
                return new JsonHttpContactsServiceFactory();
            case PROP:
                return new PropertyContactsServiceFactory();
            default:
                throw new UnknownContactsServiceFactoryTypeException
                        ("Unexpected value: " + factoryType);
        }
    }


}