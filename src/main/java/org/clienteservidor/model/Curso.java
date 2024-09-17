package org.clienteservidor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Curso {

    public enum Area {
        EXATAS,
        HUMANAS,
        BIOLOGICAS,
        ARTES
    }

    private Long codigo;
    private String nome;
    private String sigla;
    private Area area;
}
