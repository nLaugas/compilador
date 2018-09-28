package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_Next extends SemanticAction{

    public AS_Next(LexicalAnalyzer lexicalAnalyzer){
        super(lexicalAnalyzer);
    }

    public void Action(Character symbol) {
            lexical.buffer+= symbol;
            lexical.index++;
            lexical.column++;
    }

}
