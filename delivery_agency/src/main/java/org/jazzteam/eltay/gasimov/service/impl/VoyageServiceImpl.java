package org.jazzteam.eltay.gasimov.service.impl;

import org.jazzteam.eltay.gasimov.dto.VoyageDto;
import org.jazzteam.eltay.gasimov.entity.Voyage;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jazzteam.eltay.gasimov.repository.VoyageRepository;
import org.jazzteam.eltay.gasimov.service.VoyageService;
import org.jazzteam.eltay.gasimov.validator.VoyageValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "voyageService")
public class VoyageServiceImpl implements VoyageService {
    @Autowired
    private VoyageRepository voyageRepository;

    @Override
    public Voyage save(VoyageDto voyageDtoToSave) throws IllegalArgumentException {
        Voyage voyageToSave = new Voyage();
        voyageToSave.setId(voyageDtoToSave.getId());
        if (voyageDtoToSave.getExpectedOrders() != null) {
            voyageToSave.setExpectedOrders(voyageDtoToSave.getExpectedOrders().stream()
                    .map(CustomModelMapper::mapDtoToOrder)
                    .collect(Collectors.toList()));
        }
        if (voyageDtoToSave.getDispatchedOrders() != null) {
            voyageToSave.setDispatchedOrders(voyageDtoToSave.getDispatchedOrders().stream()
                    .map(CustomModelMapper::mapDtoToOrder)
                    .collect(Collectors.toList()));
        }
        voyageToSave.setDeparturePoint(voyageDtoToSave.getDeparturePoint());
        voyageToSave.setDestinationPoint(voyageDtoToSave.getDestinationPoint());
        voyageToSave.setSendingTime(voyageDtoToSave.getSendingTime());
        VoyageValidator.validateOnSave(voyageToSave);

        return voyageRepository.save(voyageToSave);
    }

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        Optional<Voyage> foundClientFromRepository = voyageRepository.findById(idForDelete);
        Voyage foundVoyage = foundClientFromRepository.orElseGet(Voyage::new);
        VoyageValidator.validateVoyage(foundVoyage);
        voyageRepository.deleteById(idForDelete);
    }

    @Override
    public List<Voyage> findAll() throws IllegalArgumentException {
        List<Voyage> voyagesFromRepository = voyageRepository.findAll();
        VoyageValidator.validateVoyageList(voyagesFromRepository);
        return voyagesFromRepository;
    }

    @Override
    public Voyage findOne(long idForSearch) throws IllegalArgumentException {
        Optional<Voyage> foundClientFromRepository = voyageRepository.findById(idForSearch);
        Voyage foundVoyage = foundClientFromRepository.orElseGet(Voyage::new);
        VoyageValidator.validateVoyage(foundVoyage);

        return foundVoyage;
    }

    @Override
    public Voyage update(VoyageDto voyage) throws IllegalArgumentException {
        Voyage voyageToUpdate = new Voyage();
        voyageToUpdate.setId(voyage.getId());
        voyageToUpdate.setSendingTime(voyage.getSendingTime());
        voyageToUpdate.setDestinationPoint(voyage.getDestinationPoint());
        voyageToUpdate.setDeparturePoint(voyage.getDeparturePoint());
        VoyageValidator.validateVoyage(voyageToUpdate);

        return voyageRepository.save(voyageToUpdate);
    }
}