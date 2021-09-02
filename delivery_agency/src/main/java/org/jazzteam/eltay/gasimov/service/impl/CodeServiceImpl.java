package org.jazzteam.eltay.gasimov.service.impl;

import lombok.extern.java.Log;
import org.jazzteam.eltay.gasimov.dto.ClientsCodeDto;
import org.jazzteam.eltay.gasimov.entity.Client;
import org.jazzteam.eltay.gasimov.entity.ClientsCode;
import org.jazzteam.eltay.gasimov.repository.CodeRepository;
import org.jazzteam.eltay.gasimov.service.CodeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log
public class CodeServiceImpl implements CodeService {
    @Autowired
    private CodeRepository codeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ClientsCode findByClientId(Client clientFroFind) {
        return codeRepository.findByClient(clientFroFind);
    }

    @Override
    public Optional<ClientsCode> findById(Long idForFind) {
        return codeRepository.findById(idForFind);
    }

    @Override
    public List<ClientsCode> findAll(Long idForFind) {
        return codeRepository.findAll();
    }

    @Override
    public void delete(String code) {
        codeRepository.delete(codeRepository.findByGeneratedCode(code));
    }

    @Override
    public ClientsCode save(ClientsCodeDto clientsCode) {
        final ClientsCode save = codeRepository.save(modelMapper.map(clientsCode, ClientsCode.class));
        log.severe(save.toString());
        return save;
    }

    @Override
    public ClientsCode findByCode(String code) {
        return codeRepository.findByGeneratedCode(code);
    }
}
