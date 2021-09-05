package com.br.chriscakes.resources;

import com.br.chriscakes.domain.dto.IngredienteDTO;
import com.br.chriscakes.domain.entities.Estoque;
import com.br.chriscakes.services.EstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
public class EstoqueResource {

    private EstoqueService estoqueService;

    public EstoqueResource(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping("/{id}/valor-em-estoque")
    public ResponseEntity<Double> getValorEmEstoque(@PathVariable Long id) {
        return ResponseEntity.ok(estoqueService.getValorTotalIngredienteEmEstoque(id));
    }

    @GetMapping("/{estoqueId}/adicionar/{quantidade}")
    public ResponseEntity<IngredienteDTO> increaseStock(@PathVariable Long estoqueId, @PathVariable Long quantidade) {
        estoqueService.adicionaIngredienteEmEstoque(estoqueId, quantidade);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{estoqueId}/retirar/{quantidade}")
    public ResponseEntity<IngredienteDTO> decrementStock(@PathVariable Long estoqueId, @PathVariable Long quantidade) {
        estoqueService.retirarIngredienteEmEstoque(estoqueId, quantidade);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Estoque> createStock(@RequestBody Estoque estoque) {
        return ResponseEntity.ok(estoqueService.criarEstoque(estoque));
    }
}
