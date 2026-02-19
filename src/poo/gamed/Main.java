package poo.gamed;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    Mediateca mediateca = new Mediateca();

    public static void main(String[] args) {
        Mediateca.menu_principal();
    }
}