/* Declaraciones */

%{ 
import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;
import SymbolTable.*;
import Tercetos.Terceto;

import java.util.ArrayList;
%}

/* Lista de tokens */

%token IF ELSE PRINT INTEGER ID CTE CADENA ASIG MAYIG MENIG IGUAL DIST FIN SINGLE END_IF LOOP UNTIL LET MUT ENTERO FLOTANTE

/* Reglas */

%{


  %}
%%
programa : lista_sentencia{} 
	 | FIN {yyerror("No hay sentencia");}   		
         | error {yyerror("No hay sentencia");} FIN{}
	 ;

lista_sentencia : sentencia{}
	 | sentencia lista_sentencia{} 
	 ;

sentencia: ejecutable {}
	| declaracion {}
        ;

declaracion: LET MUT tipo lista_id ',' {for(String lexem : id){    //estoy aca!
											Symbol s = st.getSymbol(lexem);
											
											if (!s.isUsada()){
												s.setUsada(true);
												s.setEsMutable(true);
												s.setTipoVar($3.sval);}
											else{//se puede poner bien que variable es si id es una lista de parserVal
												yyerror("Variable ya definida ",$1.getFila(),$1.getColumna());		
											}					
                                            }id.clear();
                                            Vector<ParserVal> vectorTokens = (Vector<ParserVal>)($4.obj);
                                            String tipo = ($3.sval);// para que esto ande tocar la regla del no terminal tipo
                                            tipo = tipo.toLowerCase();
                                            for(int i=0; i< vectorTokens.size();i++){
                                                ParserVal token = vectorTokens.elementAt(i);
                                                Symbol simbolo = token.obj;
                                                if (simbolo.getTipoVar=="sin asignar")//controlar de agregar este por defecto a symbol
                                                    simbolo.setTipoVar(tipo);
                                                else
                                                    yyerror("Se esta intentado redeclarar la variable "+simboblo.getLexema(),token.getFila(),token.getColumna());
                                                }

										}
        | error {yyerror("Declaracion mal definida ");}
        ;

lista_id: ID {  id.add( ((Symbol)($1.obj)).getLexema() );
                Vector<ParserVal> vect = new Vector<ParserVal>();//$1 es el parser val con el symbolo de ese ID
                vect.add($1);//ver si anda, hay que castear a Symbol?
                $$.obj = vect; }
	| '*' ID {	((Symbol)($2.obj)).setEspuntero(true); //reconoce puntero
				id.add(((Symbol)($2.obj)).getLexema());//} //agrega a lista de identificadores reconocidos
				Vector<ParserVal> vect = new Vector<ParserVal>();//$2 es el parser val con el symbolo de ese ID
                vect.add($2);//ver si anda, hay que castear a Symbol? .obj
                $$.obj = vect;
                                }
	
	| ID ';' lista_id {id.add(((Symbol)($1.obj)).getLexema());
                     Vector<ParserVal> vect = (Vector<ParserVal>)($3.obj); //$3 me trae el vector original primero y desp aumenta
                    vect.add($1);//ver si anda, hay que castear a Symbol? .obj
                    $$.obj = vect;
	}
	| '*' ID ';' lista_id {id.add(((Symbol)($2.obj)).getLexema());
                            ((Symbol)($2.obj)).setEspuntero(true); //reconoce puntero
                            Vector<ParserVal> vect = (Vector<ParserVal>)($4.obj); //$4 me trae el vector original primero y desp aumenta
                            vect.add($2);//ver si anda, hay que castear a Symbol? .obj
                            $$.obj = vect;
	}
        | ID lista_id{yyerror("Se esperaba ';' ",$1.getFila(),$1.getColumna());}
        ;

tipo: INTEGER {$$.sval="integer";}
	| SINGLE {$$.sval="float";}
	| error ','{yyerror("Tipo indefinido",$1.getFila(),$1.getColumna());}
	;

lista_ejecutable: ejecutable {}
	| ejecutable lista_ejecutable {}
	;

ejecutable: asignacion ','{}
          | bloque {}
          | exp_print ','{}
	  ;

expresion: termino '+' expresion {/*if(!(((Symbol)($1.obj)).getTipoVar().equals(((Symbol)($3.obj)).getTipoVar()))){
									yyerror("tipos incompatibles ",$1.getFila(),$1.getColumna());		
								  }
								  $$=$1;
								  T_Suma_Resta t = new T_Suma_Resta(contadorTerceto,"+",$1.obj,$3.obj,ts);
														contadorVarAux++;
														t.setVariableAux(contadorVarAux);
														t.setTabla(tabla);
														contadorTerceto ++;
														listaTercetos.add(t);
														$$.obj = t;*/
								Terceto t = new T_Suma_Resta(contadorTerceto,"+",$1.obj,$3.obj,st);
                                //st es la tabla de simbolos, paso lexema porque lo uso para buscar en la tabla de simbolos
                                //contadorVarAux++;         por ahora no lo necesitamos
                                //t.setVariableAux(contadorVarAux);
                                for(int i=0; i< t.errores.size();i++){
                                          yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
                                      ;}
                                contadorTerceto ++;
                                listaTercetos.add(t);
                     System.out.println(t.toString());
                $$.obj = t;
}
	| termino '-' expresion {/*
	                        if(!(((Symbol)($1.obj)).getTipoVar().equals(((Symbol)($3.obj)).getTipoVar()))){
								yyerror("tipos incompatibles ",$1.getFila(),$1.getColumna());		
							}
							 $$=$1;
							T_Suma_Resta t = new T_Suma_Resta(contadorTerceto,"-",$1.obj,$3.obj,ts);
														contadorVarAux++;
														t.setVariableAux(contadorVarAux);
														t.setTabla(tabla);
														contadorTerceto ++;
														listaTercetos.add(t);
														$$.obj = t;
										*/
						     Terceto t = new T_Suma_Resta(contadorTerceto,"-",$1.obj,$3.obj,st);
                             //st es la tabla de simbolos, paso lexema porque lo uso para buscar en la tabla de simbolos
                            //contadorVarAux++;         por ahora no lo necesitamos
                            //t.setVariableAux(contadorVarAux);
                            for(int i=0; i< t.errores.size();i++){
                                       yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
                                   ;}
                            contadorTerceto ++;
                            listaTercetos.add(t);
                     System.out.println(t.toString());
            $$.obj = t;
}
	| termino {$$=$1;
    $$.obj=$1.obj; //creo que es necesario para que no se pierdan los lexemas, si quieren reveanlo
}
        ;


termino: factor '/' termino {/*
                            if(!(((Symbol)($1.obj)).getTipoVar().equals(((Symbol)($3.obj)).getTipoVar()))){
								//System.out.println("tipo de primer elem: "+$1.sval+" tipo 2do elem : "+$3.sval);
								yyerror("tipos incompatibles ",$1.getFila(),$1.getColumna());		
							}
							$$=$1;
							// 
							T_Mult_Div t = new T_Mult_Div(contadorTerceto,"/",$1.obj,$3.obj,ts);
                            contadorVarAux++;
                            t.setVariableAux(contadorVarAux);
                            contadorTerceto ++;
                            t.setTabla(tabla);
                            listaTercetos.add(t);
                     System.out.println(t.toString());
            $$.obj = t;
				*/
                Terceto t = new T_Mult_Div(contadorTerceto,"/",$1.obj,$3.obj,st);
                //contadorVarAux++;         por ahora no lo necesitamos
                //t.setVariableAux(contadorVarAux);
                for(int i=0; i< t.errores.size();i++){
                           yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
                       ;}
                contadorTerceto ++;
                listaTercetos.add(t);
                     System.out.println(t.toString());
$$.obj = t;

}
	| factor '*' termino{/*
	                    if(!(((Symbol)($1.obj)).getTipoVar().equals(((Symbol)($3.obj)).getTipoVar()))){
								yyerror("tipos incompatibles ",$1.getFila(),$1.getColumna());		
							}
							$$=$1;
						// se crea terceto	
						T_Mult_Div t = new T_Mult_Div(contadorTerceto,"*",$1.obj,$3.obj,ts);
                        contadorVarAux++;
                        t.setVariableAux(contadorVarAux);
                        t.setTabla(tabla);
                        contadorTerceto ++;
                        listaTercetos.add(t);
                     System.out.println(t.toString());
        $$.obj = t;
                        */
                Terceto t = new T_Mult_Div(contadorTerceto,"*",$1.obj,$3.obj,st);
                //contadorVarAux++;         por ahora no lo necesitamos
                //t.setVariableAux(contadorVarAux);
                for(int i=0; i< t.errores.size();i++){
                           yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
                       ;}
                contadorTerceto ++;
                listaTercetos.add(t);
                     System.out.println(t.toString());
$$.obj = t;
    }
    | factor {$$=$1;
			  // terceto
			  $$.obj=$1.obj;	
			 }
	;
// integer y single son tipos de las varibles
// estero y flotante son las constantes	
factor: ENTERO {$$=$1;}
	| FLOTANTE {$$=$1;}
	| ID {if(!((Symbol)($1.obj)).isUsada()){
			//error
			yyerror("variable no declarada",$1.getFila(),$1.getColumna());
			}
			 $$=$1;
	}
	| '-' ENTERO {    /** Revisar sino pierdo el puntero al elemento qe necesito **/
					  $$=$2;
                      //Symbol aux = st.getSymbol(lex.lastSymbol);
                      st.addcambiarSigno(((Symbol)($2.obj)));  //((Symbol))($2.obj))
 		              }
	|'-' FLOTANTE{			/** Revisar sino pierdo el puntero al elemento qe necesito **/
		             $$=$2;
					 // Antes qedaban atributos sin setear
                    // Symbol aux = st.getSymbol(lex.lastSymbol);
                     st.addcambiarSigno(((Symbol)($2.obj)));  //((Symbol))($2.obj))
                    }
	;

asignacion: ID ASIG  expresion{		//necesito el tipo de la expresion
									if (!((Symbol)($1.obj)).isUsada()){
										yyerror("La variable no esta definida ",$1.getFila(),$1.getColumna());
									}else{if (!((Symbol)($1.obj)).getEsMutable()){
										yyerror("La variable no es mutable ",$1.getFila(),$1.getColumna());
									}else{if(!(((Symbol)($1.obj)).getTipoVar().equals($3.sval))){
										yyerror("Tipos incompatibles en la asignacion ",$1.getFila(),$1.getColumna());
									}}}
									// crear tercetoe de asignacion
	estructuras.add("Asignacion "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
    | LET tipo '*'ID ASIG '&' ID  { // Estoy definiendo una variable
									if (((Symbol)($4.obj)).isUsada()){
										yyerror("La variable ya esta definida ",$1.getFila(),$1.getColumna());
									}else{
										Symbol s = ((Symbol)($4.obj));
										s.setUsada(true);
										s.setEsMutable(false);
										s.setEspuntero(true);
										s.setTipoVar($2.sval);
										// faltaria mutabilidad de lo apuntado
									}
									if (!((Symbol)($7.obj)).isUsada()){
										yyerror("La variable no esta definida, &ID ",$1.getFila(),$1.getColumna());	
									}else{
										Symbol s = ((Symbol)($7.obj));
										Symbol sy = ((Symbol)($4.obj));
										if (!(s.getTipoVar().equals(sy.getTipoVar()))){
											yyerror("incompatibilidad de tipos en la asignacion ",$1.getFila(),$1.getColumna());											
										}
										if (s.isEsPuntero())
											yyerror("No se permiten punteros multiples ",$1.getFila(),$1.getColumna());
									}
									estructuras.add("Asignacion de puntero "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
    | LET tipo ID ASIG expresion  {//Estoy definiendo una variable
									if (((Symbol)($3.obj)).isUsada()){
										yyerror("La variable ya esta definida ",$1.getFila(),$1.getColumna());
									}else{
										Symbol s = ((Symbol)($3.obj));
										s.setUsada(true);
										s.setEsMutable(false);
										s.setEspuntero(false);
										s.setTipoVar($2.sval);
										// faltaria mutabilidad de lo apuntado
										if(!(s.getTipoVar().equals($5.sval))){
										yyerror("Tipos incompatibles en la asignacion ",$1.getFila(),$1.getColumna());
									}
									}
									estructuras.add("Asignacion "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
	| ASIG expresion  {yyerror("Falta elemento de asignacion y palabra reservada 'let'",$1.getFila(),$1.getColumna());}
	| ID ASIG  {yyerror("Falta elemento de asignacion ",$1.getFila(),$1.getColumna());}
	| ID error  {yyerror("no se encontro ':=' ",$1.getFila(),$1.getColumna());}
	;

exp_print: PRINT '(' CADENA ')' {estructuras.add("Expresion print "+" fila "+$1.getFila()+" columna "+$1.getColumna());
									T_Print t = new T_Print(contadorTerceto,"OUT",$3.obj,"-",ts);	
										contadorTerceto ++;
										listaTercetos.add(t);   
										$$.obj = t;
}
	| PRINT error {yyerror("Linea  Error en la construccion del print",$1.getFila(),$1.getColumna());}
	;

bloque: sent_if {}
	| sent_loop {}
	;


sent_if: IF '(' condicion ')' cuerpo ELSE cuerpo END_IF{estructuras.add("Sentencia IF Else" +" fila "+$1.getFila()+" columna "+$1.getColumna());}
        |IF '(' condicion ')' cuerpo END_IF{estructuras.add("Sentencia IF " +" fila "+$1.getFila()+" columna "+$1.getColumna());}
	|  '(' condicion ')' cuerpo ELSE cuerpo {yyerror(" falta la palabra reservada IF",$1.getFila(),$1.getColumna());}
	| IF error ELSE {yyerror(" Error en la construccion de la sentencia IF ",$1.getFila(),$1.getColumna());}
	| IF '(' condicion ')' cuerpo cuerpo {yyerror(" Falta la palabra reservada ELSE ",$1.getFila(),$1.getColumna());}
	;

sent_loop: LOOP cuerpo UNTIL '(' condicion ')'{estructuras.add("Sentencia Loop " +" fila "+$1.getFila()+" columna "+$1.getColumna());}
	| LOOP cuerpo '(' condicion ')'{yyerror("Linea  Falta palabra reservada UNTIL",$1.getFila(),$1.getColumna());}
	;

cuerpo: ejecutable {}
	| '{' lista_ejecutable '}'{}
	| error lista_ejecutable '}' {yyerror("LInea  Omision de la palabra reservada '{' ",$1.getFila(),$1.getColumna());}
	;

condicion: expresion '>' expresion {if(!(((Symbol)($1.obj)).getTipoVar().equals(((Symbol)($3.obj)).getTipoVar())))
										yyerror("tipos incompatibles ",$1.getFila(),$1.getColumna());}
	| expresion '<' expresion {if(!(((Symbol)($1.obj)).getTipoVar().equals(((Symbol)($3.obj)).getTipoVar())))
										yyerror("tipos incompatibles ",$1.getFila(),$1.getColumna());}
	| expresion IGUAL expresion {if(!(((Symbol)($1.obj)).getTipoVar().equals(((Symbol)($3.obj)).getTipoVar())))
										yyerror("tipos incompatibles ",$1.getFila(),$1.getColumna());}
	| expresion DIST expresion {if(!(((Symbol)($1.obj)).getTipoVar().equals(((Symbol)($3.obj)).getTipoVar())))
										yyerror("tipos incompatibles ",$1.getFila(),$1.getColumna());}
	| expresion MAYIG expresion {if(!(((Symbol)($1.obj)).getTipoVar().equals(((Symbol)($3.obj)).getTipoVar())))
										yyerror("tipos incompatibles ",$1.getFila(),$1.getColumna());}
	| expresion MENIG expresion {if(!(((Symbol)($1.obj)).getTipoVar().equals(((Symbol)($3.obj)).getTipoVar())))
										yyerror("tipos incompatibles ",$1.getFila(),$1.getColumna());}
	| '>' expresion {yyerror("Linea  se esperaba una expresion y se encontro '>'",$1.getFila(),$1.getColumna());}
	| '<' expresion {yyerror("Linea  se esperaba una expresion y se encontro '<'",$1.getFila(),$1.getColumna());}
	| MAYIG expresion {yyerror("Linea  se esperaba una expresion y se encontro '>='",$1.getFila(),$1.getColumna());}
	| MENIG expresion {yyerror("Linea  se esperaba una expresion y se encontro '<='",$1.getFila(),$1.getColumna());}
	;

%%

  LexicalAnalyzer lex;
  SymbolTable st;
  Errors errors;
  public ArrayList<String> estructuras=new ArrayList<>();
  public ArrayList<String> tokens = new ArrayList<>();
  public ArrayList<String> id = new ArrayList<>();
  ArrayList<Terceto> listaTercetos = new ArrayList<>();
  int contadorVarAux=0;
  int contadorTerceto=0;

    int yylex(){

    int a = lex.getNextToken();

    if (lex.yylval != null){
      yylval = lex.yylval;
      lex.yylval = null;
    }else{
      yylval = new ParserVal();
    }
    tokens.add(yylval.toString()+" fila: "+yylval.getFila()+" columna: "+yylval.getColumna());
    return a;
  }

  public Parser(LexicalAnalyzer lex,SymbolTable st, Errors er)
{
  this.lex = lex;
  this.st = st;
  this.errors=er;
}

void yyerror(String s){
    errors.setError(lex.row, lex.column,s);
  }
void yyerror(String s,int row,int column){
      errors.setError(row,column,s);
  }