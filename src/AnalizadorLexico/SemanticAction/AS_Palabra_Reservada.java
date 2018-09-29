package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;

import javax.swing.text.html.parser.Parser;

public class AS_Palabra_Reservada extends SemanticAction
{
    public AS_Palabra_Reservada(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    //comienza a ser un tipo ID y avanza para empezar a reconocer el lexema
    public void Action(Character symbol) {
        String word = lexical.buffer;
        if (lexical.reservedWords.containsKey(word)){
            lexical.tokenId = lexical.reservedWords.get(word);
        }else
        {
            String e= Errors.ERROR_FAIL_CHARACTER +" "+buffer;
            lexical.errors.setError(lexical.row,lexical.column,e);
        }
        lexical.buffer = "";
    }
}