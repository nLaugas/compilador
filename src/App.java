import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import Errors.Errors;
import SymbolTable.SymbolTable;
import SymbolTable.SymbolTable;

import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class App extends JFrame{

    private JPanel panel1;
    private JTextArea textArea2;
    private JButton genArchButton;
    private JFileChooser file = new JFileChooser();
    String archivo="";
    private Errors errors;
    private OutFile outFile = new OutFile();
    private GeneradorAssembler genAssembler;
    //este metodo es solo para mostrar el contenido de manera prolija
    //al momento de apretar el boton "token"
    public String mostrarToken(int valor){

        if (valor == 0){
            return "EOF";
        }
        else if (valor == Parser.ID){
            return "IDENTIFICADOR";
        }
        else if (valor == Parser.ENTERO){
            return "ENTERO";
        }
        else if (valor == Parser.FLOTANTE){
            return "FLOTANTE";
        }
        else if (valor == Parser.CADENA){
            return "CADENA DE CARACTER";
        }
        else if (valor == Parser.ASIG){
            return "ASIGNACION";
        }
        else if (valor == Parser.ID) {
            return "IDENTIFICADOR";
        }



        else if (valor == Parser.ELSE) {
            return "PALABRA RESERVADA ELSE";
        }

        else if (valor == Parser.END_IF) {
            return "PALABRA RESERVADA END_IF";
        }
        else if (valor == Parser.IF) {
            return "PALABRA RESERVADA IF";
        }
        else if (valor == Parser.INTEGER) {
            return "PALABRA RESERVADA INTEGER";
        }
        else if (valor == Parser.LET) {
            return "PALABRA RESERVADA LET";
        }

        else if (valor == Parser.MUT) {
            return "PALABRA RESERVADA MUT";
        }

        else if (valor == Parser.LOOP) {
            return "PALABRA RESERVADA LOOP";
        }
        else if (valor == Parser.UNTIL) {
            return "PALABRA RESERVADA UNTIL";
        }

        else if (valor == Parser.PRINT) {
            return "PALABRA RESERVADA PRINT";
        }

        else if (valor == Parser.SINGLE) {
            return "PALABRA RESERVADA SINGLE";
        }
        else if (valor == Parser.MAYIG) {
            return "OPERADOR MAYOR IGUAL";
        }
        else if (valor == Parser.MENIG) {
            return "OPERADOR MENOR IGUAL";
        }
        else if (valor == Parser.DIST) {
            return "DISTINTO";
        }

        else if (valor < 255) {
            return "SIMBOLO "+(char)valor;
        }
        return "";
    }


    public App(/*String srcCode*/)  throws IOException {

       String srcCode ="srcCode"; /**Eliminar para compilar**/
       FileReader file = new FileReader(srcCode);
       BufferedReader src= new BufferedReader(file);
       String cadena;

        while ((cadena = src.readLine()) != null)
          archivo += cadena+"\n";
        archivo = archivo.substring(0,archivo.length()-1);
        errors = new Errors();
        final SymbolTable st = new SymbolTable();
        final LexicalAnalyzer lexical = new LexicalAnalyzer(archivo,st,errors);
        final Parser par = new Parser(lexical,st,errors);

        add(panel1);
        setSize(700,500);
        textArea2.append(archivo);// muestra archivo


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        genArchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                par.run();
                genAssembler = new GeneradorAssembler(par,st);
                outFile.tlFile(st,"tablaSimbolos.txt");
                outFile.tokenFile(par, "token.txt");
                outFile.structFile(par,"estructurasReconocidas.txt");
                outFile.errorFiles(errors,"errores.txt");
                outFile.tercetoFile(par,"terceto.txt");
                outFile.assemblerFile(genAssembler.getCodigoAssembler(),"assembler.asm");
            }
        });

    }
}
