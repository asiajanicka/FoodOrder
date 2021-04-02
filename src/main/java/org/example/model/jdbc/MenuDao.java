package org.example.model.jdbc;

import org.example.model.menu.Menu;

import java.io.IOException;

public interface MenuDao {
    Menu getMenu() throws IOException;
}
