package org.clienteservidor.util;

import org.clienteservidor.model.Alunos;
import org.clienteservidor.model.Curso;

import java.util.List;

public class Funcoes {
    public Funcoes(){}
    public static void printAluno(Alunos alunos) {
        System.err.println("Nome: " + alunos.getNome());
        System.err.println("Matricula: " + alunos.getMatricula());
        System.err.println("Curso: " + alunos.getCurso());
        System.err.println(alunos.isMaioridade() ? "Maior idade" : "Menor idade");
        System.err.println("Sexo: " + alunos.getSexo());
        System.err.println("Telefone: " + alunos.getTelefone());
        System.out.println("=====================================================");
    }

    public static void printListAluno(List<Alunos> lista) {
        for (Alunos aluno : lista){
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("Matricula: " + aluno.getMatricula());
            System.out.println("Curso: " + aluno.getCurso());
            System.out.println(aluno.isMaioridade() ? "Maior idade" : "Menor idade");
            System.out.println("Sexo: " + aluno.getSexo());
            System.out.println("Telefone: " + aluno.getTelefone());
            System.out.println("=====================================================");
        }
    }

    public static void printCurso(Curso curso) {
        System.err.println("Código: " + curso.getCodigo());
        System.err.println("Nome: " + curso.getNome());
        System.err.println("Sigla: " + curso.getSigla());
        System.err.println("Área: " + curso.getArea());
        System.out.println("=====================================================");
    }

    public static void printListCurso(List<Curso> lista) {
        for (Curso curso : lista) {
            System.out.println("Código: " + curso.getCodigo());
            System.out.println("Nome: " + curso.getNome());
            System.out.println("Sigla: " + curso.getSigla());
            System.out.println("Área: " + curso.getArea());
            System.out.println("=====================================================");
        }
    }
}
