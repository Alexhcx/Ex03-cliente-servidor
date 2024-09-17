package org.clienteservidor.dao;

import org.clienteservidor.model.Alunos;
import org.clienteservidor.model.Curso;

import java.util.List;
import java.util.Optional;

public interface IAlunoDAO {

    Alunos create(Alunos aluno);
    void update(Alunos aluno);
    void delete(Alunos aluno);
    Optional<Alunos> findById(Long id);
    List<Alunos> findAll();
    List<Alunos> findByCurso(Curso curso);

}