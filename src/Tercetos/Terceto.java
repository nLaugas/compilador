package Tercetos;
import java.util.*;

import SymbolTable.*;

public abstract class Terceto {
	public static boolean OperacionConstante = false;
	public Object operando1;
	public Object operando2;
	public String operador;
	public static Hashtable reemplazoTercetoOptimizar = new Hashtable<Integer,Object>();

	//public static synchronizedMap<Integer,Object> reemplazoTercetoOptimizar= new synchronizedMap<Integer,Object>();
	public int num; //numero de terceto, arrancando en 0 o 1?
	public String varAux;//   tendria que ser el resultado de la operacion? eso que dijo marcela de los cuartetos ficticios
	// habria que agregarla a la tabla de simbolos?
	public SymbolTable ts;

	// ver para qe se usa
	protected String label="";


	public Vector<String> errores; // ACA LE DOY 3 VUELTAS DE ALAMBRE A LOS ERRORES SEMANTICOS

	public Terceto(int num,String operador,Object operando1,Object operando2,SymbolTable ts){
		this.operando1=operando1;//operando 1 y 2 son los lexemas de los simbolos
		this.operando2=operando2;
		this.operador=operador;
		this.num=num;
		this.varAux="";
		this.ts = ts;
		this.errores = new Vector<String>();

/*		Enumeration e = this.reemplazoTercetoOptimizar.keys();
		Object clave;
		Object valor;
		while( e.hasMoreElements() ){
			clave = e.nextElement();
			valor = this.reemplazoTercetoOptimizar.get( clave );
			System.out.println( "Clave : " + clave + " - Valor : " + valor );
		}*/
	}

	//public void getAssembler();
	public void imprimirTerceto(){
		System.out.println(num+" ( "+operador + ","+operando1+ ","+operando2+" )");
	};

	public int getnum() {
		return num;
	}
	public void setnum(int num) {
		this.num = num;
	}
	public Object getOperando2() {
		return operando2;
	}
	public void setOperando2(Object operando2) {
		this.operando2 = operando2;
	}
	public Object getOperando1() {
		return operando1;
	}
	public void setOperando1(Object operando1) {
		this.operando1 = operando1;
	}
	public Object getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}
	public String toString() {
		String op1,op2;
		if (operando1!="trampita")
			if (esTerceto(1))
				op1 ="["+String.valueOf(((Terceto)operando1).num)+"]";
			else
				op1=((Symbol)operando1).getLexema();
		else
			op1="- ";
		if (operando2!="trampita"){
			if (esTerceto(2))
				op2 = "["+String.valueOf(((Terceto)operando2).num)+"]";
			else
				op2 = ((Symbol) operando2).getLexema();
		}
		else op2 = "- ";
		return "["+num+"] ("+operador+", "+op1+", "+op2+")."; }

	/** Ver aca **/
	public void setLabel(){
		label = "Label"+num+":";
	}
	public String getLabel()
	{
		return label;
	}
	public boolean equals(Terceto t){
		if (t.getOperador().equals(operador))
			return true;
		else return false;
	}


	public boolean esTerceto(int a)// quiero saber si el operando es un terceto
	{//si es un terceto lo guarde con un corchete al principio y al final, despues se los saco en c/u
		if (operando1.toString().isEmpty() || operando2.toString().isEmpty()){
			System.out.println("anduvo mal, llego un operando que no sabe hacerse string");
			return false;
		}
		if (a == 1)
			if (operando1.toString().charAt(0) == '[')
				return true;
		if (a == 2)
			if (operando2.toString().charAt(0) == '[')
		return true;
		return false;
	}

	public String getVarAux(){
		return "_"+varAux;
	}

	public void setVariableAux(int a){
		varAux = "@AUX"+Integer.toString(a);
		Symbol s = new Symbol(varAux, 500);
		s.setTipoVar(this.getTipo());
		this.ts.setSymbol(s);
	}
	/*
	public void OperandosDeclarados(){
		ParserVal token = vectorTokens.elementAt(i);
		Symbol simbolo = token.obj;
		if (simbolo.getTipoVar=="sin asignar")//controlar de agregar este por defecto a symbol
			simbolo.setTipoVar(tipo);
		else
			yyerror("Se esta intentado redeclarar la variable "+simboblo.getLexema(),token.getFila(),token.getColumna());
		;
		}
*/

	public abstract String getTipo();
	public abstract Vector<String> getAssembler();

}
