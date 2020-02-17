import javax.swing.*;


import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import Errors.Errors;
import SymbolTable.*;
import Tercetos.Terceto;

import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;


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



    public void optimizaTercetos(ArrayList<Terceto> tercetos) {
        System.out.println("optimizando");

        int tam= tercetos.size();

        for (int i = 0; i<tam; i++) {
            Terceto t = tercetos.get(i);

            int tipo;
            // ver si eliminar primer operador
            if (t.esTerceto(1) && t.reemplazoTercetoOptimizar.containsKey(((Terceto)t.operando1).getnum()) ){
                System.out.println("este terceto se puede eliminar componente 1 " + t.getnum());
               if (((Terceto)t.getOperando1()).getTipo().equals("single")){
                   tipo = 276;
                }else{
                   tipo = 275;
               }
                t.setOperando1(new Symbol(t.reemplazoTercetoOptimizar.get(((Terceto)t.operando1).getnum()).toString(),tipo ));
            }

            //ver si eliminar segundo operador
            if (t.esTerceto(2) && t.reemplazoTercetoOptimizar.containsKey(((Terceto)t.operando2).getnum()) ){
                System.out.println("este terceto se puede eliminar componente 2 " + t.getnum());
                if (((Terceto)t.getOperando2()).getTipo().equals("single")){
                    tipo = 276;
                }else{
                    tipo = 275;
                }
                t.setOperando2(new Symbol(t.reemplazoTercetoOptimizar.get(((Terceto)t.operando2).getnum()).toString(),tipo ));
            }

            //marcar para eliminar
            if (t.reemplazoTercetoOptimizar.containsKey(t.getnum())){
                // estoy en un terceto a eliminar o marcar como eliminar
                System.out.println("este terceto se puede eliminar " + t.getnum());
                t.setnum(999);

            }
        }

        Enumeration e = tercetos.get(0).reemplazoTercetoOptimizar.keys();
		Object clave;
		Object valor;
		while( e.hasMoreElements() ){
			clave = e.nextElement();
			valor = tercetos.get(0).reemplazoTercetoOptimizar.get( clave );
			System.out.println( "Clave : " + clave + " - Valor : " + valor );
			tercetos.get((Integer) clave).setnum(999);
		}

    }

    public App(/*String srcCode*/)  throws IOException {

       String srcCode ="srcCode"; /**Eliminar para compilar**/
       FileReader file = new FileReader(srcCode);
       BufferedReader src= new BufferedReader(file);
       String cadena;

        while ((cadena = src.readLine()) != null)
          archivo += cadena+"\n";
          archivo+=  "\n";
          archivo+=  "\n";
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

                try{ Thread.sleep(100); } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }


                outFile.tlFile(st, "tablaSimbolos.txt");
                outFile.tokenFile(par, "token.txt");
                outFile.structFile(par, "estructurasReconocidas.txt");
                outFile.errorFiles(errors, "errores.txt");

                optimizaTercetos(par.listaTercetos);
                outFile.tercetoFile(par, "terceto.txt");
                genAssembler = new GeneradorAssembler(par, st);
               // genAssembler.optimizaTercetos();

                outFile.tercetoFile(genAssembler.getTercetosOptimizado(),"tercetoOptimizado.txt");
                if (errors.getAll().length() == 0){
                    //optimizaTercetos(par.listaTercetos);
                    outFile.assemblerFile(genAssembler.getCodigoAssembler(), "assembler.asm");
                    outFile.tercetoFile(genAssembler.getTercetosOptimizado(),"tercetoOptimizado.txt");

                }

                String comc = "C:\\masm32\\bin\\ml /c /Zd /coff assembler.asm ";
                String coml = "C:\\masm32\\bin\\Link /SUBSYSTEM:CONSOLE assembler.obj ";
                Process ptasm32 = null;
                Process ptlink32 = null;

                try {
                    ptasm32 = Runtime.getRuntime().exec(comc);
                    ptlink32 = Runtime.getRuntime().exec(coml);
                } catch (IOException e1) {
                    System.out.println("No se puede crear ejecutable, revise estructura de errores ");
                }
            }
        });

    }
}
