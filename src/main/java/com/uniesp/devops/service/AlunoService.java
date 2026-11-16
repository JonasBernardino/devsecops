package com.uniesp.devops.service;

import com.uniesp.devops.model.Aluno;
import com.uniesp.devops.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<Aluno> listar() {
        return repository.listar();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    public List<Aluno> buscarPorNome(String nome) {
        return repository.buscarPorNome(nome);
    }

    public Aluno cadastrar(String nome, String email, String curso) {
        return repository.salvar(nome, email, curso);
    }
}
