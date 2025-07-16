package dao;

import model.Giudice;

public interface IGiudiceDAO {

    Giudice login(String username, String password);

}
