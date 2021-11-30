package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.ParcelParametersDto;
import org.jazzteam.eltay.gasimov.entity.ParcelParameters;
import org.jazzteam.eltay.gasimov.repository.ParcelParametersRepository;
import org.jazzteam.eltay.gasimov.service.ParcelParametersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelParametersServiceImpl implements ParcelParametersService {
    @Autowired
    private ParcelParametersRepository parcelParametersRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ParcelParameters findById(Long idForFind) {
        Optional<ParcelParameters> foundOptional = parcelParametersRepository.findById(idForFind);
        return foundOptional.orElseGet(ParcelParameters::new);
    }

    @Override
    public List<ParcelParameters> findAll() {
        return parcelParametersRepository.findAll();
    }

    @Override
    public void delete(Long idForDelete) {
        parcelParametersRepository.deleteById(idForDelete);
    }

    @Override
    public ParcelParameters save(ParcelParametersDto parcelParametersToSave) {
        return parcelParametersRepository.save(modelMapper.map(parcelParametersToSave, ParcelParameters.class));
    }
}
