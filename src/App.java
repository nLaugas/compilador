import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;

import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import Errors.Errors;
import SymbolTable.SymbolTable;
import SymbolTable.SymbolTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class App extends JFrame{
    private JTabbedPane tabbedPane1;
    private JButton compilarButton;
    private JButton tokenButton;
    private JPanel panel1;
    private JTextPane textPane1;
    private JTextArea textArea1;
    private JButton archivoButton;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JEditorPane editorPane1;
    private JFileChooser file = new JFileChooser();
    private JPanel a;
    String archivo="";
    private Errors errors;
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

    public App()  throws IOException {
        FileReader file = new FileReader("src/srcCode");
        BufferedReader src= new BufferedReader(file);
        String cadena;

        while ((cadena = src.readLine()) != null)
          archivo += cadena+"\n";
        archivo = archivo.substring(0,archivo.length()-1);
        errors = new Errors();
        final SymbolTable st = new SymbolTable();
        System.out.println(cadena);
        final LexicalAnalyzer lexical = new LexicalAnalyzer(archivo,st,errors);
        final Parser par = new Parser(lexical,st,errors);

        add(panel1);
        setSize(700,500);


        //al presionar compila y consume todos los token
        compilarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                par.run();
                textArea1.removeAll();
                textArea3.removeAll();
                textArea3.append("ERRORES \n \n");
                while (!errors.isEmpty()) {
                    textArea3.append(errors.getError() + " fila " + errors.getRow() + " columna " + errors.getColumn() + "\n");
                }
                textArea1.append("TABLA DE SIMBOLOS \n\n");
                while (!st.isEmpty()) {
                    textArea1.append(st.getAtributosNextLexema() + "\n");
                }
                compilarButton.setEnabled(false);
                tokenButton.setEnabled(false);

            }

        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //al presionar va retornando token y mostrandolos para probar solo
        //el analizador lexico
        tokenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int token=lexical.getNextToken();
                if (token != -1) {
                    if (token == 0)
                        textArea1.append("fin de archivo\n");
                    textArea1.append(mostrarToken(token)+"\n");
                }
                if (!errors.isEmpty()) {
                    textArea3.append(errors.getError() + " fila " + errors.getRow() + " columna " + errors.getColumn() + "\n");
                }

                compilarButton.setEnabled(false);
            }
        });


        archivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textArea2.append(archivo);
                tokenButton.setEnabled(true);
                compilarButton.setEnabled(true);
            }
        });
    }
}
