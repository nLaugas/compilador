package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import AnalizadorLexico.SemanticAction.*;
import AnalizadorLexico.StateMachine.StateMachine;
import Errors.*;
import SymbolTable.*;

public class LexicalAnalyzer {


    public final int MAX_WORD_SIZE = 25;


    public String srcCode;
    public int row; //controla cada \n del string
    public int column;
    public SymbolTable symbolTable;
    public String buffer;
    public int state;
    public int index; //cursor para seguir el string que viene de forma lineal _a = 6; \n if..
    public String token;
    public Errors errors;

    private void create(){

        //
        StateMachine.addTransition(0, '_',3,new  Next(this));
        StateMachine.addTransition(0, 'a',15,new  Next(this));
        StateMachine.addTransition(0, '1',1,new  Next(this));
        StateMachine.addTransition(0, 'i',15,new  Next(this));
        StateMachine.addTransition(0, ' ',0,new NextSpaceCase(this));
        StateMachine.addTransition(0, '\n',0,new NextLineCase(this));
        StateMachine.addTransition(0,'.' ,10,new  Next(this));
        StateMachine.addTransition(0, 'f',15,new  Next(this));
        StateMachine.addTransition(0,'*',14,new  Next(this));
        StateMachine.addTransition(0,'-',11,new  Next(this));
        StateMachine.addTransition(0, '+',11,new  Next(this));

        StateMachine.addTransition(1, '_',2,new  Next(this));
        StateMachine.addTransition(1, '1',1,new  Next(this));
        StateMachine.addTransition(1, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(1, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(1, ' ',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(1, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(1,'.' ,5,new  Next(this));
        StateMachine.addTransition(1, 'f',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(1, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(1, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(1, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(2, '_',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, '1',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, 'i',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(2, ' ',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2,'.' ,StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, 'f',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(2, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));


        StateMachine.addTransition(3, '_',12,new Next(this));
        StateMachine.addTransition(3, 'a',4,new Next(this));
        StateMachine.addTransition(3, '1',4,new Next(this));
        StateMachine.addTransition(3, 'i',4,new Next(this));
        StateMachine.addTransition(3, ' ',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(3, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(3,'.' ,StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(3, 'f',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(3, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(3, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(3, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(4, '_',4,new Next(this));
        StateMachine.addTransition(4, 'a',4,new Next(this));
        StateMachine.addTransition(4, '1',4,new Next(this));
        StateMachine.addTransition(4, 'i',4,new Next(this));
        StateMachine.addTransition(4, ' ',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(4, '\n',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(4,'.' ,StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(4, 'f',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(4, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(4, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(4, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(5, '_',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(5, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(5, '1',6,new Next(this));
        StateMachine.addTransition(5, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(5, ' ',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(5, '\n',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(5,'.' ,StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(5, 'f',7,new Next(this));
        StateMachine.addTransition(5, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(5, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(5, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(6, '_',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(6, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(6, '1',6,new Next(this));
        StateMachine.addTransition(6, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(6, ' ',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(6, '\n',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(6,'.' ,StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(6, 'f',7,new Next(this));
        StateMachine.addTransition(6, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(6, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(6, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(7, '_',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(7, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(7, '1',9,new Next(this));
        StateMachine.addTransition(7, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(7, ' ',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(7, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(7,'.' ,StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(7, 'f',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(7, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(7, '-',8,new Next(this));
        StateMachine.addTransition(7, '+',8,new Next(this));

        StateMachine.addTransition(8, '_',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(8, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(8, '1',9,new Next(this));
        StateMachine.addTransition(8, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(8, ' ',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(8, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(8,'.' ,StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(8, 'f',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(8, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(8, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(8, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(9, '_',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(9, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(9, '1',9,new Next(this));
        StateMachine.addTransition(9, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(9, ' ',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(9, '\n',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(9,'.' ,StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(9, 'f',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(9, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(9, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(9, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(10, '_',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(10, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(10, '1',6,new Next(this));
        StateMachine.addTransition(10, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(10, ' ',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(10, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(10,'.' ,StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(10, 'f',7,new Next(this));
        StateMachine.addTransition(10, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(10, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(10, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(11, '_',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(11, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(11, '1',16,new Next(this));
        StateMachine.addTransition(11, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(11, ' ',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(11, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(11,'.' ,10,new Next(this));
        StateMachine.addTransition(11, 'f',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(11, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(11, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(11, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(12, '_',13,new Next(this));
        StateMachine.addTransition(12, 'a',13,new Next(this));
        StateMachine.addTransition(12, '1',13,new Next(this));
        StateMachine.addTransition(12, 'i',13,new Next(this));
        StateMachine.addTransition(12, ' ',13,new Next(this));
        StateMachine.addTransition(12, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(12,'.' ,13,new Next(this));
        StateMachine.addTransition(12, 'f',13,new Next(this));
        StateMachine.addTransition(12, '*',13,new Next(this));
        StateMachine.addTransition(12, '-',13,new Next(this));
        StateMachine.addTransition(12, '+',13,new Next(this));

        StateMachine.addTransition(13, '_',13,new Next(this));
        StateMachine.addTransition(13, 'a',13,new Next(this));
        StateMachine.addTransition(13, '1',13,new Next(this));
        StateMachine.addTransition(13, 'i',13,new Next(this));
        StateMachine.addTransition(13, ' ',13,new Next(this));
        StateMachine.addTransition(13, '\n',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(13,'.' ,13,new Next(this));
        StateMachine.addTransition(13, 'f',13,new Next(this));
        StateMachine.addTransition(13, '*',13,new Next(this));
        StateMachine.addTransition(13, '-',13,new Next(this));
        StateMachine.addTransition(13, '+',13,new Next(this));

        StateMachine.addTransition(14, '_',14,new Next(this));
        StateMachine.addTransition(14, 'a',14,new Next(this));
        StateMachine.addTransition(14, '1',14,new Next(this));
        StateMachine.addTransition(14, 'i',14,new Next(this));
        StateMachine.addTransition(14, ' ',14,new Next(this));
        StateMachine.addTransition(14, '\n',14,new Next(this));
        StateMachine.addTransition(14,'.' ,14,new Next(this));
        StateMachine.addTransition(14, 'f',14,new Next(this));
        StateMachine.addTransition(14, '*',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(14, '-',14,new Next(this));
        StateMachine.addTransition(14, '+',14,new Next(this));

        StateMachine.addTransition(15, '_',15,new Next(this));
        StateMachine.addTransition(15, 'a',15,new Next(this));
        StateMachine.addTransition(15, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(15, '1',15,new Next(this));
        StateMachine.addTransition(15, ' ',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(15, '\n',StateMachine.FINAL_STATE,new TokenEnd(this));
        StateMachine.addTransition(15, '.',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(15, 'f',15,new Next(this));
        StateMachine.addTransition(15, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(15, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(15, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));

        StateMachine.addTransition(16, '_',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(16, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(16, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(16, '1',16,new Next(this));
        StateMachine.addTransition(16, ' ',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(16, '\n',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(16, '.',16,new Next(this));
        StateMachine.addTransition(16, 'f',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(16, '*',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(16, '-',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(16, '+',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
    }
    public LexicalAnalyzer(String srcCode, SymbolTable symbolTable, Errors errors) throws FileNotFoundException, IOException {
        this.symbolTable = symbolTable;
        row = 1;
        column = 1;
        state = StateMachine.INITIAL_STATE;
        index = 0;
        buffer = "";
        this.srcCode = srcCode;
        this.errors = errors;
        create();

    }



    public String  getNextToken(){
        state = StateMachine.INITIAL_STATE;
        Character symbol;
        while (state != StateMachine.FINAL_STATE){
            if (index >=srcCode.length()){
                return null;
            }
            symbol = srcCode.charAt(index);
            StateMachine.getSemanticAction(state,symbol).Action();
            state = StateMachine.getNextState(state,symbol);



        }


        return token;
    }

}
