package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import AnalizadorSintactico.ParserVal;
import Errors.*;

public class AS_Id_End extends SemanticAction{

    public AS_Id_End(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }
    //deja el tipo asignado en Start
    @Override
    public void Action(Character symbol) {
        if (lexical.buffer.length() > lexical.MAX_WORD_SIZE){
            lexical.errors.setError(lexical.row,lexical.column,Errors.ERROR_MAX_WORD_SIZE);
            lexical.buffer = lexical.buffer.substring(0,lexical.MAX_WORD_SIZE);
            //siempre acota a 25 caracteres
        }

        lexical.symbolTable.setSymbol(lexical.buffer, Parser.ID);
        lexical.symbolTable.setAtributo(lexical.buffer,"=>","IDENTIFICADOR");
        //agrega en la tabla de simbolo ya que es de tipo ID

        // ######## ASEGURARNOS DE QUE ESTAMOS CONSTRUYENDO EL PARSER VAL CON UN PUNTERO A LA TABLA DE SYMBOLOS

        lexical.yylval.obj=lexical.symbolTable.getSymbol(lexical.buffer);

        lexical.lastSymbol=lexical.buffer; // guardo el simbolo viejo
        lexical.buffer = "";
    }
}
