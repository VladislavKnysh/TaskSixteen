package com.company.properties;

import com.company.service.ContactsService;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
public class ProjectStarter {
    private ProfileHandler ph = new ProfileHandler();
    private ModeHandler mh = new ModeHandler();
    public ContactsService startProject(){
        ph.setProfile();
        return mh.getService(ContactsService.class);
    }
}
