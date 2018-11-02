package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

import AnalizadorSintactico.Parser;
import AnalizadorSintactico.ParserVal;

public class AS_Entero_Start extends SemanticAction{

    public AS_Entero_Start(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        lexical.tokenId = Parser.ENTERO;
        lexical.buffer+= symbol;// no agrega el '

      /*  lexical.yylval = new ParserVal();
        lexical.yylval.setColumna(lexical.column);
        lexical.yylval.setFila(lexical.row);*/
        lexical.index++;
        lexical.column++;
    }
}
