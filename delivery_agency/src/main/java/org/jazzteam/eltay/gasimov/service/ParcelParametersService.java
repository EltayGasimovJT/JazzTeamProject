package org.jazzteam.eltay.gasimov.service;

import org.jazzteam.eltay.gasimov.dto.ParcelParametersDto;
import org.jazzteam.eltay.gasimov.entity.ParcelParameters;

import java.util.List;

public interface ParcelParametersService {
    ParcelParameters findById(Long idForFind);

    List<ParcelParameters> findAll();

    void delete(Long idForDelete);

    ParcelParameters save(ParcelParametersDto parcelParametersToSave);
}
