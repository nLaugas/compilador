package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorLexico.StateMachine.StateMachine;
import Errors.Errors;

public class AS_Vaciar_Buffer extends SemanticAction{
    public AS_Vaciar_Buffer(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }
    //caso en que empieza a leer un comentario, por eso usa nextSpace y ademas no le importa el buffer
    public void Action(Character symbol) {
        lexical.index++;
        lexical.column++;
        lexical.buffer = "";

    }
}
