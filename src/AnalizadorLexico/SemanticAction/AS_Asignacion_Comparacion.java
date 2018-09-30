package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorLexico.StateMachine.StateMachine;
import AnalizadorSintactico.Parser;
import Errors.Errors;

public class AS_Asignacion_Comparacion extends SemanticAction {

    public AS_Asignacion_Comparacion(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    public void Action(Character symbol) {
        switch (lexical.buffer) {
            case "!":
                lexical.tokenId = Parser.DIST;
                System.out.println("!");
                break;
            case "<":
                lexical.tokenId = Parser.MENIG;
                System.out.println("<");
                break;
            case ">":
                lexical.tokenId = Parser.MAYIG;
                System.out.println(">");
                break;
            case ":":
                lexical.tokenId = Parser.ASIG;
                System.out.println(":");
                break;
        }
        lexical.index++; // avanzo el cursor porque use el caracter
        lexical.column++;
        lexical.buffer = "";
}
}
