/* Declaraciones */

%{ 
import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;
import SymbolTable.*;

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
											else{
												yyerror("Variable ya definida ",$1.getFila(),$1.getColumna());		
											}					
                                            }id.clear();
										}
        | error {yyerror("Declaracion mal definida ");}
        ;

lista_id: ID {id.add( ((Symbol)($1.obj)).getLexema() ); } 
	| '*' ID {	((Symbol)($2.obj)).setEspuntero(true); //reconoce puntero
				id.add(((Symbol)($2.obj)).getLexema());} //agrega a lista de identificadores reconocidos
	
	| ID ';' lista_id {id.add(((Symbol)($1.obj)).getLexema());}
	| '*' ID ';' lista_id {id.add(((Symbol)($2.obj)).getLexema());}
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

expresion: termino '+' expresion {}
	| termino '-' expresion {}
	| termino {}
        ;

termino: factor '/' termino {}
	| factor '*' termino{}
        | factor {}
	;
// integer y single son tipos de las varibles
// estero y flotante son las constantes	
factor: ENTERO {}
	| SINGLE {}
	| ID {if(!((Symbol)($1.obj)).isUsada()){
			//error
			yyerror("variable no declarada",$1.getFila(),$1.getColumna());
	}}
	| '-' ENTERO {    
                      //Symbol aux = st.getSymbol(lex.lastSymbol);
                      st.addcambiarSigno(((Symbol)($2.obj)));  //((Symbol))($2.obj))
                     
 		              }
	|'-' SINGLE{
		             
                    // Symbol aux = st.getSymbol(lex.lastSymbol);
                     st.addcambiarSigno(((Symbol)($2.obj)));  //((Symbol))($2.obj))
                    }
	;

asignacion: ID ASIG  expresion{		if (!((Symbol)($1.obj)).getEsMutable()){
										yyerror("La variable no es mutable ",$1.getFila(),$1.getColumna());
									}
									if (!((Symbol)($1.obj)).isUsada()){
										yyerror("La variable no esta definida ",$1.getFila(),$1.getColumna());
									}
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
									}
									estructuras.add("Asignacion "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
	| ASIG expresion  {yyerror("Falta elemento de asignacion y palabra reservada 'let'",$1.getFila(),$1.getColumna());}
	| ID ASIG  {yyerror("Falta elemento de asignacion ",$1.getFila(),$1.getColumna());}
	| ID error  {yyerror("no se encontro ':=' ",$1.getFila(),$1.getColumna());}
	;

exp_print: PRINT '(' CADENA ')' {estructuras.add("Expresion print "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
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

condicion: expresion '>' expresion {}
	| expresion '<' expresion {}
	| expresion IGUAL expresion {}
	| expresion DIST expresion {}
	| expresion MAYIG expresion {}
	| expresion MENIG expresion {}
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