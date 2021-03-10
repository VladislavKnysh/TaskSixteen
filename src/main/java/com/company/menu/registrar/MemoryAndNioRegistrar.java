package com.company.menu.registrar;

import com.company.exceptions.IllegalRegistrarMethodAccessException;
import com.company.menu.actions.*;
import com.company.service.ContactsService;

import java.util.ArrayList;
import java.util.List;

public class MemoryAndNioRegistrar implements Registrar {
    private final List<MenuActions> actions = new ArrayList<>();

    public List<MenuActions>  defineMenuActions(){
        actions.add(new AddContactMenuAction());
        actions.add(new ReadAllContactsMenuActions());
        actions.add(new RemoveContactsMenuActions());
        actions.add(new SearchContactMenuAction());
        actions.add(new ExportContactsInFileMenuActions());
        return actions;
    }

    @Override
    public List<MenuActions> defineMenuActions(ContactsService contactsService)
            throws IllegalRegistrarMethodAccessException {
        throw new IllegalRegistrarMethodAccessException();
    }
}
