package poo.gamed;

import java.io.*;
import java.util.ArrayList;

public class Serializador {
    // Metodo para salvar todas as listas em um único arquivo
    public static void salvarEstado(String arquivo,
                                    ArrayList<Obra> obras,
                                    ArrayList<Utente> utentes,
                                    ArrayList<Categoria> categorias,
                                    ArrayList<Requisicao> requisicoes) throws IOException {

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(arquivo))) {

            // Criar um objeto que contém todas as listas
            Object[] estado = new Object[4];
            estado[0] = utentes;
            estado[1] = obras;
            estado[2] = categorias;
            estado[3] = requisicoes;


            oos.writeObject(estado);
            System.out.println("Estado salvo em: " + arquivo);
        }
    }

    /*/ Metodo para carregar todas as listas */
    public static Object[] carregarEstado(String arquivo)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(arquivo))) {

            Object[] estado = (Object[]) ois.readObject();
            System.out.println("Estado carregado de: " + arquivo);
            return estado;
        }
    }

    // Metodo especifico para salvar obras
    public static void salvarObras(String arquivo, ArrayList<Obra> obras)
            throws IOException {

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(arquivo))) {
            oos.writeObject(obras);
        }
    }

    // Metodo específico para salvar utentes
    public static void salvarUtentes(String arquivo, ArrayList<Utente> utentes)
            throws IOException {

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(arquivo))) {
            oos.writeObject(utentes);
        }
    }
}
