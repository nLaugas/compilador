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
											
										//id.clear();/*vacio lista*/
										/*si algun id ya estaba definido debo retornar error*/	}
        | error {yyerror("Declaracion mal definida ");}
        ;

lista_id: ID {id.add( ((Symbol)($1.obj)).getLexema() ); } 
	| '*' ID {	((Symbol)($2.obj)).setEspuntero(true); //reconoce puntero
				id.add(((Symbol)($2.obj)).getLexema());} //agrega a lista de identificadores reconocidos
	
	| ID ';' lista_id {id.add(((Symbol)($1.obj)).getLexema());}
        | ID lista_id{yyerror("Se esperaba ';' ",$1.getFila(),$1.getColumna());}
        ;

tipo: INTEGER {$$.sval="integer";}
	| FLOTANTE {$$.sval="float";}
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
	
factor: ENTERO {}
	| FLOTANTE {}
	| ID {}
	| '-' ENTERO {
                      Symbol aux = st.getSymbol(lex.lastSymbol);
                      st.addcambiarSigno(aux);
                     
 		              }
	|'-' FLOTANTE{
                     Symbol aux = st.getSymbol(lex.lastSymbol);
                     st.addcambiarSigno(aux);
                    
                    }
	;

asignacion: ID ASIG  expresion { /*if (!(Symbol)$1.obj.usar())/*((!(Symbol)$1.obj.usar())&&(!(Symbol)$1.obj.getTipo()==$3.ival))*///{
									//yyerror("error en la asignacion, la variable no existe ",$1.getFila(),$1.getColumna());		
									//}
	estructuras.add("Asignacion "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
    | LET tipo '*'ID ASIG '&' ID  { //if ((Symbol)$1.obj.usar()){
											// esta para usar, entonces ya existe
									//		yyerror("error en la asignacion, la variable ya esta definida",$1.getFila(),$1.getColumna());	
									//		}
											//deberia asignarle el tipo a la variable

										estructuras.add("Asignacion de puntero "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
    | LET tipo ID ASIG expresion  {//if ((Symbol)$1.obj.usar()){
											// esta para usar, entonces ya existe
										//	yyerror("error en la asignacion, la variable ya esta definida",$1.getFila(),$1.getColumna());	
										//	}
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