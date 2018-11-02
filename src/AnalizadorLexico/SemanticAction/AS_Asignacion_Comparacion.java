package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorLexico.StateMachine.StateMachine;
import AnalizadorSintactico.Parser;
import AnalizadorSintactico.ParserVal;
import Errors.Errors;

public class AS_Asignacion_Comparacion extends SemanticAction {

    public AS_Asignacion_Comparacion(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    public void Action(Character symbol) {
        lexical.yylval.sval=lexical.buffer+"=";
        switch (lexical.buffer) {
            case "!":
                lexical.tokenId = Parser.DIST;
                break;
            case "<":
                lexical.tokenId = Parser.MENIG;
                break;
            case ">":
                lexical.tokenId = Parser.MAYIG;
                break;
            case ":":
                lexical.tokenId = Parser.ASIG;
                break;
        }

        lexical.index++; // avanzo el cursor porque use el caracter
        lexical.column++;

        lexical.buffer = "";
}
}
