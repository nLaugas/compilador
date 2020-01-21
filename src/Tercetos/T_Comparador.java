package Tercetos;
import java.util.Vector;
import SymbolTable.*;


public class T_Comparador extends Terceto{
	private String tipo;
	private boolean invertir=false;

	public T_Comparador(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);	
		String tipo1;
		String tipo2;
		if (esTerceto(1))
			tipo1 =((Terceto)operando1).getTipo();
		else
			tipo1 =  ((Symbol)operando1).getTipoVar();//revisar la parte de (String)operando1 (tiene que ser el lexema)
		if (esTerceto(2))
			tipo2 =((Terceto)operando2).getTipo();
		else
			tipo2 =  ((Symbol)operando2).getTipoVar();


		if (tipo1 == "integer" || tipo2 == "integer")
			tipo = "integer";
		else
			tipo = "single";

		if (tipo1 == "sin asignar")
			errores.add("Variable no declarada, "+operando1.toString());
		if (tipo2 == "sin asignar")
			errores.add("Variable no declarada, "+operando2.toString());
		if (tipo2 != tipo1)
			errores.add("Hay una incompatibilidad de tipo, entre "+operando1.toString()+" de tipo "+
					tipo1+", y "+operando2.toString()+" de tipo "+tipo2);

	}

	@Override
	public String getTipo()
	{
		return tipo;
	}

	@Override
	public Vector<String> getAssembler()
	{
		Vector<String> v = new Vector<String>();
		String op1 = ((Symbol)(operando1)).getLexema();
		String op2 = getOperando2().toString();
		//String tipoVar;

		Character a = op1.charAt(0);
		if (Character.isDigit(a))
			op1 = "_" + op1;

		if (esTerceto(1)){
			op1 = ((Terceto)operando1).getVarAux();
			//tipoVar=((Terceto)operando1).getTipo();
			}
		else {
			op1 = ((Symbol)(operando1)).getLexema();
			a = op1.charAt(0);
			if (Character.isDigit(a))
				op1 = "_" + op1;
		//	tipoVar=((Symbol)(operando1)).getTipoVar();
		}
		if (esTerceto(2)){
			op2 = ((Terceto)operando2).getVarAux(); }
		else {
			op2 = ((Symbol)(operando2)).getLexema();
			a = op2.charAt(0);
			if (Character.isDigit(a))
				op2 = "_" + op2;
		}

		//System.out.println("tipo varrr:: "+tipoVar);

		if (getTipo() == "integer"){
			v.add(new String("\r\nMOV AX, " + op1));
			v.add(new String("CMP AX, " + op2));
		}
		else {
			v.add(new String("\r\nFLD " +op1.replace(".","p").replace("-","n")));
			v.add(new String("\r\nFCOMP " +op2.replace(".","p").replace("-","n")));
			v.add(new String("\r\nFSTSW _memaux"));
			v.add(new String("\r\nMOV AX, _memaux"));
			v.add(new String("\r\nSAHF"));
//			v.add(new String("\r\nFLD " +op1.replace(".","p").replace("-","n")));
//			v.add(new String("\r\nFLD " +op2.replace(".","p").replace("-","n")));
//			v.add(new String("\r\nFCOMP"));
//  lo anterior
//			v.add(new String("\r\nMOV EAX, "+op1.replace(".","p").replace("-","n")));
//			v.add(new String("CMP " + op2.replace(".","p").replace("-","n")) +", EAX");
		}
		return v;
	}

	public void setInvertir(){
		this.invertir=true;
	}

	public SymbolTable getTabla() {
		return ts;
	}

	public void setTabla(SymbolTable ts) {
		this.ts = ts;
	}

	public String getSaltoConSigno() {
		if (!invertir) {
			if (operador.equals(">")) {
				//Saltas por Menor Igual
				return "JLE ";
			}
			if (operador.equals("<")) {
				//Saltas por Mayor Igual
				return "JGE ";
			}
			if (operador.equals(">=")) {
				//Saltas por Menor
				return "JL ";
			}
			if (operador.equals("<=")) {
				//Saltas por Mayor
				return "JG ";
			}
			if (operador.equals("=")) {
				//Saltas por distinto
				return "JNE ";
			}
			if (operador.equals("!=")) {
				//Saltas por igual
				return "JE ";
			}
		}else
		{
			if (operador.equals(">")) {
				//Saltas por Menor Igual
				return "JGE ";
			}
			if (operador.equals("<")) {
				//Saltas por Mayor Igual
				return "JLE ";
			}
			if (operador.equals(">=")) {
				//Saltas por Menor
				return "JG ";
			}
			if (operador.equals("<=")) {
				//Saltas por Mayor
				return "JL ";
			}
			if (operador.equals("=")) {
				//Saltas por distinto
				return "JE ";
			}
			if (operador.equals("!=")) {
				//Saltas por igual
				return "JNE ";
			}
		}
		return null;
	}

	public String getSaltoSinSigno() {
		if (!invertir) {
			if (operador.equals(">")) {
				//Saltas por Menor Igual
				return "JBE ";
			}
			if (operador.equals("<")) {
				//Saltas por Mayor Igual
				return "JAE ";
			}
			if (operador.equals(">=")) {
				//Saltas por Menor
				return "JB ";
			}
			if (operador.equals("<=")) {
				//Saltas por Mayor
				return "JA ";
			}
			if (operador.equals("=")) {
				//Saltas por distinto
				return "JNE ";
			}
			if (operador.equals("!=")) {
				//Saltas por igual
				return "JE ";
			}
		}else{
			if (operador.equals(">")) {
				//Saltas por Menor Igual
				return "JAE ";
			}
			if (operador.equals("<")) {
				//Saltas por Mayor Igual
				return "JBE ";
			}
			if (operador.equals(">=")) {
				//Saltas por Menor
				return "JA ";
			}
			if (operador.equals("<=")) {
				//Saltas por Mayor
				return "JB ";
			}
			if (operador.equals("=")) {
				//Saltas por distinto
				return "JE ";
			}
			if (operador.equals("!=")) {
				//Saltas por igual
				return "JNE ";
			}
		}
		return null;
	}
}