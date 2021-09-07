package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.VoyageDto;
import org.jazzteam.eltay.gasimov.entity.Voyage;

import java.util.List;

public interface VoyageService {
    Voyage save(VoyageDto voyageDtoToSave);

    void delete(Long idForDelete);

    List<Voyage> findAll();

    Voyage findOne(long idForSearch);

    Voyage update(VoyageDto voyageToUpdate);
}