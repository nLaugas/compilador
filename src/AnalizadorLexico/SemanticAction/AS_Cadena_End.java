package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import Errors.Errors;

public class AS_Cadena_End extends SemanticAction{

    public AS_Cadena_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        lexical.symbolTable.setSymbol(lexical.buffer, Parser.CADENA);
        System.out.println(lexical.buffer);
        lexical.buffer = "";
        lexical.index++;
        lexical.column++;
    }
}