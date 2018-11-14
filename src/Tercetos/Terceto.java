package Tercetos;
import java.util.Vector;
import SymbolTable.*;

public abstract class Terceto {
	public Object operando1;
	public Object operando2;
	public String operador;
	public int num; //numero de terceto, arrancando en 0 o 1?
//	public String varAux;   tendria que ser el resultado de la operacion? eso que dijo marcela de los cuartetos ficticios
	// habria que agregarla a la tabla de simbolos?
	public SymbolTable ts;
	public Vector<String> errores; // ACA LE DOY 3 VUELTAS DE ALAMBRE A LOS ERRORES SEMANTICOS

	public Terceto(int num,String operador,Object operando1,Object operando2,SymbolTable ts){
		this.operando1=operando1;//operando 1 y 2 son los lexemas de los simbolos
		this.operando2=operando2;
		this.operador=operador;
		this.num=num;
		//varAux="";
		this.ts = ts;
		this.errores = new Vector<String>();
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
	public String toString()
	{
		return "["+num+"]";
	}

	public boolean equals(Terceto t){
		if (t.getOperador().equals(operador))
			return true;
		else return false;
	}
	public boolean esTerceto(int a)// quiero saber si el operando es un terceto
	{//si es un terceto lo guarde con un corchete al principio y al final, despues se los saco en c/u
		if (a == 1)
			if (operando1.toString().charAt(0) == '[')
				return true;
		if (a == 2)
			if (operando2.toString().charAt(0) == '[')
		return true;
		return false;
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
