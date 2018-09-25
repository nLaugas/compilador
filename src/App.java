import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;

import AnalizadorLexico.LexicalAnalyzer;
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
    private JEditorPane editorPane1;
    private JFileChooser file = new JFileChooser();
    private JPanel a;
    String archivo="";

    public App()  throws IOException {
        FileReader file = new FileReader("src/srcCode");
        BufferedReader src= new BufferedReader(file);
        String cadena;
        while ((cadena = src.readLine()) != null)
          archivo += cadena+"\n";

        SymbolTable st = new SymbolTable();
        LexicalAnalyzer lexical = new LexicalAnalyzer(archivo,st);
        add(panel1);
        setSize(400,500);

        compilarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tokenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String token=lexical.getNextToken();
                textArea1.append(token);

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
