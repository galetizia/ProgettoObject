package dao;

import model.Giudice;

public interface IGiudiceDAO {

    Giudice login(String username, String password);

    void saveCommento(String commento, Integer id, Giudice giudice);

    boolean haCommentatoAggiornamento(Integer id, Giudice giudice);
}
