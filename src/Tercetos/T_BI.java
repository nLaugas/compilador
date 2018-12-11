package Tercetos;
import java.util.Vector;
import SymbolTable.*;


public class T_BI extends Terceto {
	
	public T_BI(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,"trampita",ts);
	}
	
	@Override
	public Vector<String> getAssembler(){
		Vector<String> codigoAss = new Vector<String>();
		codigoAss.add(new String("JMP Label" +(int) operando1));
		return codigoAss;
	}

	@Override
	public String toString() {
		String op1,op2;
		if (operando1!="trampita")
			op1=(String.valueOf(operando1));
		else
			op1="-";
		return "["+num+"] ("+operador+", ["+op1+"], [-]).";
	}

	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	};
}
