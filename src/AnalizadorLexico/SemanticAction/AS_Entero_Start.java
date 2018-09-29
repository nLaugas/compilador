package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

import AnalizadorSintactico.Parser;

public class AS_Entero_Start extends SemanticAction{

    public AS_Entero_Start(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        lexical.tokenId = Parser.ENTERO;
        lexical.buffer+= symbol;// no agrega el '
        lexical.index++;
        lexical.column++;
    }
}
