package com.lab.design_patterns_spring.repository;

import com.lab.design_patterns_spring.model.Endereco;
import org.springframework.data.repository.CrudRepository;

public interface EnderecoRepository extends CrudRepository<Endereco, String> {
}
