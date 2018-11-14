package Tercetos;
import java.util.Vector;
import SymbolTable.*;


public class T_Comparador extends Terceto{
	private String tipo;

	public T_Comparador(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);	
		String tipo1;
		String tipo2;
		if (esTerceto(1))
			tipo1 =((Terceto)operando1).getTipo();
		else
			tipo1 = (String) ts.getSymbol((String)operando1).getTipoVar();
		if (esTerceto(2))
			tipo2 =((Terceto)operando1).getTipo();
		else
			tipo2 = (String) ts.getSymbol((String)operando2).getTipoVar();

		if (tipo1 == "single" || tipo2 == "single")
			tipo = "single";
		else
			tipo = "integer";

		if (tipo1 == "sin asignar")
			errores.add("Variable no declarada, "+(String)operando1);
		if (tipo2 == "sin asignar")
			errores.add("Variable no declarada, "+(String)operando2);
		if (tipo2 != tipo1)
			errores.add("Hay una incompatibilidad de tipo, entre "+(String)operando1+" de tipo "+
					tipo1+", y "+(String)operando2+" de tipo "+tipo2);
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

		return v;
	}


	public SymbolTable getTabla() {
		return ts;
	}

	public void setTabla(SymbolTable ts) {
		this.ts = ts;
	}
	

}