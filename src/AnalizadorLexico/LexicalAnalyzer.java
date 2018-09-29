package AnalizadorLexico;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import AnalizadorLexico.SemanticAction.*;
import AnalizadorLexico.StateMachine.StateMachine;
import AnalizadorSintactico.Parser;
import Errors.*;
import SymbolTable.*;

public class LexicalAnalyzer {


    public final int MAX_WORD_SIZE = 25;
    public final int MAX_INT_SIZE = (int)Math.pow(2,15);
    public final int MIN_INT_SIZE = (int)-Math.pow(2,15)-1;
    public final float MIN_FLOAT_SIZE = (float) Math.pow(1.17549435,-38);
    public final float MAX_FLOAT_SIZE = (float) Math.pow(3.40282347,38);



    public String srcCode;
    public int row; //controla cada \n del string
    public int column;
    public SymbolTable symbolTable;
    public String buffer;
    public int state;
    public int index; //cursor para seguir el string que viene de forma lineal _a = 6; \n if..
    public int tokenId;
    public Errors errors;
    public Hashtable<String,Integer> reservedWords;
    private void create(){

        //
        SemanticAction tokenAscii = new AS_TokenAscii(this);
        SemanticAction asignacion_Comparacion = new AS_Asignacion_Comparacion(this);
        SemanticAction comp_error = new AS_Comparador_Error(this);
        SemanticAction next = new AS_Next(this);//avanza y guarda en el buffer
        SemanticAction next_line= new AS_NextLine(this);//solo avanza fila
        SemanticAction next_espace = new AS_NextSpace(this);//solo avanza columna
        SemanticAction comentario = new AS_Vaciar_Buffer(this);
        SemanticAction entero_end = new AS_Entero_End(this);
        SemanticAction entero_start = new AS_Entero_Start(this);
        SemanticAction flotante_end = new AS_Flotante_End(this);
        SemanticAction flotante_start = new AS_Flotante_Start(this);
        SemanticAction cadena_start = new AS_Cadena_Start(this);
        SemanticAction cadena_end = new AS_Cadena_End(this);
        SemanticAction next_line_cadena = new AS_NextLineCadena(this);
        SemanticAction id_end = new AS_Id_End(this);
        SemanticAction id_start = new AS_Id_Start(this);
        SemanticAction not_lexema = new AS_ErrorNotLexema(this);
        SemanticAction palabra_reservada = new AS_Palabra_Reservada(this);



        StateMachine.addTransition(0, '_',1,next); //OK
        StateMachine.addTransition(0, 'a',9,next); //OK
        StateMachine.addTransition(0, '1',2,next); //OK
        StateMachine.addTransition(0, 'i',9,next); //OK
        StateMachine.addTransition(0, ' ',0,next_espace);//OK
        StateMachine.addTransition(0, '\n',0,next_line); //OK
        StateMachine.addTransition(0, '.',4,next); //OK
        StateMachine.addTransition(0, 'F',9,next); //OK
        StateMachine.addTransition(0, '*',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, '-',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, '+',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, '&',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, '=',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, ')',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, '/',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, '{',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, '}',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, ',',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, ';',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, '(',StateMachine.FINAL_STATE,tokenAscii);//OK
        StateMachine.addTransition(0, ':',10,next);//OK
        StateMachine.addTransition(0, '<',10,next);//OK
        StateMachine.addTransition(0, '>',10,next);//OK
        StateMachine.addTransition(0, '!',10,next);//OK
        StateMachine.addTransition(0, '\'',12,cadena_start);//OK


        //13 es el estado para no conocer _ solo, este obliga a tener un d/l

        StateMachine.addTransition(1, '_',11,comentario); //OK
        StateMachine.addTransition(1, 'a',13,id_start);//OK
        StateMachine.addTransition(1, '1',13,id_start); //OK
        StateMachine.addTransition(1, 'i',13,id_start); //OK
        StateMachine.addTransition(1, ' ',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(1, '\n',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, '.',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, 'F',13,id_start); //OK
        StateMachine.addTransition(1, '*',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(1, '-',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(1, '+',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(1, '&',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, '=',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, ')',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, '/',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, '{',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, '}',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, ',',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, ';',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, '(',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, ':',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, '<',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, '>',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, '!',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(1, '\'',StateMachine.ERROR_STATE,not_lexema);//OK


        StateMachine.addTransition(2, '_',3,entero_start); //OK
        StateMachine.addTransition(2, 'a',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(2, '1',2,next); //OK
        StateMachine.addTransition(2, 'i',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(2, ' ',StateMachine.ERROR_STATE,not_lexema);//Ok
        StateMachine.addTransition(2, '\n',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(2, '.',5,flotante_start);//OK
        StateMachine.addTransition(2, 'F',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '*',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '-',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '+',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '&',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '=',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, ')',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '/',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '{',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '}',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, ',',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, ';',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '(',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, ':',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '<',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '>',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '!',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(2, '\'',StateMachine.ERROR_STATE,not_lexema); //OK

        StateMachine.addTransition(3, '_',StateMachine.ERROR_STATE,not_lexema);//OK//OK
        StateMachine.addTransition(3, 'a',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '1',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, 'i',StateMachine.FINAL_STATE,entero_end);//OK
        StateMachine.addTransition(3, ' ',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '\n',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '.',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, 'F',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '*',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '-',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '+',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '&',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '=',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, ')',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '/',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '{',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '}',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, ',',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, ';',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '(',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, ':',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '<',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '>',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '!',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(3, '\'',StateMachine.ERROR_STATE,not_lexema);//OK


        StateMachine.addTransition(4, '_',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(4, 'a',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(4, '1',5,flotante_start); //OK
        StateMachine.addTransition(4, 'i',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(4, ' ',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(4, '\n',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(4, '.',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(4, 'F',StateMachine.ERROR_STATE,not_lexema); //OK
        StateMachine.addTransition(4, '*',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '-',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '+',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '&',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '=',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, ')',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '/',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '{',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '}',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, ',',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, ';',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '(',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, ':',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '<',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '>',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '!',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(4, '\'',StateMachine.ERROR_STATE,not_lexema);//OK


        StateMachine.addTransition(5, '_',StateMachine.FINAL_STATE,flotante_end); //OK
        StateMachine.addTransition(5, 'a',StateMachine.FINAL_STATE,flotante_end); //OK
        StateMachine.addTransition(5, '1',5,next); //OK
        StateMachine.addTransition(5, 'i',StateMachine.FINAL_STATE,flotante_end); //OK
        StateMachine.addTransition(5, ' ',StateMachine.FINAL_STATE,flotante_end); //OK
        StateMachine.addTransition(5, '\n',StateMachine.FINAL_STATE,flotante_end); //OK
        StateMachine.addTransition(5, '.',StateMachine.FINAL_STATE,flotante_end); //OK
        StateMachine.addTransition(5, 'F',6,next); //OK
        StateMachine.addTransition(5, '*',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '-',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '+',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '&',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '=',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, ')',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '/',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '{',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '}',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, ',',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, ';',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '(',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, ':',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '<',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '>',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '!',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(5, '\'',StateMachine.FINAL_STATE,flotante_end);//OK

        StateMachine.addTransition(6, '_',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, 'a',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '1',8,next);//OK
        StateMachine.addTransition(6, 'i',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, ' ',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '\n',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '.',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, 'F',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '*',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '-',7,next);//OK
        StateMachine.addTransition(6, '+',7,next);//OK
        StateMachine.addTransition(6, '&',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '=',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, ')',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '/',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '{',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '}',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, ',',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, ';',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '(',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, ':',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '<',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '>',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '!',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(6, '\'',StateMachine.ERROR_STATE,not_lexema);//OK


        StateMachine.addTransition(7, '_',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, 'a',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '1',8,next);//OK
        StateMachine.addTransition(7, 'i',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, ' ',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '\n',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '.',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, 'F',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '*',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '-',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '+',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '&',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '=',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, ')',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '/',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '{',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '}',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, ',',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, ';',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '(',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, ':',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '<',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '>',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '!',StateMachine.ERROR_STATE,not_lexema);//OK
        StateMachine.addTransition(7, '\'',StateMachine.ERROR_STATE,not_lexema);//OK


        StateMachine.addTransition(8, '_',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, 'a',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '1',8,next);
        StateMachine.addTransition(8, 'i',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, ' ',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '\n',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '.',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, 'F',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '*',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '-',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '+',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '&',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '=',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, ')',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '/',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '{',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '}',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, ',',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, ';',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '(',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, ':',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '<',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '>',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '!',StateMachine.FINAL_STATE,flotante_end);//OK
        StateMachine.addTransition(8, '\'',StateMachine.FINAL_STATE,flotante_end);//OK


        StateMachine.addTransition(9, '_',9,next);//OK
        StateMachine.addTransition(9, 'a',9,next);//OK
        StateMachine.addTransition(9, '1',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, 'i',9,next);//OK
        StateMachine.addTransition(9, ' ',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '\n',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '.',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, 'F',9,next);//OK
        StateMachine.addTransition(9, '*',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '-',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '+',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '&',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '=',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, ')',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '/',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '{',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '}',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, ',',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, ';',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '(',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, ':',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '<',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '>',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '!',StateMachine.FINAL_STATE,palabra_reservada);//OK
        StateMachine.addTransition(9, '\'',StateMachine.FINAL_STATE,palabra_reservada);//OK


//en la accion semantica de cualquier simbolo que no sea = contemplamos la historia (<>) o (!:)
//si tiene que saltar un error o devolver un token, porque vaya al final o al error
//la accion semantica  es la que decide
        StateMachine.addTransition(10, '_',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, 'a',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '1',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, 'i',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, ' ',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '\n',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '.',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, 'F',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '*',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '-',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '+',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '&',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '=',StateMachine.FINAL_STATE,asignacion_Comparacion);//OK
        StateMachine.addTransition(10, ')',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '/',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '{',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '}',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, ',',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, ';',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '(',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, ':',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '<',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '>',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '!',StateMachine.ERROR_STATE,comp_error);//OK
        StateMachine.addTransition(10, '\'',StateMachine.ERROR_STATE,comp_error);//OK




        StateMachine.addTransition(11, '_',11,next_espace);
        StateMachine.addTransition(11, 'a',11,next_espace);
        StateMachine.addTransition(11, '1',11,next_espace);
        StateMachine.addTransition(11, 'i',11,next_espace);
        StateMachine.addTransition(11, ' ',11,next_espace);
        StateMachine.addTransition(11, '\n',StateMachine.INITIAL_STATE,next_line);
        StateMachine.addTransition(11, '.',11,next_espace);
        StateMachine.addTransition(11, 'F',11,next_espace);
        StateMachine.addTransition(11, '*',11,next_espace);
        StateMachine.addTransition(11, '-',11,next_espace);
        StateMachine.addTransition(11, '+',11,next_espace);
        StateMachine.addTransition(11, '&',11,next_espace);
        StateMachine.addTransition(11, '=',11,next_espace);
        StateMachine.addTransition(11, ')',11,next_espace);
        StateMachine.addTransition(11, '/',11,next_espace);
        StateMachine.addTransition(11, '{',11,next_espace);
        StateMachine.addTransition(11, '}',11,next_espace);
        StateMachine.addTransition(11, ',',11,next_espace);
        StateMachine.addTransition(11, ';',11,next_espace);
        StateMachine.addTransition(11, '(',11,next_espace);
        StateMachine.addTransition(11, ':',11,next_espace);
        StateMachine.addTransition(11, '<',11,next_espace);
        StateMachine.addTransition(11, '>',11,next_espace);
        StateMachine.addTransition(11, '!',11,next_espace);
        StateMachine.addTransition(11, '\'',11,next_espace);



        StateMachine.addTransition(12, '_',12,next);
        StateMachine.addTransition(12, 'a',12,next);
        StateMachine.addTransition(12, '1',12,next);
        StateMachine.addTransition(12, 'i',12,next);
        StateMachine.addTransition(12, ' ',12,next);
        StateMachine.addTransition(12, '\n',12,next_line_cadena);
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
        StateMachine.addTransition(12, '\'',StateMachine.FINAL_STATE,cadena_end);//OK


        StateMachine.addTransition(13, '_',13,next);//OK
        StateMachine.addTransition(13, 'a',13,next);//OK
        StateMachine.addTransition(13, '1',13,next);//OK
        StateMachine.addTransition(13, 'i',13,next);//OK
        StateMachine.addTransition(13, ' ',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '\n',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '.',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, 'F',13,next);//OK
        StateMachine.addTransition(13, '*',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '-',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '+',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '&',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '=',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, ')',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '/',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '{',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '}',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, ',',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, ';',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '(',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, ':',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '<',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '>',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '!',StateMachine.FINAL_STATE,id_end);
        StateMachine.addTransition(13, '\'',StateMachine.FINAL_STATE,id_end);//OK
    }
    private void addReservedWord(){
        reservedWords.put("if", (int) Parser.PR_IF);
        reservedWords.put("else", (int) Parser.PR_ELSE);
        reservedWords.put("end_if", (int) Parser.PR_END_IF);
        reservedWords.put("print", (int) Parser.PR_PRINT);
        reservedWords.put("integer", (int) Parser.PR_INTEGER);
        reservedWords.put("single", (int) Parser.PR_SINGLE);
        reservedWords.put("loop", (int) Parser.PR_LOOP);
        reservedWords.put("until", (int) Parser.PR_UNTIL);
        reservedWords.put("let", (int) Parser.PR_LET);
        reservedWords.put("mut", (int) Parser.PR_MUT);
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
        reservedWords = new Hashtable<String, Integer>();
        addReservedWord();

    }


    public boolean isReservedWord(String lexema){
        //completar
        if (lexema == "loop")
            return true;
        return false;
    }
    public int  getNextToken(){
        tokenId = -1;
        state = StateMachine.INITIAL_STATE;
        Character symbol;
        while (state != StateMachine.FINAL_STATE){
            if (index >=srcCode.length()){
                return 0;
            }
            symbol = srcCode.charAt(index);
            int old=state;
            state = StateMachine.getNextState(state,symbol);
            StateMachine.getSemanticAction(old,symbol).Action(symbol);



        }


        return tokenId;
    }

}
