package com.company.properties;

import com.company.service.ContactsService;
import com.company.service.factory.ContactsServiceFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
public class ProjectStarter {
    private ProfileHandler ph = new ProfileHandler();
    private ModeHandler mh = new ModeHandler();
    private ContactsServiceFactory contactsServiceFactory = new ContactsServiceFactory();
    public ContactsService startProject(){
        ph.setProfile();
        return contactsServiceFactory.createPropertyService(mh.chooseMode());
    }
}
