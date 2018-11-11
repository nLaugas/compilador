package Tercetos;
import java.util.Vector;
import SymbolTable.*;

public class T_Asignacion extends Terceto{
	private String tipo;

	public T_Asignacion(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);	
		String tipo1;
		if (esTerceto(1))
			tipo1 =((Terceto)operando1).getTipo();
		else
			tipo1 = ts.getSymbol((String)operando1).getTipoVar();
		if (tipo1 == "DOUBLE") //esto seria con conversion?? || tipo2 == "DOUBLE")
			tipo = "DOUBLE";
		else
			tipo = "ULONG";
		//donde chequeamos que no se conviertan los tipos? ose que no se permitan distintos
	}
	
	@Override
	public Vector<String> getAssembler(){
		Vector<String> v = new Vector<String>();
		return v;
}
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	}

	public SymbolTable getTabla() {
		return ts;
	}

	public void setTabla(SymbolTable tabla) {
		this.ts = tabla;
	}
/*  a la variable con el resultado de esta operacion hay que ponerle el tipo que tenemos aca, y tambien a la TSymbolos
        public void setVariableAux(int a){
            varAux = "@AUX"+Integer.toString(a);
            Simbolo s = new Simbolo(300, false, varAux, tipo);
            ts.agregarSimbolo(varAux,s);
        }*/
}
