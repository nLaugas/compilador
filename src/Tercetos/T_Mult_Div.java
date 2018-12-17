package Tercetos;
import java.util.Vector;
import SymbolTable.*;

public class T_Mult_Div extends TercetoOperacion {
	

	public T_Mult_Div(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);
	}
	
	@Override
	public Vector<String> getAssembler()
	{
		Vector<String> v = new Vector<String>();
		String tipo1;
		String tipo2;
		String op1;
		String op2;
		if (esTerceto(1)){
			op1 = ((Terceto)operando1).getVarAux();
			tipo1 = ((Terceto)operando1).getTipo();
		}
		else{
			op1 = ((Symbol)(operando1)).getLexema().replace(".","p").replace("-","n");
			tipo1 = ((Symbol)(operando1)).getTipoVar();
			Character a = op1.charAt(0);
			if (Character.isDigit(a))
				op1 = "_" + op1;
		}

		if (esTerceto(2)){
			op2 = ((Terceto)operando2).getVarAux();
			tipo2 = ((Terceto)operando2).getTipo();
		}
		else{
			op2 = ((Symbol)(operando2)).getLexema().replace(".","p").replace("-","n");
			tipo2 = ((Symbol)(operando2)).getTipoVar();

			Character a = op2.charAt(0);
			if (Character.isDigit(a))
				op2 = "_" + op2;
		}

		if (getTipo() == "integer"){
			if (operador=="*") {
				v.add(new String("\r\nMOV EAX, " + op1));
				v.add(new String("MUL " + op2));
				v.add(new String("MOV " + getVarAux() + "AX"));
			}else{
				//para la division
				v.add(new String("\r\nMOV AX, " + op1));
				v.add(new String("DIV" + op2));
				v.add(new String("MOV " + getVarAux() + "AX"));
				//listoo
			}
		}
		else {
			if (operador=="*") {
				v.add(new String("\r\nFILD "+op1));
				v.addElement(new String("FMUL "+op2));
				v.addElement(new String("FSTP "+ getVarAux()));
				//listo
			}else{
				//para la division
				v.add(new String("\r\nFILD "+op1));
				v.addElement(new String("FDIV "+op2));
				v.addElement(new String("FSTP "+ getVarAux()));
				//listo
			}
		}
		return v;
	}

}
