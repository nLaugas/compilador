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
			tipo1 =  ((Symbol)operando1).getTipoVar();//revisar la parte de (String)operando1 (tiene que ser el lexema)
		if (esTerceto(2))
			tipo2 =((Terceto)operando2).getTipo();
		else
			tipo2 =  ((Symbol)operando2).getTipoVar();


		if (tipo1 == "integer" || tipo2 == "integer")
			tipo = "integer";
		else
			tipo = "single";

		if (tipo1 == "sin asignar")
			errores.add("Variable no declarada, "+operando1.toString());
		if (tipo2 == "sin asignar")
			errores.add("Variable no declarada, "+operando2.toString());
		if (tipo2 != tipo1) {
			errores.add("Hay una incompatibilidad de tipo, entre " + operando1.toString() + " de tipo " +
					tipo1 + ", y " + operando2.toString() + " de tipo " + tipo2);

		}


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
