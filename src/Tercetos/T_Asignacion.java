package Tercetos;
import java.util.Vector;

import AnalizadorSintactico.Parser;
import SymbolTable.*;

public class T_Asignacion extends Terceto{
	private String tipo;

	public T_Asignacion(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);
		String tipo1,tipo2;
		if (esTerceto(1)) {
			tipo1 = ((Terceto) operando1).getTipo();
			System.out.println("es terceto operando 1");
		}
		else
			tipo1 =  ((Symbol)operando1).getTipoVar();//revisar la parte de (String)operando1 (tiene que ser el lexema)
		if (esTerceto(2)){
			tipo2 =((Terceto)operando2).getTipo();
			System.out.println("es terceto operando 2");}
		else
			tipo2 =  ((Symbol)operando2).getTipoVar();

		System.out.println("tipo1 : "+tipo1+"tipo2 : "+tipo2);

		if (tipo1 == "single" || tipo2 == "single")
			tipo = "single";
		else
			tipo = "integer";

		if (tipo1 == "sin asignar")
			errores.add("Variable no declarada, "+operando1.toString());
		if (tipo2 == "sin asignar")
			errores.add("Variable no declarada, "+operando2.toString());
		if (tipo2 != tipo1)
			errores.add("Hay una incompatibilidad de tipo, entre "+operando1.toString()+" de tipo "+
					tipo1+", y "+operando2.toString()+" de tipo "+tipo2);


	}
	
	@Override
	public Vector<String> getAssembler(){
		Vector<String> v = new Vector<String>();
		String op1 = ((Symbol)(operando1)).getLexema();
		String op2 = getOperando2().toString();

		if (esTerceto(2)){
			op2 = ((Terceto)operando2).getVarAux(); }
		else {
			op2 = ((Symbol)(operando2)).getLexema();


			Character a = op2.charAt(0);
			//a = op2.charAt(0);
			if (Character.isDigit(a))
				op2 = "_" + op2;
		}
		if(operador=="&"){
			if (((Symbol) (operando1)).getTipoVar() == "integer") {
			// el puntero de tipo int
				v.add(new String("MOV AX, OFFSET "+op2 ));
				v.add(new String("MOV "+op1+", AX"));
			} else {
			// el puntero de tipo float
				v.add(new String("MOV EAX, OFFSET "+op2 ));
				v.add(new String("MOV "+op1+", EAX"));
			}
		}else if(((Symbol) (operando1)).isEsPuntero()){
			// en op2 tengo el elemento que necesito de la tabla de simbolos


			if (esTerceto(2)){
				System.out.println("es terceto operando 2");
				v.add(new String("MOV EAX, OFFSET "+op1 ));
				v.add(new String("MOV DWORD PTR [EAX], "+((Terceto)operando2).getVarAux()));

			}
			else{
				//CASO EN PUNT := ID
				v.add(new String("MOV AX, OFFSET "+((Symbol)operando2).getLexema() ));
				v.add(new String("MOV "+op1+", AX"));
				// falta float

			}
		}else {
				if (((Symbol) (operando1)).getTipoVar() == "integer") {
					v.add(new String("\r\nMOV AX, " + op2));
					v.add(new String("MOV " + op1 + " ,AX")); //aca va varAux o op1?
				} else {
					v.add(new String("\r\nFLD " + op2.replace(".", "p").replace("-", "n")));
					v.add(new String("FSTP " + op1));
				}
			}
		return v;
}
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return tipo;
	}

	public SymbolTable getTabla() {
		return ts;
	}

	public void setTabla(SymbolTable tabla) {
		this.ts = tabla;
	}
/*  a la variable con el resultado de esta operacion hay que ponerle el tipo que tenemos aca, y tambien a la TSymbolos
        public void setVariableAux(int a){
            varAux = "@AUX"+Integer.toString(a);
            Simbolo s = new Simbolo(300, false, varAux, tipo);
            ts.agregarSimbolo(varAux,s);
        }*/
}
