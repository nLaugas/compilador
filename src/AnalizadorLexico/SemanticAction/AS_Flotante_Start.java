package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import AnalizadorSintactico.ParserVal;
import Errors.Errors;

public class AS_Flotante_Start extends SemanticAction {

    public AS_Flotante_Start(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    //deja el tipo asignado en Start
    @Override
    public void Action(Character symbol) {
        lexical.tokenId = Parser.FLOTANTE;
        lexical.buffer+= symbol;


        lexical.index++;
        lexical.column++;
    }
}