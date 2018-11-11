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
		return v;
	}

}
