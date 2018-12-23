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
		if (esTerceto(1)){
			//	op1 = op1.substring(1, op1.length()-1);
			op1 = ((Terceto)operando1).getVarAux();
			tipo1 = ((Terceto)operando1).getTipo();
		}
		else {
            op1 = ((Symbol)(operando1)).getLexema().replace(".","p").replace("-","n");
            tipo1 = ((Symbol)(operando1)).getTipoVar();
			Character a = op1.charAt(0);
			if (Character.isDigit(a))
				op1 = "_" + op1;

		}
		if (esTerceto(2)){
			//op2 = op2.substring(1, op2.length()-1);
			op2 = ((Terceto)operando2).getVarAux();
			tipo2 = ((Terceto)operando2).getTipo();
		}
		else {
			//tipo2 = tabla.getTipo(op2);
            op2 = ((Symbol)(operando2)).getLexema().replace(".","p").replace("-","n");
            tipo2 = ((Symbol)(operando2)).getTipoVar();

            Character a = op2.charAt(0);
			if (Character.isDigit(a))
				op2 = "_" + op2;
		}
	 if (getTipo() == "integer"){
	 	if (operador=="+") {
			v.add(new String("\r\nMOV AX, " + op1));
			v.add(new String("ADD AX, " + op2));
			v.add(new String("JO @OVERFLOW_EN_SUMA"));
			v.add(new String("MOV " + getVarAux() + " ,AX"));
			v.add("\n");
		}else{
	 		//para la resta
			v.add(new String("\r\nMOV AX, " + op1));
			v.add(new String("SUB AX, " + op2));
			v.add(new String("JS @RESULTADO_NEGATIVO_RESTA "));
			v.add(new String("MOV " + getVarAux() + " ,AX"));
			v.add("\n");
		 }
		}
		else {
		 if (operador=="+") {
			 v.add(new String("\r\nFLD " + op1));
			 v.add(new String("FLD " + op2));
			 v.add(new String("FADD"));
			 v.add(new String("JO @OVERFLOW_EN_SUMA")); //verificar si es el mismo flag
			 v.add(new String("FSTP " + getVarAux()));
			 v.add("\n");
		 }else{
		 	//para la resta
			 v.add(new String("\r\nFLD " + op1));
			 v.add(new String("FLD " + op2));
			 v.add(new String("FSUB"));
			 v.add(new String("JS @RESULTADO_NEGATIVO_RESTA "));
			 v.add(new String("FSTP " + getVarAux()));
             v.add("\n");
		 }
		}
		return v;
	}	
}

