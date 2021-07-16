package com.br.chriscakes.domain.enums;

import lombok.Getter;

@Getter
public enum UnidadeMedida {
    KG("quilograma"),
    L("litro"),
    UN("unidade"),
    G("grama");

    private String descricao;

    UnidadeMedida(String descricao) {
        this.descricao = descricao;
    }
}
