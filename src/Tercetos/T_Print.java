package Tercetos;
import java.util.Vector;
import SymbolTable.*;


public class T_Print extends Terceto {
	

	
	public T_Print(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);
		}
	
	
	
	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Vector<String> getAssembler() {
    	Vector<String> codigoAss = new Vector<String>();
		return codigoAss;	
	}
	
	public SymbolTable getTabla() {
		return ts;
	}
	public void setTabla(SymbolTable ts) {
		this.ts = ts;
	}
}
