package com.br.chriscakes.domain.dto;

import com.br.chriscakes.domain.entities.Ingrediente;
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

    private Long id;
    private String nome;
    private UnidadeMedida unidadeMedida;
    private Long quantidadeMedida;
    private Long quantidadeEmEstoque;

    public IngredienteDTO(Ingrediente entity) {
        id = entity.getId();
        nome = entity.getNome();
        unidadeMedida = entity.getUnidadeMedida();
        quantidadeMedida = entity.getQuantidadeMedida();
        quantidadeEmEstoque = entity.getQuantidadeEmEstoque();
    }
}
