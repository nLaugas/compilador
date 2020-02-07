package Tercetos;
import java.util.Vector;

import AnalizadorSintactico.Parser;
import SymbolTable.*;

public class T_Asignacion extends Terceto{
	private String tipo;
	String punt = null;

	public T_Asignacion(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);
		String tipo1,tipo2;
		String opAusar1;
		String opAusar2;


		/** Reconoce puntero **/

		if (operador.equals("&")){

			opAusar1 = ((Symbol)(operando1)).getLexema();
			Character a = opAusar1.charAt(0);
			//a = op2.charAt(0);
			if (Character.isDigit(a))
				opAusar1 = "_" + opAusar1;

			opAusar2 = ((Symbol)(operando2)).getLexema();
			Character a2 = opAusar2.charAt(0);
			//a = op2.charAt(0);
			if (Character.isDigit(a2))
				opAusar2 = "_" + opAusar2;


		ts.setAtributo(opAusar1,"IdUsar",opAusar2);
			System.out.println(" El ID es : "+((Symbol)operando1).getAtributo("IdUsar"));
		}else {
			if (((Symbol)operando1).isEsPuntero()){
				punt = new String(((Symbol)operando1).getAtributo("IdUsar").toString());
			}

		}

		if (esTerceto(1)) {
			tipo1 = ((Terceto) operando1).getTipo();

		}
		else
			tipo1 =  ((Symbol)operando1).getTipoVar();//revisar la parte de (String)operando1 (tiene que ser el lexema)
		if (esTerceto(2)){
			tipo2 =((Terceto)operando2).getTipo();
			}
		else
			tipo2 =  ((Symbol)operando2).getTipoVar();


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
		if(operador.equals("&")){
			v.add(new String(""));
/*			if (((Symbol) (operando1)).getTipoVar() == "integer") {
			// el puntero de tipo int
				v.add(new String("MOV EAX, OFFSET "+op2 ));
				v.add(new String("MOV "+op1+", AX"));
			} else {
			// el puntero de tipo float
				v.add(new String("MOV EAX, OFFSET "+op2 ));
				v.add(new String("MOV "+op1+", EAX"));
			}*/
		}

		else {
			if (((Symbol) (operando1)).isEsPuntero()) {
	/*
				// en op2 tengo el elemento que necesito de la tabla de simbolos

				if (esTerceto(2)) {
					if (((Terceto) operando2).getTipo() == "integer") {

						v.add(new String("MOV EAX, OFFSET " + op1));
						v.add(new String("MOV BX, " + ((Terceto) operando2).getVarAux()));
						v.add(new String("MOV DWORD PTR [EAX], EBX"));
					} else {
						v.add(new String("MOV EAX, OFFSET " + op1.replace(".", "p").replace("-", "n")));
						v.add(new String("MOV EBX, " + ((Terceto) operando2).getVarAux()));
						v.add(new String("MOV DWORD PTR [EAX], EBX"));
						// caso float
					}
				} else {
					if (((Symbol) (operando2)).getTipo() == 275 *//*|| ((Symbol) (operando2)).getTipo()==276*//*) {
						// CASO QUE SEA CONSTANTE
						if (((Symbol) (operando2)).getTipo() == 275) {
							v.add(new String("MOV EAX, OFFSET " + op1));
							String addlist = "MOV BX, " + op2;
							v.add(addlist);
							v.add(new String("MOV DWORD PTR [EAX], EBX"));
						} else {
							// CASO CONSTANTE FLOAT
							v.add(new String("MOV EAX, OFFSET " + op1.replace(".", "p").replace("-", "n")));
							String addlist = "MOV BX, " + op2;
							v.add(addlist);
							v.add(new String("MOV DWORD PTR [EAX], EBX"));

						}
					} else {
						//CASO EN PUNT := ID
						if (((Symbol) (operando1)).getTipoVar() == "integer") {

							v.add(new String("MOV EAX, OFFSET " + ((Symbol) operando2).getLexema()));
							v.add(new String("MOV " + op1 + ", AX"));
						} else {
							String operan = (((Symbol) operando2).getLexema()).replace(".", "p").replace("-", "n");
							if (operan.charAt(0) != '_') {
								operan = "_" + operan;
							}

							v.add(new String("MOV EAX, OFFSET " + operan));
							v.add(new String("MOV " + op1 + ", EAX"));
						}
					}
				}    // float ya contemplado*/

				op1 = punt;
			}



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
