package Tercetos;
import java.util.Vector;
import SymbolTable.*;

public class T_Suma_Resta extends TercetoOperacion {
	
	public T_Suma_Resta(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);	
	}
	
	
	public Vector<String> getAssembler()
	{
		Vector<String> v = new Vector<String>();
		String op1 = getOperando1().toString();
		String op2 = getOperando2().toString();
		String tipo1, tipo2;
		if (tieneCorchete(1)){
			//	op1 = op1.substring(1, op1.length()-1);
			op1 = ((Terceto)operando1).getVarAux();
			tipo1 = ((Terceto)operando1).getTipo();
		}
		else {
			tipo1 = ((Symbol)(operando1)).getTipoVar();
			//tipo1 = tabla.getTipo(op1);
		/**	Fijarse si esto va
			Character a = op1.charAt(0);
			if (!Character.isDigit(a))
				op1 = "_" + op1; **/
		}
		if (tieneCorchete(2)){
			//op2 = op2.substring(1, op2.length()-1);
			op2 = ((Terceto)operando2).getVarAux();
			tipo2 = ((Terceto)operando2).getTipo();
		}
		else {
			//tipo2 = tabla.getTipo(op2);
			tipo2 = ((Symbol)(operando2)).getTipoVar();
			/**   no se si va
			Character a = op2.charAt(0);
			if (!Character.isDigit(a))
				op2 = "_" + op2;**/

		}
	 if (getTipo() == "ULONG"){
			v.add(new String("\r\nMOV EAX, " + op1));
			v.add(new String("ADD EAX, " + op2));
			v.add(new String("MOV " + getVarAux() + " ,EAX"));
			v.add(new String("JO OVERFLOW_EN_SUMA"));
			v.add("\n");
		}
		else {
			v.add(new String("\r\nFLD " + op1 ));
			v.add(new String("FLD " + op2));
			v.add(new String("FADD"));
			v.add(new String("FSTP " + getVarAux()));
		    v.add("\n");
		}
		return v;
	}	
}

