package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import AnalizadorSintactico.ParserVal;


public class AS_Id_Start extends SemanticAction
{
    public AS_Id_Start(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    //comienza a ser un tipo ID y avanza para empezar a reconocer el lexema
    public void Action(Character symbol) {
        lexical.tokenId = Parser.ID;
        lexical.buffer+= symbol;

        lexical.index++;
        lexical.column++;
    }
}
