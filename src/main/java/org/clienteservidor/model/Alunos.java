package org.clienteservidor.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alunos {
   private Long matricula;
   private String nome;
   private String telefone;
   private boolean maioridade;
   private String curso;
   private String sexo;
}
