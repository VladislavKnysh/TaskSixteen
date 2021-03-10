package com.company.properties;

import com.company.service.factory.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class FactoryHandler {

    public ContactsServiceFactory determineFactory
            (ContactsServiceFactory.FactoryType factoryType) {
        return factoryType.getContactsServiceFactory();
    }
}


