package AnalizadorLexico;

import java.io.FileNotFoundException;
import java.io.IOException;

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
    public int tokenId;
    public Errors errors;

    private void create(){

        //
        SemanticAction tokenAscii = new AS_TokenAscii(this);
        SemanticAction asignacion_Comparacion = new AS_Asignacion_Comparacion(this);
        SemanticAction comp_error = new AS_Comparador_Error(this);
        SemanticAction next = new AS_Next(this);//avanza y guarda en el buffer
        SemanticAction saltoLinea = new AS_NextLine(this);//solo avanza fila
        SemanticAction espacio = new AS_NextSpace(this);//solo avanza columna
        SemanticAction comentario = new AS_Vaciar_Buffer(this);


        StateMachine.addTransition(0, '_',1,new AS_Next(this));
        StateMachine.addTransition(0, 'a',9,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(0, '1',2,new AS_Next(this));
        StateMachine.addTransition(0, 'i',9,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(0, ' ',0,espacio);
        StateMachine.addTransition(0, '\n',0,saltoLinea);
        StateMachine.addTransition(0, '.',4,new AS_Next(this));
        StateMachine.addTransition(0, 'F',9,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(0, '*',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, '-',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, '+',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, '&',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, '=',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, ')',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, '/',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, '{',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, '}',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, ',',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, ';',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, '(',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(0, ':',10,tokenAscii);
        StateMachine.addTransition(0, '<',10,tokenAscii);
        StateMachine.addTransition(0, '>',10,tokenAscii);
        StateMachine.addTransition(0, '!',10,tokenAscii);
        StateMachine.addTransition(0, '\'',12,tokenAscii);


        //13 es el estado para no conocer _ solo, este obliga a tener un d/l

        StateMachine.addTransition(1, '_',11,comentario);
        StateMachine.addTransition(1, 'a',13,next);
        StateMachine.addTransition(1, '1',13,new AS_Next(this));
        StateMachine.addTransition(1, 'i',13,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(1, ' ',StateMachine.ERROR_STATE,espacio);
        StateMachine.addTransition(1, '\n',StateMachine.ERROR_STATE,new AS_NextLine(this));
        StateMachine.addTransition(1, '.',StateMachine.ERROR_STATE,new AS_Next(this));
        StateMachine.addTransition(1, 'F',13,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(1, '*',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '-',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '+',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '&',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '=',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, ')',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '/',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '{',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '}',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, ',',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, ';',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '(',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, ':',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '<',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '>',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '!',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(1, '\'',StateMachine.ERROR_STATE,tokenAscii);


        StateMachine.addTransition(2, '_',3,new AS_Next(this));
        StateMachine.addTransition(2, 'a',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(2, '1',2,new AS_Next(this));
        StateMachine.addTransition(2, 'i',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(2, ' ',StateMachine.ERROR_STATE,espacio);
        StateMachine.addTransition(2, '\n',StateMachine.ERROR_STATE,new AS_NextLine(this));
        StateMachine.addTransition(2, '.',5,new AS_Next(this));
        StateMachine.addTransition(2, 'F',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(2, '*',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '-',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '+',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '&',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '=',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, ')',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '/',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '{',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '}',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, ',',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, ';',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '(',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, ':',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '<',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '>',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '!',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(2, '\'',StateMachine.ERROR_STATE,tokenAscii);

        StateMachine.addTransition(3, '_',StateMachine.ERROR_STATE,new AS_Next(this));
        StateMachine.addTransition(3, 'a',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(3, '1',StateMachine.ERROR_STATE,new AS_Next(this));
        StateMachine.addTransition(3, 'i',StateMachine.FINAL_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(3, ' ',StateMachine.ERROR_STATE,espacio);
        StateMachine.addTransition(3, '\n',StateMachine.ERROR_STATE,new AS_NextLine(this));
        StateMachine.addTransition(3, '.',StateMachine.ERROR_STATE,new AS_Next(this));
        StateMachine.addTransition(3, 'F',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(3, '*',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '-',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '+',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '&',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '=',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, ')',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '/',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '{',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '}',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, ',',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, ';',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '(',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, ':',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '<',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '>',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '!',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(3, '\'',StateMachine.ERROR_STATE,tokenAscii);


        StateMachine.addTransition(4, '_',StateMachine.ERROR_STATE,new AS_Next(this));
        StateMachine.addTransition(4, 'a',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(4, '1',5,new AS_Next(this));
        StateMachine.addTransition(4, 'i',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(4, ' ',StateMachine.ERROR_STATE,espacio);
        StateMachine.addTransition(4, '\n',StateMachine.ERROR_STATE,new AS_NextLine(this));
        StateMachine.addTransition(4, '.',StateMachine.ERROR_STATE,new AS_Next(this));
        StateMachine.addTransition(4, 'F',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(4, '*',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '-',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '+',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '&',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '=',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, ')',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '/',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '{',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '}',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, ',',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, ';',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '(',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, ':',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '<',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '>',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '!',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(4, '\'',StateMachine.ERROR_STATE,tokenAscii);


        StateMachine.addTransition(5, '_',StateMachine.FINAL_STATE,new AS_Next(this));
        StateMachine.addTransition(5, 'a',StateMachine.FINAL_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(5, '1',5,new AS_Next(this));
        StateMachine.addTransition(5, 'i',StateMachine.FINAL_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(5, ' ',StateMachine.FINAL_STATE,espacio);
        StateMachine.addTransition(5, '\n',StateMachine.FINAL_STATE,new AS_NextLine(this));
        StateMachine.addTransition(5, '.',StateMachine.FINAL_STATE,new AS_Next(this));
        StateMachine.addTransition(5, 'F',6,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(5, '*',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '-',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '+',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '&',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '=',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, ')',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '/',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '{',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '}',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, ',',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, ';',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '(',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, ':',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '<',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '>',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '!',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(5, '\'',StateMachine.FINAL_STATE,tokenAscii);

        StateMachine.addTransition(6, '_',StateMachine.ERROR_STATE,new AS_Next(this));
        StateMachine.addTransition(6, 'a',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(6, '1',8,new AS_Next(this));
        StateMachine.addTransition(6, 'i',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(6, ' ',StateMachine.ERROR_STATE,espacio);
        StateMachine.addTransition(6, '\n',StateMachine.ERROR_STATE,new AS_NextLine(this));
        StateMachine.addTransition(6, '.',StateMachine.ERROR_STATE,new AS_Next(this));
        StateMachine.addTransition(6, 'F',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(6, '*',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, '-',7,tokenAscii);
        StateMachine.addTransition(6, '+',7,tokenAscii);
        StateMachine.addTransition(6, '&',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, '=',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, ')',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, '/',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, '{',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, '}',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, ',',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, ';',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, '(',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, ':',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, '<',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, '>',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, '!',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(6, '\'',StateMachine.ERROR_STATE,tokenAscii);


        StateMachine.addTransition(7, '_',StateMachine.ERROR_STATE,new AS_Next(this));
        StateMachine.addTransition(7, 'a',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(7, '1',8,new AS_Next(this));
        StateMachine.addTransition(7, 'i',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(7, ' ',StateMachine.ERROR_STATE,espacio);
        StateMachine.addTransition(7, '\n',StateMachine.ERROR_STATE,new AS_NextLine(this));
        StateMachine.addTransition(7, '.',StateMachine.ERROR_STATE,new AS_Next(this));
        StateMachine.addTransition(7, 'F',StateMachine.ERROR_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(7, '*',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '-',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '+',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '&',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '=',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, ')',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '/',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '{',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '}',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, ',',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, ';',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '(',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, ':',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '<',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '>',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '!',StateMachine.ERROR_STATE,tokenAscii);
        StateMachine.addTransition(7, '\'',StateMachine.ERROR_STATE,tokenAscii);


        StateMachine.addTransition(8, '_',StateMachine.FINAL_STATE,new AS_Next(this));
        StateMachine.addTransition(8, 'a',StateMachine.FINAL_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(8, '1',8,new AS_Next(this));
        StateMachine.addTransition(8, 'i',StateMachine.FINAL_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(8, ' ',StateMachine.FINAL_STATE,espacio);
        StateMachine.addTransition(8, '\n',StateMachine.FINAL_STATE,new AS_NextLine(this));
        StateMachine.addTransition(8, '.',StateMachine.FINAL_STATE,new AS_Next(this));
        StateMachine.addTransition(8, 'F',StateMachine.FINAL_STATE,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(8, '*',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '-',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '+',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '&',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '=',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, ')',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '/',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '{',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '}',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, ',',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, ';',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '(',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, ':',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '<',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '>',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '!',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(8, '\'',StateMachine.FINAL_STATE,tokenAscii);


        StateMachine.addTransition(9, '_',9,new AS_Next(this));
        StateMachine.addTransition(9, 'a',9,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(9, '1',StateMachine.FINAL_STATE,new AS_Next(this));
        StateMachine.addTransition(9, 'i',9,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(9, ' ',StateMachine.FINAL_STATE,espacio);
        StateMachine.addTransition(9, '\n',StateMachine.FINAL_STATE,new AS_NextLine(this));
        StateMachine.addTransition(9, '.',StateMachine.FINAL_STATE,new AS_Next(this));
        StateMachine.addTransition(9, 'F',9,new AS_ErrorNotLexema(this));
        StateMachine.addTransition(9, '*',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '-',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '+',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '&',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '=',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, ')',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '/',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '{',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '}',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, ',',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, ';',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '(',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, ':',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '<',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '>',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '!',StateMachine.FINAL_STATE,tokenAscii);
        StateMachine.addTransition(9, '\'',StateMachine.FINAL_STATE,tokenAscii);


//en la accion semantica de cualquier simbolo que no sea = contemplamos la historia (<>) o (!:)
//si tiene que saltar un error o devolver un token, porque vaya al final o al error
//la accion semantica  es la que decide
        StateMachine.addTransition(10, '_',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, 'a',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '1',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, 'i',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, ' ',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '\n',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '.',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, 'F',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '*',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '-',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '+',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '&',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '=',StateMachine.FINAL_STATE,asignacion_Comparacion);
        StateMachine.addTransition(10, ')',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '/',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '{',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '}',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, ',',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, ';',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '(',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, ':',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '<',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '>',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '!',StateMachine.FINAL_STATE,comp_error);
        StateMachine.addTransition(10, '\'',StateMachine.FINAL_STATE,comp_error);



        StateMachine.addTransition(11, '_',11,espacio);
        StateMachine.addTransition(11, 'a',11,espacio);
        StateMachine.addTransition(11, '1',11,espacio);
        StateMachine.addTransition(11, 'i',11,espacio);
        StateMachine.addTransition(11, ' ',11,espacio);
        StateMachine.addTransition(11, '\n',StateMachine.INITIAL_STATE,saltoLinea);
        StateMachine.addTransition(11, '.',11,espacio);
        StateMachine.addTransition(11, 'F',11,espacio);
        StateMachine.addTransition(11, '*',11,espacio);
        StateMachine.addTransition(11, '-',11,espacio);
        StateMachine.addTransition(11, '+',11,espacio);
        StateMachine.addTransition(11, '&',11,espacio);
        StateMachine.addTransition(11, '=',11,espacio);
        StateMachine.addTransition(11, ')',11,espacio);
        StateMachine.addTransition(11, '/',11,espacio);
        StateMachine.addTransition(11, '{',11,espacio);
        StateMachine.addTransition(11, '}',11,espacio);
        StateMachine.addTransition(11, ',',11,espacio);
        StateMachine.addTransition(11, ';',11,espacio);
        StateMachine.addTransition(11, '(',11,espacio);
        StateMachine.addTransition(11, ':',11,espacio);
        StateMachine.addTransition(11, '<',11,espacio);
        StateMachine.addTransition(11, '>',11,espacio);
        StateMachine.addTransition(11, '!',11,espacio);
        StateMachine.addTransition(11, '\'',11,espacio);



        StateMachine.addTransition(12, '_',12,next);
        StateMachine.addTransition(12, 'a',12,next);
        StateMachine.addTransition(12, '1',12,next);
        StateMachine.addTransition(12, 'i',12,next);
        StateMachine.addTransition(12, ' ',12,next);
        StateMachine.addTransition(12, '\n',12,new AS_NextLineCadena(this));
        StateMachine.addTransition(12, '.',12,next);
        StateMachine.addTransition(12, 'F',12,next);
        StateMachine.addTransition(12, '*',12,next);
        StateMachine.addTransition(12, '-',12,next);
        StateMachine.addTransition(12, '+',12,next);
        StateMachine.addTransition(12, '&',12,next);
        StateMachine.addTransition(12, '=',12,next);
        StateMachine.addTransition(12, ')',12,next);
        StateMachine.addTransition(12, '/',12,next);
        StateMachine.addTransition(12, '{',12,next);
        StateMachine.addTransition(12, '}',12,next);
        StateMachine.addTransition(12, ',',12,next);
        StateMachine.addTransition(12, ';',12,next);
        StateMachine.addTransition(12, '(',12,next);
        StateMachine.addTransition(12, ':',12,next);
        StateMachine.addTransition(12, '<',12,next);
        StateMachine.addTransition(12, '>',12,next);
        StateMachine.addTransition(12, '!',12,next);
        StateMachine.addTransition(12, '\'',StateMachine.FINAL_STATE,new AS_Id_Start(this));

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


    public boolean isReservedWord(String lexema){
        //completar
        if (lexema == "loop")
            return true;
        return false;
    }
    public int  getNextToken(){
        state = StateMachine.INITIAL_STATE;
        Character symbol;
        while (state != StateMachine.FINAL_STATE){
            if (index >=srcCode.length()){
                return -1;
            }
            symbol = srcCode.charAt(index);
            int old=state;
            state = StateMachine.getNextState(state,symbol);
            StateMachine.getSemanticAction(old,symbol).Action(symbol);



        }


        return tokenId;
    }

}
