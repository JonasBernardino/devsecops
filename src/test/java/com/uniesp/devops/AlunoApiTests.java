package com.uniesp.devops;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/reset-alunos.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AlunoApiTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveListarAlunosCadastrados() throws Exception {
        mockMvc.perform(get("/api/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void deveConsultarAlunoPorId() throws Exception {
        mockMvc.perform(get("/api/alunos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Pedro Almeida")));
    }

    @Test
    void devePesquisarAlunoPorNomeValido() throws Exception {
        mockMvc.perform(get("/api/alunos/buscar").param("nome", "Pedro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is("pedro.almeida@example.test")));
    }

    @Test
    void deveCadastrarNovoAluno() throws Exception {
        String aluno = objectMapper.writeValueAsString(new CadastroAluno("Rafaela Lima", "rafaela@example.test", "DevOps"));

        mockMvc.perform(post("/api/alunos").contentType(MediaType.APPLICATION_JSON).content(aluno))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome", is("Rafaela Lima")));
    }

    private record CadastroAluno(String nome, String email, String curso) {
    }
}
