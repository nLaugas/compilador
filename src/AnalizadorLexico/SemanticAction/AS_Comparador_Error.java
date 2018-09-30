package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorLexico.StateMachine.StateMachine;
import AnalizadorSintactico.Parser;
import Errors.Errors;

public class AS_Comparador_Error extends SemanticAction {

    public AS_Comparador_Error(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    public void Action(Character symbol) {
        switch (lexical.buffer) {
            case "<":
                lexical.tokenId = (int)'<';
                lexical.state=StateMachine.FINAL_STATE;
                break;
            case ">":
                lexical.tokenId = (int)'>';
                lexical.state=StateMachine.FINAL_STATE;
                break;
            default:
                String e= Errors.ERROR_FAIL_CHARACTER+" "+buffer;
                lexical.errors.setError(lexical.row,lexical.column,e);

//en este caso suponemos que hasta no retornar un tocken valido se sigue
        }
        lexical.buffer="";
    }
}