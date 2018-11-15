package Tercetos;
import java.util.Vector;
import SymbolTable.*;

public class T_BF extends Terceto {
		
	public T_BF(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);
		}
	
	@Override
	public Vector<String> getAssembler(){
		
		Vector<String> codigoAss = new Vector<String>();
		return codigoAss;
	
	}

	public String toString() {
		String op1,op2;
		if (operando1!="trampita")
			op1 =operando1.toString().substring(0,3);
		else
			op1="-";
		if (operando2!="trampita")
			op2=(String.valueOf(operando2));
		else
			op2="-";
		return "["+num+"] ("+operador+", "+op1+"], ["+op2+"]).";
	}

	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	}




}
