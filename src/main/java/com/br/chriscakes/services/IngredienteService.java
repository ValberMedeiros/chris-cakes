package com.br.chriscakes.services;

import com.br.chriscakes.domain.dto.IngredienteDTO;
import com.br.chriscakes.domain.entities.Ingrediente;
import com.br.chriscakes.repositories.IngredientesRepository;
import com.br.chriscakes.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredienteService {

    private IngredientesRepository repository;

    public IngredienteService(IngredientesRepository repository) {
        this.repository = repository;
    }

    public Page<IngredienteDTO> findAll(Pageable pageable) {
        Page<Ingrediente> ingredientes = repository.findAll(pageable);
        return ingredientes
                .map(IngredienteDTO::new);
    }

    public IngredienteDTO findById(Long id) {
        var ingrediente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Ingrediente com id %d não encontrado!", id)));

        return new IngredienteDTO(ingrediente);
    }

    @Transactional
    public IngredienteDTO insert(IngredienteDTO dto) {
        var ingrediente = makeIngrediente(dto);
        ingrediente = repository.save(ingrediente);
        return new IngredienteDTO(ingrediente);
    }

    private Ingrediente makeIngrediente(IngredienteDTO dto) {
        var ingrediente = new Ingrediente();
        ingrediente.setNome(dto.getNome());
        ingrediente.setUnidadeMedida(dto.getUnidadeMedida());
        ingrediente.setQuantidadeMedida(dto.getQuantidadeMedida());
        ingrediente.setQuantidadeEmEstoque(dto.getQuantidadeEmEstoque());
        return ingrediente;
    }
}
