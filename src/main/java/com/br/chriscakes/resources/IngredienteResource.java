package com.br.chriscakes.resources;

import com.br.chriscakes.domain.dto.IngredienteDTO;
import com.br.chriscakes.services.IngredienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteResource {

    private IngredienteService service;

    public IngredienteResource(IngredienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<IngredienteDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredienteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}
