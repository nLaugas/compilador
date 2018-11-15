package Tercetos;
import java.util.Vector;
import SymbolTable.*;


public class T_BI extends Terceto {
	
	public T_BI(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,"trampita",ts);
	}
	
	@Override
	public Vector<String> getAssembler(){
		Vector<String> codigoAss = new Vector<String>();
		return codigoAss;
	
	}
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	};
}
