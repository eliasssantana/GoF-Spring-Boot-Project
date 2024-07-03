package com.lab.design_patterns_spring.repository;

import com.lab.design_patterns_spring.model.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
}
