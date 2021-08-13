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
    public VoyageDto save(VoyageDto voyage) {
        return fromVoyageToDTO(voyageRepository.save(fromDtoToVoyage(voyage)));
    }

    @Override
    public void delete(Long id) {
        voyageRepository.delete(id);
    }

    @Override
    public List<VoyageDto> findAll() {
        List<Voyage> voyages = voyageRepository.findAll();
        List<VoyageDto> voyageDtos = new ArrayList<>();

        for (Voyage voyage : voyages) {
            voyageDtos.add(fromVoyageToDTO(voyage));
        }

        return voyageDtos;
    }

    @Override
    public VoyageDto findOne(long id) {
        final Voyage voyage = voyageRepository.findOne(id);
        return fromVoyageToDTO(voyage);
    }

    @Override
    public VoyageDto update(VoyageDto voyage) throws SQLException {
        final Voyage update1 = fromDtoToVoyage(voyage);
        return fromVoyageToDTO(voyageRepository.update(update1));
    }
}