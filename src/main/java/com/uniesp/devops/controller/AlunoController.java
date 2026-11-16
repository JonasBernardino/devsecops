package com.uniesp.devops.controller;

import com.uniesp.devops.model.Aluno;
import com.uniesp.devops.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {
    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Aluno> listar() {
        return service.listar();
    }

    @GetMapping("/buscar")
    public List<Aluno> buscarPorNome(@RequestParam String nome) {
        return service.buscarPorNome(nome);
    }

    @GetMapping(value = "/exportar.csv", produces = "text/csv")
    public String exportarCsv() {
        StringBuilder csv = new StringBuilder("id,nome,email,curso\n");
        for (Aluno aluno : service.listar()) {
            csv.append(aluno.id()).append(',')
                    .append(aluno.nome()).append(',')
                    .append(aluno.email()).append(',')
                    .append(aluno.curso()).append('\n');
        }
        return csv.toString();
    }

    @GetMapping("/{id}")
    public Aluno buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno cadastrar(@RequestBody CadastroAlunoRequest request) {
        return service.cadastrar(request.nome(), request.email(), request.curso());
    }

    public record CadastroAlunoRequest(String nome, String email, String curso) {
    }
}
