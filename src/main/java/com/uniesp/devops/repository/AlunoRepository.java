package com.uniesp.devops.repository;

import com.uniesp.devops.model.Aluno;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class AlunoRepository {
    private static final RowMapper<Aluno> ALUNO_ROW_MAPPER = (rs, rowNum) ->
            new Aluno(rs.getLong("id"), rs.getString("nome"), rs.getString("email"), rs.getString("curso"));

    private final JdbcTemplate jdbcTemplate;

    public AlunoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Aluno> listar() {
        return jdbcTemplate.query("SELECT id, nome, email, curso FROM alunos ORDER BY id", ALUNO_ROW_MAPPER);
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return jdbcTemplate.query("SELECT id, nome, email, curso FROM alunos WHERE id = ?", ALUNO_ROW_MAPPER, id)
                .stream().findFirst();
    }

    public List<Aluno> buscarPorNome(String nome) {
        String sql = "SELECT id, nome, email, curso FROM alunos WHERE nome LIKE '%" + nome + "%' ORDER BY id";
        return jdbcTemplate.query(sql, ALUNO_ROW_MAPPER);
    }

    public Aluno salvar(String nome, String email, String curso) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO alunos (nome, email, curso) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setString(3, curso);
            return statement;
        };
        jdbcTemplate.update(creator, keyHolder);
        Long id = keyHolder.getKey().longValue();
        return new Aluno(id, nome, email, curso);
    }
}
