package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;

public class AS_ErrorNotLexema extends SemanticAction{

    public AS_ErrorNotLexema(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        String e= Errors.ERROR_FAIL_CHARACTER +" "+buffer;
        lexical.errors.setError(lexical.row,lexical.column,e);
        lexical.buffer = "";
        //lexical.index++;
        //lexical.column++;
    }
}
