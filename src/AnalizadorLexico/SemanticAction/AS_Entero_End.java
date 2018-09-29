package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import Errors.*;

public class AS_Entero_End extends SemanticAction{

    public AS_Entero_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        //saca el _ para quedarse con solo el numero
        int num = Integer.valueOf(lexical.buffer.substring(0,lexical.buffer.length()-1));
        System.out.print(num);
        if (num < lexical.MIN_INT_SIZE) {
            lexical.errors.setError(lexical.row,lexical.column,Errors.ERROR_RANGE);
            num = lexical.MIN_INT_SIZE;
        }else if ( num > lexical.MAX_INT_SIZE){
            lexical.errors.setError(lexical.row,lexical.column,Errors.ERROR_RANGE);
            num = lexical.MAX_INT_SIZE;
        }
        lexical.symbolTable.setSymbol(String.valueOf(num), Parser.ENTERO);
        //tecnica de reemplazo por el valor mas grande permitido
        System.out.println(num);
        lexical.buffer = "";
        lexical.column++;
        lexical.index++;
    }
}
