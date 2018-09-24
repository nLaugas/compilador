package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class NextSpaceCase extends SemanticAction {

    public NextSpaceCase(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action() {
            System.out.println("index++");
            lexicalAnalyzer.index++;
            System.out.println("column++");
            lexicalAnalyzer.column++;
    }
}
