package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_NextSpace extends SemanticAction {

    public AS_NextSpace(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
            lexical.index++;
            lexical.column++;
    }
}
