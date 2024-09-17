package org.clienteservidor;
/*
Instruções
1. Crie uma classe em Model para Curso com os seguintes atributos:

-   Código ( Long , primária e auto incremento no banco)
-   Nome (String)
-   Sigla (String , única no banco)
-   Área (Enum: exatas, humanas, biológicas, artes)

2. Crie ICursoDAO e o CursoDAO , com as funções do CRUD:

-   create , update, delete, findAll , findById findByArea e findBySigla

3. Em Aluno, modifique:

-   Curso será do tipo string
-   O curso deverá vir da nova classe criada Curso
-   Apenas a sigla do curso deve ser mostrada ao imprimir.

4. Modifique o relacionamento do banco de dados entre aluno e curso, de modo que um Curso pode ser cursado por n Alunos, e um Aluno pode cursar apenas um Curso.

5. Utilize interface gráfica para mostrar estes dados, permitindo também inserir, atualizar e deletar dados.
*/

import org.clienteservidor.dao.AlunoDAO;
import org.clienteservidor.dao.CursoDAO;
import org.clienteservidor.dao.IAlunoDAO;
import org.clienteservidor.dao.ICursoDAO;
import org.clienteservidor.interfaceUI.InterfaceEx03;
import org.clienteservidor.model.Alunos;
import org.clienteservidor.model.Curso;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        IAlunoDAO alunoDAO = new AlunoDAO();
        ICursoDAO cursoDAO = new CursoDAO();

        SwingUtilities.invokeLater(() -> new InterfaceEx03(alunoDAO, cursoDAO).setVisible(true));
    }

    public static void adicionarAlunoAoCurso(Alunos aluno, Curso curso) {
        aluno.setCurso(curso.getSigla());

        IAlunoDAO alunoDAO = new AlunoDAO();
        alunoDAO.create(aluno);
    }

}