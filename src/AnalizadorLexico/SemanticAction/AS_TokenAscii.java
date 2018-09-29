package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_TokenAscii extends SemanticAction {

    public AS_TokenAscii(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    public void Action(Character symbol) {
        lexical.tokenId = (int)symbol;
        lexical.index++;
        lexical.column++;
        lexical.buffer = "";
    }
}
