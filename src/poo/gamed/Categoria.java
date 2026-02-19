package poo.gamed;

import java.io.Serializable;

public class Categoria implements Serializable {

    private String nome;

    public Categoria(String nome) {
        this.nome = nome.toUpperCase();
    }
    // Getters e setters
    public String getNome() { return nome; }

}
