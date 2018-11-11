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
		return v;
	}	
}

