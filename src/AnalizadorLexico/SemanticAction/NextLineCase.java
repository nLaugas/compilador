package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class NextLineCase extends SemanticAction {

    public NextLineCase(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action() {
        System.out.println("index++");
        lexicalAnalyzer.index++;
        System.out.println("row++");
        lexicalAnalyzer.row++;
        System.out.println("column = 0");
        lexicalAnalyzer.column = 0;
    }
}
