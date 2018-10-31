/* Declaraciones */

%{ package AnalizadorSintactico;
%}

/* Lista de tokens */

%token IF ELSE PRINT INTEGER ID CTE CADENA ASIG MAYIG MENIG IGUAL DIST FIN SINGLE END_IF LOOP UNTIL LET MUT ENTERO FLOTANTE

/* Reglas */

%{

  LexicalAnalyzer lex;
  SymbolTable st;
  Errors errors;
  public ArrayList<String> estructuras=new ArrayList<>();
  public ArrayList<String> tokens = new ArrayList<>();

    int yylex(){
    // yylval = lex.getVal();
/**Lo dejamos para despues!!!!**/
    //Para mostrar los tokens encontrados
    int a = lex.getNextToken();
//    tokens.add(lex.yylval.toString()+" fila: "+lex.yylval.getFila()+" columna: "+lex.yylval.getColumna());

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
  //nothing to do
}

void yyerror(String s){
    errors.setError(lex.row, lex.column,s);
  }

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

declaracion: LET MUT tipo lista_id ',' {}
        | error {yyerror("Declaracion mal definida ");}
        ;

lista_id: ID {}
	| '*' ID {}
	| ID ';' lista_id {}
        | ID lista_id{yyerror("Se esperaba ';' ");}
        ;

tipo: INTEGER {}
	| SINGLE {}
	| error ','{yyerror("Tipo indefinido");}
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

asignacion: ID ASIG expresion  {estructuras.add("Asignacion "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
        | LET tipo '*'ID ASIG '&' ID  {estructuras.add("Asignacion de puntero "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
        | LET tipo ID ASIG expresion  {estructuras.add("Asignacion "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
	| ASIG expresion  {yyerror("Falta elemento de asignacion y palabra reservada 'let'");}
	| ID ASIG  {yyerror("Falta elemento de asignacion ");}
	| ID error  {yyerror("no se encontro ':=' ");}
	;

exp_print: PRINT '(' CADENA ')' {estructuras.add("Expresion print "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
	| PRINT error {yyerror("Linea  Error en la construccion del print");}
	;

bloque: sent_if {estructuras.add("Sentencia IF " +" fila "+$1.getFila()+" columna "+$1.getColumna());}
	| sent_loop {estructuras.add("Sentencia Loop " +" fila "+$1.getFila()+" columna "+$1.getColumna());}
	;


sent_if: IF '(' condicion ')' cuerpo ELSE cuerpo END_IF{}
        |IF '(' condicion ')' cuerpo END_IF{}
	|  '(' condicion ')' cuerpo ELSE cuerpo {yyerror(" falta la palabra reservada IF");}
	| IF error ELSE {yyerror(" Error en la construccion de la sentencia IF ");}
	| IF '(' condicion ')' cuerpo cuerpo {yyerror(" Falta la palabra reservada ELSE ");}
	;

sent_loop: LOOP cuerpo UNTIL '(' condicion ')'{}   
	| LOOP cuerpo '(' condicion ')'{yyerror("Linea  Falta palabra reservada UNTIL");}   
	;

cuerpo: ejecutable {}
	| '{' lista_ejecutable '}'{}
	| error lista_ejecutable '}' {yyerror("LInea  Omision de la palabra reservada '{' ");}
	;

condicion: expresion '>' expresion {}
	| expresion '<' expresion {}
	| expresion IGUAL expresion {}
	| expresion DIST expresion {}
	| expresion MAYIG expresion {}
	| expresion MENIG expresion {}
	| '>' expresion {yyerror("Linea  se esperaba una expresion y se encontro '>'");}
	| '<' expresion {yyerror("Linea  se esperaba una expresion y se encontro '<'");}
	| MAYIG expresion {yyerror("Linea  se esperaba una expresion y se encontro '>='");}
	| MENIG expresion {yyerror("Linea  se esperaba una expresion y se encontro '<='");}
	;
