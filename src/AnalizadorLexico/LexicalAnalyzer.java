package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import AnalizadorLexico.SemanticAction.*;
import AnalizadorLexico.StateMachine.StateMachine;
import SymbolTable.*;

public class LexicalAnalyzer {

    public String srcCode;
    public int row;
    public int column;
    public SymbolTable symbolTable;
    public String buffer;
    public int state;
    public int index;

    private void create(){
        StateMachine.addTransition(0, '_',3,new  Next(this));
        StateMachine.addTransition(0, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(0, '1',1,new  Next(this));
        StateMachine.addTransition(0, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(0, ' ',0,new NextSpaceCase(this));
        StateMachine.addTransition(0, '\n',0,new NextLineCase(this));

        StateMachine.addTransition(1, '_',2,new  Next(this));
        StateMachine.addTransition(1, '1',1,new  Next(this));
        StateMachine.addTransition(1, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(1, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(1, ' ',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(1, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(2, '_',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, '1',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, 'i',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(2, ' ',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(3, '_',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(3, 'a',4,new Next(this));
        StateMachine.addTransition(3, '1',4,new Next(this));
        StateMachine.addTransition(3, 'i',4,new Next(this));
        StateMachine.addTransition(3, ' ',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(3, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(4, '_',4,new Next(this));
        StateMachine.addTransition(4, 'a',4,new Next(this));
        StateMachine.addTransition(4, '1',4,new Next(this));
        StateMachine.addTransition(4, 'i',4,new Next(this));
        StateMachine.addTransition(4, ' ',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(4, '\n',StateMachine.FINAL_STATE,new TokenEnd(this));


    }
    public LexicalAnalyzer(String srcCode, SymbolTable symbolTable) throws FileNotFoundException, IOException {
        this.symbolTable = symbolTable;
        row = 1;
        column = 1;
        state = StateMachine.INITIAL_STATE;
        index = 0;
        buffer = "";
        this.srcCode = srcCode;
        create();

    }

    public Token getNextToken(){
        state = StateMachine.INITIAL_STATE;
        Character symbol;
        while (state != StateMachine.FINAL_STATE){
            symbol = srcCode.charAt(index);
            System.out.print("simbolo: "+symbol);
            System.out.print("indice: "+index);
            System.out.print("stado: "+state);
            StateMachine.getSemanticAction(state,symbol).Action();
            state = StateMachine.getNextState(state,symbol);



        }


        return null;
    }


}
