import AnalizadorLexico.LexicalAnalyzer;
import SymbolTable.SymbolTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String Args[]) throws IOException {
        FileReader file = new FileReader("src/srcCode");
        BufferedReader src= new BufferedReader(file);
        String archivo = src.readLine()+"\n";
        archivo += src.readLine();
        SymbolTable st = new SymbolTable();
        LexicalAnalyzer la = new LexicalAnalyzer(archivo,st);
        la.getNextToken();
        la.getNextToken();
        la.getNextToken();
        //System.out.print(a);


    }
}
