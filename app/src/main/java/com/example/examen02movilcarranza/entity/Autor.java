package com.example.examen02movilcarranza.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Autor {
    private int idAutor;
    private String nombres;
    private String apaterno;
    private String amaterno;
    private Pais idPais;

}
