package com.br.chriscakes.services;

import com.br.chriscakes.domain.dto.IngredienteDTO;
import com.br.chriscakes.domain.entities.Ingrediente;
import com.br.chriscakes.repositories.IngredientesRepository;
import com.br.chriscakes.services.exceptions.DataBaseException;
import com.br.chriscakes.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredienteService {

    private static final String MSG_RECURSO_NAO_ENCONTRADO = "Ingrediente de id %d não foi encontrado!";

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
        var ingrediente = makeInsertIngrediente(dto);
        ingrediente = repository.save(ingrediente);
        return new IngredienteDTO(ingrediente);
    }

    @Transactional
    public IngredienteDTO update(IngredienteDTO dto, Long id) {
        var ingredienteSalvo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MSG_RECURSO_NAO_ENCONTRADO, id)));
        dto.setId(id);
        var ingrediente = makeUpdateIngrediente(ingredienteSalvo, dto);
        ingrediente = repository.save(ingrediente);
        return new IngredienteDTO(ingrediente);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(String.format(MSG_RECURSO_NAO_ENCONTRADO, id));
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(String.format("Não foi possível deletar o ingrediente de id %d," +
                    " pois o mesmo existe em estoque", id));
        }
    }

    @Transactional
    public void adicionaIngredienteEmEstoque(Long id, Long quantidade) {
        var ingrediente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MSG_RECURSO_NAO_ENCONTRADO, id)));
        Long quantidadeEmEstoque = ingrediente.getQuantidadeEmEstoque();
        ingrediente.setQuantidadeEmEstoque(quantidadeEmEstoque += quantidade);
        repository.save(ingrediente);
    }

    @Transactional
    public void retirarIngredienteEmEstoque(Long id, Long quantidade) {
        var ingrediente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MSG_RECURSO_NAO_ENCONTRADO, id)));
        Long quantidadeEmEstoque = ingrediente.getQuantidadeEmEstoque();
        ingrediente.setQuantidadeEmEstoque(quantidadeEmEstoque -= quantidade);
        repository.save(ingrediente);
    }

    public double getValorTotalIngredienteEmEstoque(Long id) {
        var ingrediente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MSG_RECURSO_NAO_ENCONTRADO, id)
                ));
        return (ingrediente.getQuantidadeEmEstoque() * ingrediente.getValor());
    }

    private Ingrediente makeInsertIngrediente(IngredienteDTO dto) {
        var ingrediente = new Ingrediente();
        ingrediente.setNome(dto.getNome());
        ingrediente.setUnidadeMedida(dto.getUnidadeMedida());
        ingrediente.setQuantidadeMedida(dto.getQuantidadeMedida());
        ingrediente.setQuantidadeEmEstoque(dto.getQuantidadeEmEstoque());
        ingrediente.setValor(dto.getValor());
        return ingrediente;
    }

    private Ingrediente makeUpdateIngrediente(Ingrediente ingrediente, IngredienteDTO dto) {
        ingrediente.setId(dto.getId());
        ingrediente.setNome(dto.getNome());
        ingrediente.setUnidadeMedida(dto.getUnidadeMedida());
        ingrediente.setQuantidadeMedida(dto.getQuantidadeMedida());
        ingrediente.setQuantidadeEmEstoque(dto.getQuantidadeEmEstoque());
        ingrediente.setValor(dto.getValor());
        return ingrediente;
    }
}
