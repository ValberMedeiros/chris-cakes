package com.br.chriscakes.services;

import com.br.chriscakes.domain.dto.IngredienteDTO;
import com.br.chriscakes.domain.entities.Ingredientes;
import com.br.chriscakes.repositories.IngredientesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IngredienteService {

    private IngredientesRepository repository;

    public IngredienteService(IngredientesRepository repository) {
        this.repository = repository;
    }

    public Page<IngredienteDTO> findAll(Pageable pageable) {
        Page<Ingredientes> ingredientes = repository.findAll(pageable);
        return ingredientes
                .map(IngredienteDTO::new);
    }
}
