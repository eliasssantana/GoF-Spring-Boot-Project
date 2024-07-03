package com.lab.design_patterns_spring.service.impl;

import com.lab.design_patterns_spring.model.Cliente;
import com.lab.design_patterns_spring.model.Endereco;
import com.lab.design_patterns_spring.repository.ClienteRepository;
import com.lab.design_patterns_spring.repository.EnderecoRepository;
import com.lab.design_patterns_spring.service.ClienteService;
import com.lab.design_patterns_spring.service.ViaCepService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    // TODO Singleton: Injetar as dependências com o IoC (Inversion of control) e DI (Dependency Injection)
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElse(null);

    }

    @Override
    public void inserir(Cliente cliente) {
        salvaCliente(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {

        Optional<Cliente> clienteDB = clienteRepository.findById(id);

        if (clienteDB.isEmpty()){
            // TODO: Criar classes de tratamento de exceções e criar uma exceção customizada, como: ClienteNotFoundException
            throw new RuntimeException();
        }
        salvaCliente(cliente);

    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private void salvaCliente(Cliente cliente) {
        final String cep = cliente.getEndereco().getCep();

        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });

        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
