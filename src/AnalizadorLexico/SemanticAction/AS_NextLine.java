package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_NextLine extends SemanticAction {

    public AS_NextLine(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        lexical.index++;
        lexical.row++;
        lexical.column = 1;
    }
}
