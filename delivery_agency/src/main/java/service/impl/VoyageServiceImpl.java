package service.impl;

import dto.VoyageDto;
import entity.Voyage;
import repository.VoyageRepository;
import repository.impl.VoyageRepositoryImpl;
import service.VoyageService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.ConvertUtil.fromDtoToVoyage;
import static util.ConvertUtil.fromVoyageToDTO;

public class VoyageServiceImpl implements VoyageService {
    private final VoyageRepository voyageRepository = new VoyageRepositoryImpl();

    @Override
    public VoyageDto addVoyage(VoyageDto voyage) {
        Voyage save = voyageRepository.save(fromDtoToVoyage(voyage));
        return fromVoyageToDTO(save);
    }

    @Override
    public void deleteVoyage(Long id) {
        voyageRepository.delete(id);
    }

    @Override
    public List<VoyageDto> findAllVoyages() {
        List<Voyage> voyages = voyageRepository.findAll();
        List<VoyageDto> voyageDtos = new ArrayList<>();

        for (Voyage voyage : voyages) {
            voyageDtos.add(fromVoyageToDTO(voyage));
        }

        return voyageDtos;
    }

    @Override
    public VoyageDto getVoyage(long id) {
        final Voyage voyage = voyageRepository.findOne(id);
        return fromVoyageToDTO(voyage);
    }

    @Override
    public VoyageDto update(VoyageDto voyage) throws SQLException {
        Voyage update = voyageRepository.update(fromDtoToVoyage(voyage));
        return fromVoyageToDTO(update);
    }


}