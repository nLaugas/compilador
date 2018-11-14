package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import AnalizadorSintactico.ParserVal;
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
        lexical.symbolTable.setAtributo(String.valueOf(num),"=>","CTE FLOTANTE");
        lexical.yylval.obj=lexical.symbolTable.getSymbol(String.valueOf(num));

        //PARA LOS IDENTIFICADORES DE ESTE TIPO EN LA GRAMATICA VA ESTO MISMO
        lexical.symbolTable.getSymbol(String.valueOf(num)).setTipoVar("single");
        lexical.buffer = "";
    }
}
