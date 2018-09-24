package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class Next extends SemanticAction{

    public Next(LexicalAnalyzer lexicalAnalyzer){
        super(lexicalAnalyzer);
    }

    public void Action() {
            System.out.println("carga buffer con caracter "+lexicalAnalyzer.srcCode.charAt(lexicalAnalyzer.index));
            lexicalAnalyzer.buffer+=lexicalAnalyzer.srcCode.charAt(lexicalAnalyzer.index);
            System.out.println("index++");
            lexicalAnalyzer.index++;
    }

}
