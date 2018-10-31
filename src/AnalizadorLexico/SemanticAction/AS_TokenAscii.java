package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.ParserVal;

public class AS_TokenAscii extends SemanticAction {

    public AS_TokenAscii(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    public void Action(Character symbol) {
        lexical.tokenId = (int)symbol;
        lexical.index++;
        lexical.column++;

        lexical.yylval = new ParserVal();
        lexical.yylval.setColumna(lexical.column);
        lexical.yylval.setFila(lexical.row);

        lexical.yylval.ival=lexical.tokenId;

        lexical.buffer = "";
    }
}
