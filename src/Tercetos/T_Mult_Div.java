package Tercetos;
import java.util.Vector;
import SymbolTable.*;
import org.omg.CORBA.INTERNAL;

public class T_Mult_Div extends TercetoOperacion {

	private boolean primeroConstante = false;
	private boolean seguntoConstante = false;
	

	public T_Mult_Div(int nro, String operador, Object operando1, Object operando2, SymbolTable ts){
		super(nro,operador,operando1,operando2,ts);
		if ( !this.esTerceto(1) && ( (((Symbol)(operando1)).getTipo() == 275 ) || (((Symbol)(operando1)).getTipo() == 276 ) )){
			System.out.println( "EL LEXEMA ES : "+ ((Symbol)(operando1)).getLexema() );
			primeroConstante = true;
		}

		if ( !this.esTerceto(2) &&((((Symbol)(operando2)).getTipo() == 275 ) || (((Symbol)(operando2)).getTipo() == 276 ) )){
			seguntoConstante = true;
		}

		/** Caso que OperacionConstante sea true **/
		if (primeroConstante && seguntoConstante){
			/// aca estoy en un terceto que solo realiza operaciones entre constantes!

			// Debo calcular el valor a guardar.

			if (((Symbol)(operando1)).getTipoVar().equals("integer")){
				// si son enteros
				//valor en entero del primero
				Integer primero = Integer.valueOf(((Symbol)(operando1)).getLexema().substring(0,((Symbol)(operando1)).getLexema().length()-2));
				System.out.println( "el valor es en entero 1 : "+ primero );
				//valor em entero del segundo
				Integer segundo = Integer.valueOf(((Symbol)(operando2)).getLexema().substring(0,((Symbol)(operando2)).getLexema().length()-2));
				System.out.println( "el valor es en entero 2 : "+ segundo );

				Integer resul;
				if (this.operador.equals("*")){
					resul = primero * segundo;

				}
				else {
					resul = primero / segundo;
				}
				// cargo en la estructura num de terceto y valor
				System.out.println("el num del terceto es : "+ this.num);
				reemplazoTercetoOptimizar.put(this.num,resul.toString()+"_i");
				System.out.println("el dato es : "+this.num+" "+reemplazoTercetoOptimizar.get(this.num));
				// agrego elemento en la tabla de symbolos
				ts.setSymbol(resul.toString()+"_i",resul);
				ts.getSymbol(resul.toString()+"_i").setTipoVar("integer");
				ts.getSymbol(resul.toString()+"_i").setTipo(275);

				System.out.println("hola");
			}else{
				System.out.println(((Symbol)(operando1)).getLexema());
				// caso float tengo 2 constantes float
				//Float primero = Float.valueOf(((Symbol)(operando1)).getLexema().replace(".","p").replace("-","n"));
				Float primero = Float.valueOf(((Symbol)(operando1)).getLexema());
				System.out.println( "el valor es en float 1 : "+ primero );
				//valor em entero del segundo
				Float segundo = Float.valueOf(((Symbol)(operando2)).getLexema());
				//Float segundo = Float.valueOf(((Symbol)(operando2)).getLexema().replace(".","p").replace("-","n"));
				System.out.println( "el valor es en float 2 : "+ segundo );
				Float resul;
				if (this.operador.equals("*")){
					resul = primero * segundo;
				}
				else {
					resul = primero / segundo;
				}
				String resultString = resul.toString().replace(".","p").replace("-","n");

				//agrego en estructura para despues recorrer
				reemplazoTercetoOptimizar.put(this.num,resultString);
				System.out.println("se agreggo");

				ts.setSymbol(resultString,276);
				ts.getSymbol(resultString).setTipoVar("single");

				System.out.println("result : "+ resul);
				System.out.println("result string : "+ resul.toString());

			}
		}
	}
	
	@Override
	public Vector<String> getAssembler()
	{
		Vector<String> v = new Vector<String>();
		String tipo1;
		String tipo2;
		String op1;
		String op2;
		if (esTerceto(1)){
			op1 = ((Terceto)operando1).getVarAux();
			tipo1 = ((Terceto)operando1).getTipo();
		}
		else{
			op1 = ((Symbol)(operando1)).getLexema().replace(".","p").replace("-","n");
			tipo1 = ((Symbol)(operando1)).getTipoVar();



			//System.out.println("EL LEXEMA ES : "+((Symbol)(operando1)).getLexema());
			System.out.println("T Mul Div, el tipo es : "+((Symbol)(operando1)).getTipo() + " "+((Symbol)(operando1)).getTipoVar());

			Character a = op1.charAt(0);
			if (Character.isDigit(a))
				op1 = "_" + op1;
		}

		if (esTerceto(2)){
			op2 = ((Terceto)operando2).getVarAux();
			tipo2 = ((Terceto)operando2).getTipo();
		}
		else{
			op2 = ((Symbol)(operando2)).getLexema().replace(".","p").replace("-","n");
			tipo2 = ((Symbol)(operando2)).getTipoVar();

			if ( (((Symbol)(operando2)).getTipo() == 275 ) || (((Symbol)(operando2)).getTipo() == 276 ) ){
				seguntoConstante = true;
			}


			Character a = op2.charAt(0);
			if (Character.isDigit(a))
				op2 = "_" + op2;
		}

		if (getTipo() == "integer"){
			if (operador.equals("*")) {
				v.add(new String("\r\nMOV AX, " + op1));
				v.add(new String("MUL " + op2));
				v.add(new String("MOV " + getVarAux() + ", AX"));
				v.add("\n");
			}else{
				//para la division
				v.add(new String("\r\nMOV AX, " + op1));
				v.add(new String("CMP " + op2 +", 0"));
				v.add(new String("JE @DIVISION_POR_CERO"));
				v.add(new String("DIV " + op2));
				v.add(new String("MOV " + getVarAux() + ", AX"));
				//v.add(new String("JZ @DIVISION_POR_CERO"));
				v.add("\n");
				//listoo
			}
		}
		else {
			if (operador=="*") {
				v.add(new String("\r\nFILD "+op1));
				v.addElement(new String("FMUL "+op2));
				v.addElement(new String("FSTP "+ getVarAux()));
				v.add("\n");
				//listo
			}else{
				//para la division
				v.add(new String("\r\nFILD "+op1));
				v.add(new String("CMP " + op2 +", 0"));
				v.add(new String("JE @DIVISION_POR_CERO"));
				v.addElement(new String("FDIV "+op2));
				v.addElement(new String("FSTP "+ getVarAux()));
				//v.add(new String("JZ @DIVISION_POR_CERO")); nose porque no anda el flag jz
				v.add("\n");
				//listo
			}
		}
		System.out.println("Operacion constante es : " + OperacionConstante);
		System.out.println("T_MUL_DIV");
		System.out.println("Tipo 1 : " + op1 );

		System.out.println("Tipo 2 : " + op2 );
		System.out.println("####################");
		return v;
	}

}
