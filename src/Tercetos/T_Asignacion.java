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
			tipo1 =  ((Symbol)operando1).getTipoVar();//revisar la parte de (String)operando1 (tiene que ser el lexema)
		if (esTerceto(2))
			tipo2 =((Terceto)operando2).getTipo();
		else
			tipo2 =  ((Symbol)operando2).getTipoVar();


		if (tipo1 == "single" || tipo2 == "single")
			tipo = "single";
		else
			tipo = "integer";

		if (tipo1 == "sin asignar")
			errores.add("Variable no declarada, "+operando1.toString());
		if (tipo2 == "sin asignar")
			errores.add("Variable no declarada, "+operando2.toString());
		if (tipo2 != tipo1)
			errores.add("Hay una incompatibilidad de tipo, entre "+operando1.toString()+" de tipo "+
					tipo1+", y "+operando2.toString()+" de tipo "+tipo2);


	}
	
	@Override
	public Vector<String> getAssembler(){
		Vector<String> v = new Vector<String>();
		String op1 = getOperando1().toString();
		String op2 = getOperando2().toString();

		Character a = op1.charAt(0);
		if (!Character.isDigit(a))
			op1 = "_" + op1;

		if (esTerceto(2)){
			op2 = ((Terceto)operando2).getVarAux(); }
		else {
			a = op2.charAt(0);
			if (!Character.isDigit(a))
				op2 = "_" + op2;
		}
		if (getTipo() == "single"){
			v.add(new String("\r\nMOV EAX, " + op2));
			v.add(new String("MOV " + getVarAux()+ " ,EAX"));
		}
		else
		{
			v.add(new String("\r\nFLD " + op2));
			v.add(new String("FSTP " + getVarAux()));
		}
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
