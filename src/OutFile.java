import AnalizadorLexico.LexicalAnalyzer;
import AnalizadorSintactico.Parser;
import AnalizadorSintactico.ParserVal;
import Errors.Errors;
import SymbolTable.SymbolTable;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OutFile {

    private void crear(String in,String ruta){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(ruta);
            pw = new PrintWriter(fichero);
            pw.println(in);

        } catch (IOException ee) {
            ee.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void tokenFile(Parser par,String ruta){
        String out = new String();
        for(String s :par.tokens){
            out+=s+"\n";
        }
        this.crear(out,ruta);
    }

    public void tlFile(SymbolTable st,String ruta){
        String out = new String();
        while (!st.isEmpty()) {
            out+=st.getAtributosNextLexema() + "\n";
        }
        this.crear(out,ruta);
    }

    public void structFile(Parser par, String ruta){
        String out = new String();
        for(ParserVal pv : par.estructuras){
            out+=pv.sval+"\n";
        }
        this.crear(out,ruta);
    }

    public void errorFiles(Errors errors,String ruta){
        this.crear(errors.getAll(),ruta);
    }
}
