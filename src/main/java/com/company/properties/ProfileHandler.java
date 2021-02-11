package com.company.properties;

import com.company.config.FileNameProperty;
import com.company.config.AppProperties;
import com.company.service.ContactsNioService;
import com.company.service.ContactsService;
import com.company.service.HttpPlusJsonContactsService;
import com.company.service.InMemoryContactsService;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProfileHandler {
    private AppProperties appProperties = new AppProperties();

    public void setProfile() {

        PropertiesManager propertiesManager = new PropertiesManager();
        String profile = propertiesManager.getSystemProp(FileNameProperty.class).getProfile();
        if (profile.equals("dev")) {
            appProperties = propertiesManager.getFileProp(appProperties.getClass(), "app-dev.properties");
        } else if (profile.equals("prod")) {
            appProperties = propertiesManager.getFileProp(appProperties.getClass(), "app-prod.properties");
        } else {
            System.exit(0);
        }

        System.setProperty("api.base-uri", appProperties.getUri());
        System.setProperty("file.path", appProperties.getFileName());

    }

    public ContactsService chooseMode() {
        switch (appProperties.getWorkMode()) {
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
