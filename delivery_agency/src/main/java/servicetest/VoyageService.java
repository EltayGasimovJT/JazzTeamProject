package servicetest;

import entity.Voyage;

import java.util.List;

public interface VoyageService {
    Voyage addVoyage(Voyage voyage);

    void deleteVoyage(Voyage voyage);

    List<Voyage> findAllVoyages();

    Voyage getVoyage(long id);

    Voyage update(Voyage voyage);
}
