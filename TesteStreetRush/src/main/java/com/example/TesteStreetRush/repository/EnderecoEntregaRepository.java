package com.example.TesteStreetRush.repository;

import com.example.TesteStreetRush.model.EnderecoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoEntregaRepository extends JpaRepository<EnderecoEntrega, Long> {
}
