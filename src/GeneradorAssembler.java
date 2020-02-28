import AnalizadorSintactico.*;
import SymbolTable.SymbolTable;
import Tercetos.Terceto;

import java.util.Enumeration;
import java.util.Set;
import SymbolTable.*;
import java.util.Vector;
import java.util.ArrayList;

public class GeneradorAssembler {
	private static final int ID = 0;
	private static final int TIPO = 1;

	private Parser parser;
	private ArrayList<Terceto> tercetos;
	private ArrayList<Terceto> tercetosOptimizado;
	private SymbolTable tabla;
	private Vector<String> codAss;
	private Vector<String> codigo;

	public GeneradorAssembler(Parser p, SymbolTable ts)
	{
		parser = p;
		//tercetos = parser.getTercetos();
		tercetos = p.listaTercetos;
		// revisar si anda bien el clone

		//System.out.println("optimizando");
		//this.optimizaTercetos();
		tercetosOptimizado = this.redundanciaSimple((ArrayList<Terceto>) p.listaTercetos.clone());
		tabla = ts;
	}

	private boolean noEsAsignada(int i, int j,ArrayList<Terceto> ter){
		Terceto ter_i = ter.get(i);
		Terceto ter_j = ter.get(j);

		if (ter_i.esTerceto(1) || ter_i.esTerceto(2) )
			return false;
		//para que no entren los tercetos que no son operadores arismeticos
		if (!(ter_i.getOperador().equals("+")|| ter_i.getOperador().equals("-")
			||ter_i.getOperador().equals("/")|| ter_i.getOperador().equals("*")))
			return false;

		for (int pos = i+1; pos<j; pos++){
			Terceto ter_pos = ter.get(pos);
			if (ter_pos.operador.equals(":=")){
				if (((Symbol)ter_pos.operando1).getLexema().equals( ((Symbol)ter_i.operando1).getLexema() ) ){
					return false;
				}
				if (((Symbol)ter_pos.operando1).getLexema().equals( ((Symbol)ter_i.operando2).getLexema() ) ){
					return false;
				}
			}
		}
		return true;
	}

	private ArrayList<Terceto> redundanciaSimple(ArrayList<Terceto> ter){
		ArrayList<Terceto> out = new ArrayList<>();

		// ver si recorro ter o tercetos

		int tam= ter.size();

		for (int i = 0; i<tam; i++){
			Terceto t1 = ter.get(i);

			for (int j = i; j<tam; j++){
				Terceto t2 = ter.get(j);

				if ( t1.getnum() != t2.getnum() ){
					String t1part = t1.toString().substring(4,t1.toString().length()-1);
					String t2part = t2.toString().substring(4,t2.toString().length()-1);

					if (t1part.equals(t2part) && this.noEsAsignada(i,j,ter)){

						System.out.println(" Encontro coincidencia en : "+t1part+ "  "+t2part);
					/** Tereceto redundancia **/
						ter.get(j).setnum(-1);
						//for (Terceto tAux : ter){
						 Terceto tAux = ter.get(j+1);
								/** solo necesito tercetos**/
								if (tAux.esTerceto(1)){
									if (((Terceto)tAux.getOperando1()).getnum() == t2.getnum()){
										/** Seteo el terceto */
										System.out.println("encontro en : " + ((Terceto)tAux.getOperando1()).getnum() );
										tAux.setOperando1(t1);
									}
								}
								if (tAux.esTerceto(2)){
									if (((Terceto)tAux.getOperando2()).getnum() == t2.getnum()){
										/** Seteo el terceto */
										System.out.println("encontro 2 en : " + ((Terceto)tAux.getOperando2()).getnum() );
										tAux.setOperando2(t1);
									}
								}
					//	}
					}
				}

			}
		}
		return  ter;
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
		variables.add(new String("DIVISION_POR_CERO DB \"Division por cero, error de ejecucion\" , 0"));
		variables.add(new String(""));
		variables.add(new String("_@cero DW 0,0"));
		variables.add(new String("_@max_float1 DQ 1.17549435e38"));
		variables.add(new String("_@min_float1 DQ -1.17549435e38"));
		variables.add(new String("_@max_float2 DQ 3.40282347e-38 "));
		variables.add(new String("_@min_float2 DQ -3.40282347e-38"));
		variables.add(new String("_@max_integer DD 32768"));
		variables.add(new String("_@min_integer DD -32768"));
		variables.add(new String("_memaux DW ?"));

		int cant = 1;
		int cant2 = 1;

		ArrayList<String> claves = tabla.getEntradas();
		for(String lexema: claves) {
			//for (Object o: t.get( clave ))
			//   for (int i = 0; i< e.size(); i++){
			//TablaSimbolo entrada = new TablaSimbolo((short)0, null);
			//entrada = e.elementAt(i);
			if (tabla.getSymbol(lexema).getTipo() == 261) // tipo ID
			{

				if ((tabla.getSymbol(lexema).getTipoVar().equals("single")) || (tabla.getSymbol(lexema).isEsPuntero()))
					variables.add(new String(lexema + " DD ?")); // resservo espacio para float
				else
					variables.add(new String(lexema + " DW ?"));    // resservo espacio para INTEGER
			}
			else if (tabla.getSymbol(lexema).getTipo() == 269){ // 269 es token de single
				variables.add(new String("_"+lexema +  cant + " DD " + lexema));
			}
			else if (tabla.getSymbol(lexema).getTipo() == 275){
				variables.add(new String( "_"+lexema + " DW " + lexema.substring(0, lexema.length() - 2)));
			}else if (tabla.getSymbol(lexema).getTipo() == Parser.CADENA){
				variables .add(new String("_"+lexema.replace(" ","_")+" DB " + "\"" + lexema+ "\"" + " ,0"));
			}else if(tabla.getSymbol(lexema).getTipo()  == 500){
				if (tabla.getSymbol(lexema).getTipoVar()=="integer")
					variables.add(new String("_"+lexema  + " DW ?"));
				else
					variables.add(new String("_"+lexema  + " DD ?"));
			}else if(tabla.getSymbol(lexema).getTipo() == 276){//tipo float
				variables.add(new String("_"+lexema.replace(".","p").replace("-","n")  + " DD "+lexema.replace("p",".")));
			}


/**
 else if (tabla.getSymbol(lexema).getTipo() == 300){
 if ((String)tabla.getElemento(lexema,TIPO) == "float")
 variables.addElement(new String("_"+lexema+ " DQ ?"));
 else
 variables.addElement(new String("_"+lexema+ " DD ?"));

 }**/
		}
/**
 Vector <String> msj;
 for (int i = 0; i<tercetos.size(); i++){
 Terceto t2 = tercetos.get(i);
 if (t2.getTipo() == "cadena")
 variables .add(new String("mensaje"+ i + " DB " + "\"" + (String) t2.getOperando1()+ "\"" + " ,0"));
 }**/

		return variables;
	}

	public ArrayList<Terceto> getTercetosOptimizado(){
		return this.tercetosOptimizado;
	}


	public Vector<String> getCodigoAssembler()
	{
		codAss = new Vector<String>();
		tercetos = parser.listaTercetos;
		codAss.add(new String(".CODE"));
		codAss.add(new String("START:"));
		for (Terceto t: tercetos){
			if (t.getnum()!=-1)
			{
				//System.out.println("LA LONGUITUD DE LA LISTA para reemplazo es  ES : "+ t.reemplazoTercetoOptimizar.size());
				if (((String) t.getOperador()).equals("BF")) {
					tercetos.get((int) t.getOperando2()).setLabel();
				}
				if ((t.getLabel() != "") && ((t.getOperador() != "FIN_DE_SALTO") || (t.getOperador() != "FIN_CASE")))
					codAss.add(new String(t.getLabel()));
				codAss.addAll(t.getAssembler());
			}
		}
		this.codigo = this.inicializacion();
		this.codigo.addAll(this.declaracionDeVariables());
		this.codigo.addAll(codAss);
		codigo.add(new String("jMP EXIT"));

		codigo.add(new String("@RESULTADO_NEGATIVO_RESTA:"));
		codigo.add(new String("Invoke MessageBox, NULL, addr RESULTADO_NEGATIVO_RESTA, addr RESULTADO_NEGATIVO_RESTA, MB_OK"));
		codigo.add(new String("Invoke ExitProcess, 0"));
		codigo.add(new String("@OVERFLOW_EN_SUMA:"));
		codigo.add(new String("Invoke MessageBox, NULL, addr OVERFLOW_EN_SUMA, addr OVERFLOW_EN_SUMA, MB_OK"));
		codigo.add(new String("Invoke ExitProcess, 0"));
		codigo.add(new String("@DIVISION_POR_CERO:"));
		codigo.add(new String("Invoke MessageBox, NULL, addr DIVISION_POR_CERO, addr DIVISION_POR_CERO, MB_OK"));
		codigo.add(new String("Invoke ExitProcess, 0"));
		codigo.add(new String("EXIT:"));
		codigo.add(new String("END START"));
		return codigo;
	}

}
