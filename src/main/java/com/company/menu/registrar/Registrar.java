package com.company.menu.registrar;

import com.company.exceptions.IllegalRegistrarMethodAccessException;
import com.company.menu.actions.MenuActions;
import com.company.service.ContactsService;

import java.util.List;

public interface Registrar {
    List<MenuActions> defineMenuActions() throws IllegalRegistrarMethodAccessException;
    List<MenuActions> defineMenuActions(ContactsService contactsService) throws IllegalRegistrarMethodAccessException;
}
