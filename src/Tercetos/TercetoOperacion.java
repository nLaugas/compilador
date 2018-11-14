package Tercetos;

import java.util.Vector;
import SymbolTable.*;


public abstract class TercetoOperacion extends Terceto{

	protected String tipo;
	
	public TercetoOperacion(int nro, String operador, Object operando1, Object operando2, SymbolTable ts) {
		super(nro, operador, operando1, operando2, ts);
		String tipo1;
		String tipo2;
		if (esTerceto(1))
			tipo1 =((Terceto)operando1).getTipo();
		else
			tipo1 =  ts.getSymbol((String)operando1).getTipoVar();//revisar la parte de (String)operando1 (tiene que ser el lexema)
		if (esTerceto(2))
			tipo2 =((Terceto)operando2).getTipo();
		else
			tipo2 =  ts.getSymbol((String)operando2).getTipoVar();


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
	public  String getTipo() {
		return tipo;
	}

	/*  a la variable con el resultado de esta operacion hay que ponerle el tipo que tenemos aca, y tambien a la TSymbolos

        public void setVariableAux(int a){
            varAux = "@AUX"+Integer.toString(a);
            Simbolo s = new Simbolo(300, false, varAux, tipo);
            ts.agregarSimbolo(varAux,s);
        }*/
	@Override
	public abstract Vector<String> getAssembler();

	public SymbolTable getTabla() {
		return ts;
	}

	public void setTabla(SymbolTable ts) {
		this.ts = ts;
	}
	
}
