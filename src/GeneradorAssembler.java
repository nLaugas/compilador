import AnalizadorSintactico.Parser;
import SymbolTable.SymbolTable;
import Tercetos.Terceto;

import java.util.Vector;
import java.util.ArrayList;

public class GeneradorAssembler {
	private static final int ID = 0;
	private static final int TIPO = 1;

	private Parser parser;
	private ArrayList<Terceto> tercetos;
	private SymbolTable tabla;
	private Vector<String> codAss;
	private Vector<String> codigo;
	
	public GeneradorAssembler(Parser p, SymbolTable ts)
	{
		parser = p;
		//tercetos = parser.getTercetos();
		tercetos = p.listaTercetos;
		tabla = ts;
	}
	private Vector<String> inicializacion() {
   	 Vector<String> inicializacion = new Vector<String>();
        inicializacion.add(new String(".386"));
        inicializacion.add(new String(".model flat, stdcall"));
        inicializacion.add(new String("option casemap :none"));
        inicializacion.add(new String("include \\masm32\\include\\windows.inc"));
        inicializacion.add(new String("include \\masm32\\include\\kernel32.inc"));
        inicializacion.add(new String("include \\masm32\\include\\user32.inc"));
        inicializacion.add(new String("includelib \\masm32\\lib\\kernel32.lib"));
        inicializacion.add(new String("includelib \\masm32\\lib\\user32.lib"));
        return inicializacion;
   }

	
	private Vector<String> declaracionDeVariables(){
        Vector<String> variables = new Vector<String>();
        variables.add(new String(".DATA"));
        variables.add(new String("TITULO DB \"Mensaje\" , 0"));
        variables.add(new String("OVERFLOW_EN_SUMA DB \"Overflow en suma\" , 0"));
        variables.add(new String("RESULTADO_NEGATIVO_RESTA DB \"Resultado negativo en resta\" , 0"));
        variables.add(new String("PERDIDA_INFORMACION_CONVERSION DB \"Perdida de informacion en conversion\" , 0"));
        variables.add(new String(""));
        variables.add(new String("_@cero DW 0,0"));
        variables.add(new String("_@max_double1 DQ 1.7976931348623157e308"));
        variables.add(new String("_@min_double1 DQ -1.7976931348623157e308"));
        variables.add(new String("_@max_double2 DQ 2.2250738585072014e-308 "));
        variables.add(new String("_@min_double2 DQ -2.2250738585072014e-308"));
        variables.add(new String("_@max_ulong DD 4294967295"));
        variables.add(new String("_@min_ulong DD 0"));

        int cant = 1;
        int cant2 = 1;
        
        ArrayList<String> claves = tabla.getEntradas();
        for(String lexema: claves){
			  //for (Object o: t.get( clave ))
     //   for (int i = 0; i< e.size(); i++){
			//TablaSimbolo entrada = new TablaSimbolo((short)0, null);
			//entrada = e.elementAt(i);
        	if(tabla.getSymbol(lexema).getTipo()  == 261) // tipo ID
				if (tabla.getSymbol(lexema).getTipoVar().equals("float"))
					variables.add(new String("_" + lexema + " DQ ?")); // resservo espacio para float
				else
					variables.add(new String("_" + lexema+ " DD ?"));	// resservo espacio para float
			else if (tabla.getSymbol(lexema).getTipo() == 269){ // 269 es token de single
					variables.add(new String("_float" +  cant + " DQ " + lexema)); // ver si esta bien esto
					cant++;
				}
			else if (tabla.getSymbol(lexema).getTipo() == 275){
					variables.add(new String("_integer" +  cant2 + " DD " + lexema));
					cant2++;
				}
				/*
			else if (tabla.getSymbol(lexema).getTipo() == 300){
				if ((String)tabla.getElemento(lexema,TIPO) == "DOUBLE")
					variables.addElement(new String("_"+lexema+ " DQ ?"));
				else
					variables.addElement(new String("_"+lexema+ " DD ?"));
					*/
			//}
			}

        Vector <String> msj; 
        for (int i = 0; i<tercetos.size(); i++){
 		   Terceto t2 = tercetos.get(i);
 		   if (t2.getTipo() == "cadena")
 			 	variables .add(new String("mensaje"+ i + " DB " + "\"" + (String) t2.getOperando1()+ "\"" + " ,0"));
 	   }
    
        return variables;
	}
	public Vector<String> getCodigoAssembler()
	{
		codAss = new Vector<String>();
		tercetos = parser.listaTercetos;
		codAss.add(new String(".CODE"));
        codAss.add(new String("START:"));
		for (Terceto t: tercetos){
			if (((String) t.getOperador()).equals("BF")){
				tercetos.get((int)t.getOperando2()).setLabel();}
			if ((t.getLabel()!="") /*&& ((t.getOperador() != "FIN_DE_SALTO")|| (t.getOperador() != "FIN_CASE"))*/)
				codAss.add(new String(t.getLabel()));
		codAss.addAll(t.getAssembler());
		}
		this.codigo = this.inicializacion();
		this.codigo.addAll(this.declaracionDeVariables());
		this.codigo.addAll(codAss);
		codigo.add(new String("EXIT:"));
		/*codigo.add(new String("invoke ExitProcess, 0"));
		codigo.add(new String("OVERFLOW_EN_SUMA"));
		codigo.add(new String("invoke MessageBox, NULL, addr OVERFLOW_EN_SUMA, addr TITULO , MB_OK " ));
		codigo.add(new String("JMP EXIT"));
		codigo.add(new String("RESULTADO_NEGATIVO_RESTA"));
		codigo.add(new String("invoke MessageBox, NULL, addr RESULTADO_NEGATIVO_RESTA, addr TITULO , MB_OK " ));
		codigo.add(new String("JMP EXIT"));
		codigo.add(new String("PERDIDA_INFORMACION_CONVERSION"));
		codigo.add(new String("invoke MessageBox, NULL, addr PERDIDA_INFORMACION_CONVERSION, addr TITULO , MB_OK " ));
		codigo.add(new String("JMP EXIT"));*/
		codigo.add(new String("END START"));
		return codigo;
	}
}	