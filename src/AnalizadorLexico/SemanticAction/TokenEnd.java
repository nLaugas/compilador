package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class TokenEnd extends SemanticAction {

    public TokenEnd(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action() {
        lexicalAnalyzer.buffer+=lexicalAnalyzer.srcCode.charAt(lexicalAnalyzer.index);
        System.out.println("retorna token "+lexicalAnalyzer.buffer);
        lexicalAnalyzer.token = lexicalAnalyzer.buffer;
        lexicalAnalyzer.buffer = "";
        System.out.println("vacia buffer");
        lexicalAnalyzer.index++;
        System.out.println("index ++");
    }
}
