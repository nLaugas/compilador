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
                lexical.tokenId = Parser.COMP_DISTINTO;
                System.out.println("!");
                break;
            case "<":
                lexical.tokenId = Parser.COMP_MENOR_IGUAL;
                System.out.println("<");
                break;
            case ">":
                lexical.tokenId = Parser.COMP_MAYOR_IGUAL;
                System.out.println(">");
                break;
            case ":":
                lexical.tokenId = Parser.ASIGNACION;
                System.out.println(":");
                break;
        }
        lexical.index++; // avanzo el cursor porque use el caracter
        lexical.column++;
        lexical.buffer = "";
}
}
