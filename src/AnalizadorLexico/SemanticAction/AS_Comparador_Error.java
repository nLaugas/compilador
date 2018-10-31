package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorLexico.StateMachine.StateMachine;
import AnalizadorSintactico.Parser;
import AnalizadorSintactico.ParserVal;
import Errors.Errors;

public class AS_Comparador_Error extends SemanticAction {

    public AS_Comparador_Error(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    public void Action(Character symbol) {
        lexical.yylval = new ParserVal();
        lexical.yylval.setColumna(lexical.column);
        lexical.yylval.setFila(lexical.row);

        switch (lexical.buffer) {
            case "<":
                lexical.tokenId = (int)'<';
                lexical.state=StateMachine.FINAL_STATE;
                lexical.yylval.ival=60;
                break;
            case ">":
                lexical.tokenId = (int)'>';
                lexical.state=StateMachine.FINAL_STATE;
                lexical.yylval.ival=62;
                break;
            default:
                String e= Errors.ERROR_FAIL_CHARACTER+" "+buffer;
                lexical.errors.setError(lexical.row,lexical.column,e);

//en este caso suponemos que hasta no retornar un tocken valido se sigue
        }
        lexical.buffer="";
    }
}