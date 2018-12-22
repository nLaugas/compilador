import AnalizadorLexico.LexicalAnalyzer;
import SymbolTable.SymbolTable;

import javax.swing.text.html.parser.Parser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String Args[]) {

        App a = null;
        try {
            a = new App(/*Args[0]*/); // Eliminar
            a.setVisible(true);
        } catch (IOException e) {
            System.out.println("No se puede crear ejecutable, revise estructura de errores ");
        }


    }
}
