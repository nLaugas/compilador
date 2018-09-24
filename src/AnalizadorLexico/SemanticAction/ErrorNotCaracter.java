package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class ErrorNotCaracter extends SemanticAction{

    public ErrorNotCaracter(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action() {
        System.out.print("error en linea "+lexicalAnalyzer.row+" y columna "+lexicalAnalyzer.index);
        lexicalAnalyzer.index++;
        System.out.println("index++");
        lexicalAnalyzer.buffer = "";
        System.out.println("vacia buffer");
    }
}
