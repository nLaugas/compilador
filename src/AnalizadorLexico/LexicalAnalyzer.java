package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import AnalizadorLexico.StateMachine.StateMachine;
import SymbolTable.*;

public class LexicalAnalyzer {

    private String srcCode;
    private int row;
    private int column;
    private SymbolTable symbolTable;
    private String buffer;
    private int state;
    private int index;

    public LexicalAnalyzer(String srcCode, SymbolTable symbolTable) throws FileNotFoundException, IOException {
        this.symbolTable = symbolTable;
        row = 1;
        column = 1;
        state = StateMachine.INITIAL_STATE;
        index = 0;

    }

    public Token getNextToken(){
        state = StateMachine.INITIAL_STATE;
        Character symbol = null;
        while (state != StateMachine.FINAL_STATE){
            symbol = srcCode.charAt(index);
            StateMachine.getSemanticAction(state,symbol).Action();
            state = StateMachine.getNextState(state,symbol);



        }


        return null;
    }


}
