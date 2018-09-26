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
    public String token;
    private void create(){
        StateMachine.addTransition(0, '_',3,new  Next(this));
        StateMachine.addTransition(0, 'a',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(0, '1',1,new  Next(this));
        StateMachine.addTransition(0, 'i',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
        StateMachine.addTransition(0, ' ',0,new NextSpaceCase(this));
        StateMachine.addTransition(0, '\n',0,new NextLineCase(this));
        StateMachine.addTransition(0,'.' ,10,new  Next(this));
        StateMachine.addTransition(0, 'f',StateMachine.FINAL_STATE,new ErrorNotCaracter(this));
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
        StateMachine.addTransition(11, '1',1,new Next(this));
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

    private Character convert(Character c){

        int asciiChar = (int )c;

        if ((asciiChar>=65 && asciiChar<=90)||(asciiChar>=97 && asciiChar<=101)||(asciiChar>=103 && asciiChar<=104)||(asciiChar>=106 && asciiChar<=122)){
            //{universo de letras minusculas y mayusculas} - {i}
            return 'a';
        }
        if (asciiChar>= 48 && asciiChar<=57){
            //numero de 0-9
            return '1';
        }

        return c;
    }

    public String  getNextToken(){
        state = StateMachine.INITIAL_STATE;
        Character symbol;
        while (state != StateMachine.FINAL_STATE){
            symbol = convert(srcCode.charAt(index));
            StateMachine.getSemanticAction(state,symbol).Action();
            state = StateMachine.getNextState(state,symbol);



        }


        return token;
    }


}
