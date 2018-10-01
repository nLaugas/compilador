/* Declaraciones */

%{ package AnalizadorSintactico;
%}

/* Lista de tokens */

%token IF ELSE PRINT INTEGER ID CTE CADENA ASIG MAYIG MENIG IGUAL DIST FIN SINGLE END_IF LOOP UNTIL LET MUT ENTERO FLOTANTE

/* Reglas */

%%

programa : lista_sentencia{} 
	 | FIN {yyerror("LInea  Se esperaba una sentencia");}   		
         | error {yyerror("LInea  Se esperaba una sentencia");} FIN{}
	 ;

lista_sentencia : sentencia{}
	 | sentencia lista_sentencia{} 
	 ;

sentencia: ejecutable {System.out.println("Programa");}
	| declaracion {System.out.println("Programa");}
        ;

declaracion: tipo lista_id ',' {}
        | error {yyerror("Declaracion mal definida ");}
        ;

lista_id: ID {}
	| ID ';' lista_id {}
        | ID lista_id{yyerror("LInea  Se esperaba el caracter de separacion de identificadores ';' ");}
        ;

tipo: INTEGER {}
	| SINGLE {}
	| error ','{yyerror("LInea  tipo indefinido");}
	;

lista_ejecutable: ejecutable {}
	| ejecutable lista_ejecutable {}
	;

ejecutable: asignacion ','{}
          | bloque {}
          | exp_print ','{}
	  ;

expresion: termino '+' expresion {System.out.println("Expresion suma");}
	| termino '-' expresion {System.out.println("Expresion resta");}
	| termino {}
        ;

termino: factor '/' termino {System.out.println("Expresion division");}
	| factor '*' termino{System.out.println("Expresion producto");}
	| factor {System.out.println("Factor");}
	;
	
factor: ENTERO {/*Compilador.tablaSimbolos.get(yylval.sval).setTipoDef('P');*/
             System.out.println("Constante");}
	| FLOTANTE
	| ID {}
	//| TOINT '(' expresion ')' {System.out.println("Expresion toint");}
	| '-' ENTERO { /*double a = Double.parseDouble(yylval.sval);
		   a = -1*a;
		   FilaTS nueva = new FilaTS();
 		   nueva.setValor((long)a);
 		   nueva.setTipoDef('N');
 	  	   String key = '-' + yylval.sval;
 		   if(Compilador.tablaSimbolos.containsKey(yylval.sval)){
    			if(Compilador.tablaSimbolos.get(yylval.sval).getTipoDef() == 'I'){
        			if ((a <= 0) && (a > -2147483647.0)){
    	   				Compilador.tablaSimbolos.remove(yylval.sval);
           				Compilador.tablaSimbolos.put(key, nueva);
        			}
        			else yyerror("Linea  constante fuera de rango");
    			}else if(Compilador.tablaSimbolos.get(yylval.sval).getTipoDef() == 'P'){
                			if ((a <= 0) && (a > -2147483647.0))
                    				Compilador.tablaSimbolos.put(key,nueva);
          				}else yyerror("Linea  constante fuera de rango");
    		   }*/
 		   System.out.println("Constante negativa");}
	|'-' FLOTANTE
	;

asignacion: ID ASIG expresion  {System.out.println("Asignacion");}
	| ASIG expresion  {yyerror("LInea  Se esperaba un identificador y se encontro ':'");}
	| ID ASIG  {yyerror("LInea  Se esperaba una expresion y se encontro ';' ");}
	| ID error  {yyerror("LInea  no es ':=' ");}
	;

exp_print: PRINT '(' CADENA ')' {System.out.println("Expresion print");}
	| PRINT error {yyerror("Linea  Error en la construccion del print");}
	;

bloque: sent_if {System.out.println("Sentencia IF");}
	| sent_loop {System.out.println("Sentencia loop");}
	;


sent_if: IF '(' condicion ')' cuerpo ELSE cuerpo END_IF{}
        |IF '(' condicion ')' cuerpo END_IF{}
	|  '(' condicion ')' cuerpo ELSE cuerpo {yyerror(" falta la palabra reservada IF");}
	| IF error ELSE {yyerror(" Error en la construccion de la sentencia IF ");}
	| IF '(' condicion ')' cuerpo cuerpo {yyerror(" Falta la palabra reservada ELSE ");}
	| IF condicion ')' cuerpo cuerpo {yyerror(" Omision del ( .Falta la palabra reservada ELSE ");}
	| IF '(' condicion cuerpo cuerpo {yyerror(" Omision del ) .Falta la palabra reservada ELSE ");}
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
	| IGUAL expresion {yyerror("LInea  se esperaba una expresion y se encontro '=='");}
	| DIST expresion {yyerror("LInea  se esperaba una expresion y se encontro '!='");}
	| MAYIG expresion {yyerror("LInea  se esperaba una expresion y se encontro '>='");}
	| MENIG expresion {yyerror("LInea  se esperaba una expresion y se encontro '<='");}
	;
