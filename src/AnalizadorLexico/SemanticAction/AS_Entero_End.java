package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import AnalizadorSintactico.ParserVal;
import Errors.*;

public class AS_Entero_End extends SemanticAction{

    public AS_Entero_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        //saca el _ para quedarse con solo el numero
        int num = Integer.valueOf(lexical.buffer.substring(0,lexical.buffer.length()-1));
        if ( num > lexical.MAX_INT_SIZE){
            lexical.errors.setError(lexical.row,lexical.column,Errors.ERROR_RANGE);
            num = lexical.MAX_INT_SIZE;
        }
        lexical.symbolTable.setSymbol(String.valueOf(num)+"_i"/*lexical.buffer+symbol*/, Parser.ENTERO);
        lexical.symbolTable.setAtributo(String.valueOf(num)+"_i"/*lexical.buffer+symbol*/,"=>","CTE ENTERO");
        //tecnica de reemplazo por el valor mas grande permitido
        //System.out.println(num);
        //lexical.lastSymbol=lexical.buffer+ symbol; // guardo el simbolo viejo
//###############  ASEGURARNOS QUE CREANDO UN PARSER VAL CON UN SYMBOLO ESTAMOS HACIENDO LAS COSAS BIEN
        lexical.lastSymbol=String.valueOf(num)+"_i"; // no se usa mas

        lexical.yylval.obj=lexical.symbolTable.getSymbol(lexical.buffer);

        lexical.buffer = "";
        lexical.column++;
        lexical.index++;
    }
}
