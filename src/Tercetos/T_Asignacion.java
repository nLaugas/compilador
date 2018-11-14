package Tercetos;
import java.util.Vector;
import SymbolTable.*;

public class T_Asignacion extends Terceto{
	private String tipo;

	public T_Asignacion(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);
		String tipo1,tipo2;
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
