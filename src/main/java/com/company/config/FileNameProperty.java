package com.company.config;

import com.company.annotstions.SystemProp;
import lombok.Data;

@Data
public class FileNameProperty {

    @SystemProp("contactbook.profile")
    private String profile;



}
