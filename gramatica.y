/* Declaraciones */

%{ package AnalizadorSintactico;
%}

/* Lista de tokens */

%token IF ELSE PRINT INTEGER ID CTE CADENA ASIG MAYIG MENIG IGUAL DIST FIN SINGLE END_IF LOOP UNTIL LET MUT ENTERO FLOTANTE

/* Reglas */

%%

programa : lista_sentencia{} 
	 | FIN {yyerror("No hay sentencia");}   		
         | error {yyerror("No hay sentencia");} FIN{}
	 ;

lista_sentencia : sentencia{}
	 | sentencia lista_sentencia{} 
	 ;

sentencia: ejecutable {System.out.println("Programa");}
	| declaracion {System.out.println("Programa");}
        ;

declaracion: LET MUT tipo lista_id ',' {System.out.println("Declaracion"); /*aca detectamos una declarancion, tenemos que agregar al vector de estructuras sintacticas
                                                           reconocidas un nuevo string que diga que reconocimos una declaracion en la linea $3.getlinea;*/
/* tambien hay que agregar que declaracion puede ser LET tipo lista_id*/}
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

expresion: termino '+' expresion {System.out.println("Suma");}
	| termino '-' expresion {System.out.println("Resta");}
	| termino {}
        ;

termino: factor '/' termino {System.out.println("Division");}
	| factor '*' termino{System.out.println("Producto");}
        | factor {System.out.println("Factor");}
	;
	
factor: ENTERO {System.out.println("Constante");}
	| FLOTANTE {}
	| ID {}
	| '-' ENTERO {
                      Symbol aux = st.getSymbol(lex.lastSymbol);
                      st.addcambiarSigno(aux);
                      System.out.println("Constante negativa");
 		              }
	|'-' FLOTANTE{
                     Symbol aux = st.getSymbol(lex.lastSymbol);
                     st.addcambiarSigno(aux);
                     System.out.println("Constante negativa");
                    }
	;

asignacion: ID ASIG expresion  {System.out.println("Asignacion"); /*vectorStructuras.add("Numero linea", ((Token).$1).nroLinea,"Sentencia asignacion")*/}
        | LET tipo '*'ID ASIG '&' ID  {System.out.println("Asignacion de puntero");/*reveer estas asignaciones*/}
        | LET tipo ID ASIG expresion  {System.out.println("Asignacion");}
	| ASIG expresion  {yyerror("Falta elemento de asignacion y palabra reservada 'let'");}
	| ID ASIG  {yyerror("Falta elemento de asignacion ");}
	| ID error  {yyerror("no se encontro ':=' ");}
	;

exp_print: PRINT '(' CADENA ')' {System.out.println("Expresion print");}
	| PRINT error {yyerror("Linea  Error en la construccion del print");}
	;

bloque: sent_if {System.out.println("Sentencia IF"); /*aca detectamos una declarancion, tenemos que agregar al vector de estructuras sintacticas reconocidas un nuevo string que diga que reconocimos una declaracion en la linea $1.getlinea;*/
}
	| sent_loop {System.out.println("Sentencia loop"); /*aca detectamos una declarancion, tenemos que agregar al vector de estructuras sintacticas reconocidas un nuevo string que diga que reconocimos una declaracion en la linea $1.getlinea;*/
}
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

condicion: expresion '>' expresion {System.out.println("Expresion mayor");}
	| expresion '<' expresion {System.out.println("Expresion menor");}
	| expresion IGUAL expresion {System.out.println("Expresion igual");}
	| expresion DIST expresion {System.out.println("Expresion distinto");}
	| expresion MAYIG expresion {System.out.println("Expresion mayor o igual");}
	| expresion MENIG expresion {System.out.println("Expresion menor o igual");}
	| '>' expresion {yyerror("LInea  se esperaba una expresion y se encontro '>'");}
	| '<' expresion {yyerror("LInea  se esperaba una expresion y se encontro '<'");}
	| MAYIG expresion {yyerror("LInea  se esperaba una expresion y se encontro '>='");}
	| MENIG expresion {yyerror("LInea  se esperaba una expresion y se encontro '<='");}
	;
