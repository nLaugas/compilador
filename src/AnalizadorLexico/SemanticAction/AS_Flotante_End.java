package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import Errors.Errors;

public class AS_Flotante_End extends SemanticAction{

    public AS_Flotante_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }
    //deja el tipo asignado en Start
    @Override
    public void Action(Character symbol) {

        if (lexical.buffer.contains("F")){
            lexical.buffer=lexical.buffer.replace('F','e');
        }

        float num = Float.valueOf(lexical.buffer);
        System.out.println(lexical.MIN_FLOAT_SIZE);

        System.out.println(num);
        if (num != 0.0) {
            if (num < lexical.MIN_FLOAT_SIZE) {
                lexical.errors.setError(lexical.row, lexical.column, Errors.ERROR_RANGE);
                num = lexical.MIN_FLOAT_SIZE;
            } else if (num > lexical.MAX_FLOAT_SIZE) {
                lexical.errors.setError(lexical.row, lexical.column, Errors.ERROR_RANGE);
                num = lexical.MAX_FLOAT_SIZE;
            }
        }
        lexical.symbolTable.setSymbol(String.valueOf(num), Parser.FLOTANTE);
        lexical.symbolTable.setAtributo(lexical.buffer,"=>","CTE FLOTANTE");
        //tecnica de reemplazo por el valor mas grande permitido
        System.out.println(num);
        lexical.buffer = "";
    }
}
