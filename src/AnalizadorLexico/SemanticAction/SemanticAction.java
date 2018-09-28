package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.*;

public abstract class SemanticAction {
    protected LexicalAnalyzer lexical;
    protected static String buffer;
    public SemanticAction(LexicalAnalyzer lexicalAnalyzer) {
            this.lexical = lexicalAnalyzer;
    }

    public abstract void Action(Character symbol);


}
