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

    public App()  throws IOException {
        FileReader file = new FileReader("src/srcCode");
        BufferedReader src= new BufferedReader(file);
        String cadena;

        while ((cadena = src.readLine()) != null)
          archivo += cadena+"\n";
        archivo = archivo.substring(0,archivo.length()-1);
        errors = new Errors();
        SymbolTable st = new SymbolTable();
        System.out.println(cadena);
        LexicalAnalyzer lexical = new LexicalAnalyzer(archivo,st,errors);
        Parser par = new Parser(lexical,st,errors);

        add(panel1);
        setSize(400,500);

        compilarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                par.run();
                textArea3.append("ERRORES \n \n");
                while (!errors.isEmpty()) {
                    textArea3.append(errors.getError() + " fila " + errors.getRow() + " columna " + errors.getColumn() + "\n");
                }
                textArea1.append("TABLA DE SIMBOLOS \n\n");
                while (!st.isEmpty()) {
                    textArea1.append(st.getAtributosNextLexema() + "\n");
                }

            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tokenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int token=lexical.getNextToken();
                if (token != -1) {
                    if (token == 0)
                        textArea1.append("fin de archivo\n");
                    textArea1.append(String.valueOf(token)+"\n");
                }
                if (!errors.isEmpty()) {
                    textArea3.append(errors.getError() + " fila " + errors.getRow() + " columna " + errors.getColumn() + "\n");
                }


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
