package com.br.chriscakes.repositories;

import com.br.chriscakes.domain.entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientesRepository extends JpaRepository<Ingrediente, Long> {
}
