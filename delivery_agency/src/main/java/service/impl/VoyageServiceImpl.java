package service.impl;

import dto.VoyageDto;
import entity.Voyage;
import mapping.OrderMapper;
import repository.VoyageRepository;
import repository.impl.VoyageRepositoryImpl;
import service.VoyageService;
import validator.VoyageValidator;

import java.util.List;
import java.util.stream.Collectors;

public class VoyageServiceImpl implements VoyageService {
    private final VoyageRepository voyageRepository = new VoyageRepositoryImpl();

    @Override
    public Voyage save(VoyageDto voyageDtoToSave) throws IllegalArgumentException {
        Voyage voyageToSave = new Voyage();
        voyageToSave.setId(voyageDtoToSave.getId());
        voyageToSave.setExpectedOrders(voyageDtoToSave.getExpectedOrders().stream()
                .map(OrderMapper::toOrder)
                .collect(Collectors.toList()));
        voyageToSave.setDispatchedOrders(voyageDtoToSave.getDispatchedOrders().stream()
                .map(OrderMapper::toOrder)
                .collect(Collectors.toList()));
        voyageToSave.setDeparturePoint(voyageDtoToSave.getDeparturePoint());
        voyageToSave.setDestinationPoint(voyageDtoToSave.getDestinationPoint());
        voyageToSave.setSendingTime(voyageDtoToSave.getSendingTime());
        VoyageValidator.validateOnSave(voyageToSave);

        return voyageRepository.save(voyageToSave);
    }

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        VoyageValidator.validateVoyage(voyageRepository.findOne(idForDelete));
        voyageRepository.delete(idForDelete);
    }

    @Override
    public List<Voyage> findAll() throws IllegalArgumentException {
        List<Voyage> voyagesFromRepository = voyageRepository.findAll();
        VoyageValidator.validateVoyageList(voyagesFromRepository);
        return voyagesFromRepository;
    }

    @Override
    public Voyage findOne(long idForSearch) throws IllegalArgumentException {
        Voyage foundVoyage = voyageRepository.findOne(idForSearch);
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

        return voyageRepository.update(voyageToUpdate);
    }
}