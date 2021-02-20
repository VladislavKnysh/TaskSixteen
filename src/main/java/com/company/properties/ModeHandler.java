package com.company.properties;

import com.company.config.AppProperties;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class ModeHandler {


    public String chooseMode() {

        PropertiesManager propertiesManager = new PropertiesManager();
       return propertiesManager.getSystemProp(AppProperties.class).getWorkMode();

    }






}