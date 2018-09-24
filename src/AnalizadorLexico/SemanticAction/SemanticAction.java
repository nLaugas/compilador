package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;
import SymbolTable.SymbolTable;

public abstract class SemanticAction {
    protected LexicalAnalyzer lexicalAnalyzer;

    public SemanticAction(LexicalAnalyzer lexicalAnalyzer) {
            this.lexicalAnalyzer = lexicalAnalyzer;
    }

    public abstract void Action();


}
