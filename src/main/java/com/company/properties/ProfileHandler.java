package com.company.properties;

import com.company.config.FileNameProperty;
import com.company.config.AppProperties;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProfileHandler {


    public void setProfile() {
        AppProperties appProperties = new AppProperties();
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
        System.setProperty("app.service.workmode", appProperties.getWorkMode());
    }



    }

