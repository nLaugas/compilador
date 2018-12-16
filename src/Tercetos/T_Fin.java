package Tercetos;
import java.util.Vector;
import SymbolTable.*;


public class T_Fin extends Terceto {

private String label="";
	
	
	public T_Fin(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);
		label = "\r\nLabel"+(nro)+":";
	}
	
	@Override
	public Vector<String> getAssembler(){
		Vector<String> v = new Vector<String>();
		//v.add(new String(label));
		v.add(new String(""));

		return v;}
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	};
/**
	public String toString(){
		return "["+this.getnum()+"]"+"Terceto_Fin";
	}
**/
	public String getLabel() {
		return label;
	}

	public void setLabel() {
	
	}

}
