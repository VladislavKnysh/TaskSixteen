package com.company.properties;

import com.company.config.AppProperties;
import com.company.service.ContactsNioService;
import com.company.service.ContactsService;
import com.company.service.HttpPlusJsonContactsService;
import com.company.service.InMemoryContactsService;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ModeHandler {

    public ContactsService chooseMode() {
        PropertiesManager propertiesManager = new PropertiesManager();
        String workMode = propertiesManager.getSystemProp(AppProperties.class).getWorkMode();
        switch (workMode) {
            case "api":
                return new HttpPlusJsonContactsService();
            case "file":
                return new ContactsNioService();
            case "memory":
                return new InMemoryContactsService();
            default:
                System.exit(0);
                throw new RuntimeException();
        }
    }
}