package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorLexico.StateMachine.StateMachine;
import Errors.Errors;

public class AS_NextLineCadena extends AS_NextLine {


    public AS_NextLineCadena(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        this.Action(symbol);
        int ultimoCaracter=lexical.buffer.length()-1;
        char guion = lexical.buffer.charAt(ultimoCaracter);
        if (guion=='-')
            lexical.buffer=lexical.buffer.substring(0,ultimoCaracter);
        else{
            String e= Errors.ERROR_FAIL_CHARACTER+" falto un guion para la cadena de caracteres";
            lexical.errors.setError(lexical.row,lexical.column,e);
            lexical.state= StateMachine.ERROR_STATE;
//piso estado de cadenas por estado de error???? o sigo como si  nada con warning
        }

    }
}
