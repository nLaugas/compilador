package AnalizadorLexico.SemanticAction;

import AnalizadorLexico.LexicalAnalyzer;

public class AS_ErrorNotLexema extends SemanticAction{

    public AS_ErrorNotLexema(LexicalAnalyzer lexicalAnalyzer) {
        super(lexicalAnalyzer);
    }

    @Override
    public void Action(Character symbol) {
        System.out.print("error en linea "+ lexical.row+" y columna "+ lexical.index);
        lexical.index++;
        System.out.println("index++");
        lexical.buffer = "";
        System.out.println("vacia buffer");
    }
}
