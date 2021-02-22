package com.company.properties;

import com.company.service.ContactsService;
import com.company.service.factory.ContactsServiceFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProjectStarter {

    private final ContactsServiceFactory.FactoryType factoryType;


    public ContactsService startProject() {
         ProfileHandler ph = new ProfileHandler();
         FactoryHandler fh = new FactoryHandler();
         ContactsServiceFactory contactsServiceFactory = fh.determineFactory(factoryType);

            ph.determineProfile(specify());
        return contactsServiceFactory.create();
    }

    private boolean specify() {
        if (factoryType.equals(ContactsServiceFactory.FactoryType.PROP)) {
            return true;
        } else return false;
    }
}
