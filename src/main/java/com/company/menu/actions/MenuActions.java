package com.company.menu.actions;

import com.company.service.ContactsService;

public interface MenuActions {

    void doAction(ContactsService contactsService);

    String getName();

    boolean closeAfter();
}
