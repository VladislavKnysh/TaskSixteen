package com.company.service.helper;




import com.company.dto.Contact;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactParser {


    public Contact parse(String user) {
        String name = getData(user, DataPart.NAME);
        String info = getData(user, DataPart.INFO);
        String typeString = getData(user, DataPart.TYPE);
        if (typeString.equalsIgnoreCase("Email")) {
            return new Contact(name, info, "email");
        } else return new Contact(name, info, "phone");


    }



    private String getData(String string, DataPart dataPart) {
        Matcher matcher = dataPart.getPattern().matcher(string);
        matcher.find();
        return matcher.group();
    }


    private enum DataPart {
        NAME("(\\w*\\d*)((?=[\\[]))"),
        TYPE("(?<=[\\[])\\w+(?=[:])"),
        INFO("(?<=: ).(\\S)+(?=[\\]])");
        private final String REGEX;

        DataPart(String regex) {
            this.REGEX = regex;
        }

        Pattern getPattern() {
            return Pattern.compile(REGEX);

        }

    }
}
