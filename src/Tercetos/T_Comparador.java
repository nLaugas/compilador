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

		if (tipo1 == "DOUBLE") //esto seria con conversion?? || tipo2 == "DOUBLE")
			tipo = "DOUBLE";
		else
			tipo = "ULONG";
		//donde chequeamos que no se conviertan los tipos? ose que no se permitan distintos
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