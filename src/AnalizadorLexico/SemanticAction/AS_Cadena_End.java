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
        lexical.symbolTable.setAtributo(lexical.buffer,"=>","CADENA");
        System.out.println(lexical.buffer);
        lexical.lastSymbol=lexical.buffer; // guardo el simbolo viejo
        lexical.buffer = "";
        lexical.index++;
        lexical.column++;
    }
}