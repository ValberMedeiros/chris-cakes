package com.br.chriscakes.domain.dto;

import com.br.chriscakes.domain.entities.Ingredientes;
import com.br.chriscakes.domain.enums.UnidadeMedida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteDTO {

    private String nome;
    private UnidadeMedida unidadeMedida;
    private Long quantidadeMedida;
    private Long quantidadeEmEstoque;

    public IngredienteDTO(Ingredientes entity) {
        nome = entity.getNome();
        unidadeMedida = entity.getUnidadeMedida();
        quantidadeMedida = entity.getQuantidadeMedida();
        quantidadeEmEstoque = entity.getQuantidadeEmEstoque();
    }
}
