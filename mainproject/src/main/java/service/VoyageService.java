package service;

import entity.Voyage;

import java.util.List;

public interface VoyageService {
    Voyage addUser(Voyage voyage);

    void deleteUser(Voyage voyage);

    List<Voyage> showUsers();

    Voyage getUser(long id);

    Voyage update(Voyage voyage);
}
