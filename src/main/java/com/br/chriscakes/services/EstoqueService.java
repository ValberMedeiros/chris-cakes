package com.br.chriscakes.services;

import com.br.chriscakes.domain.dto.IngredienteDTO;
import com.br.chriscakes.domain.entities.Estoque;
import com.br.chriscakes.domain.entities.Ingrediente;
import com.br.chriscakes.repositories.EstoqueRepository;
import com.br.chriscakes.repositories.IngredientesRepository;
import com.br.chriscakes.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    private static final String MSG_INGREDIENTE_NAO_ENCONTRADO = "Ingrediente de id %d não foi encontrado!";
    private static final String MSG_ESTOQUE_NAO_ENCONTRADO = "Estoque de id %d não foi encontrado!";

    private EstoqueRepository estoqueRepository;
    private IngredientesRepository ingredientesRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, IngredientesRepository ingredientesRepository) {
        this.estoqueRepository = estoqueRepository;
        this.ingredientesRepository = ingredientesRepository;
    }

    public Double getValorTotalIngredienteEmEstoque(Long id) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MSG_ESTOQUE_NAO_ENCONTRADO, id)));

        return estoque.getIngrediente().getValor() * estoque.getQuantidade();
    }

    public void adicionaIngredienteEmEstoque(Long estoqeuId, Long quantidade) {
        Estoque estoque = estoqueRepository.findById(estoqeuId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MSG_ESTOQUE_NAO_ENCONTRADO, estoqeuId)
                ));
        long quantidadeEmEstoque = estoque.getQuantidade();
        estoque.setQuantidade(quantidadeEmEstoque + quantidade);
        estoqueRepository.save(estoque);
    }

    public Estoque criarEstoque(Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    public void retirarIngredienteEmEstoque(Long estoqueId, Long quantidade) {
        Estoque estoque = estoqueRepository.findById(estoqueId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MSG_ESTOQUE_NAO_ENCONTRADO, estoqueId)
                ));
        long quantidadeEmEstoque = estoque.getQuantidade();
        estoque.setQuantidade(quantidadeEmEstoque - quantidade);
        estoqueRepository.save(estoque);
    }
}
