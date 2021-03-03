/* Declaraciones */

%{
package AnalizadorSintactico;
import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;
import SymbolTable.*;
import Tercetos.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Stack;
%}

/* Lista de tokens */

%token IF ELSE PRINT INTEGER ID CTE CADENA ASIG MAYIG MENIG DIST FIN SINGLE END_IF LOOP UNTIL LET MUT ENTERO FLOTANTE

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

declaracion: LET MUT tipo lista_id ',' {
                                            Vector<ParserVal> vectorTokens = (Vector<ParserVal>)($4.obj);
                                            String tipo = ($3.sval);// para que esto ande tocar la regla del no terminal tipo
                                            tipo = tipo.toLowerCase();
                                            for(int i=0; i< vectorTokens.size();i++){
                                                ParserVal token = vectorTokens.elementAt(i);
                                                Symbol simbolo =(Symbol) token.obj;
                                                if (!simbolo.isUsada()){
                                                    simbolo.setUsada(true);
                                                    simbolo.setEsMutable(true);
                                                    simbolo.setTipoVar($3.sval);}
                                                else
                                                    yyerror("Se esta intentado redeclarar la variable "+simbolo.getLexema(),token.getFila(),token.getColumna());
                                                }

										}
        | error {yyerror("Declaracion mal definida ");}
        ;

lista_id: ID {//  id.add( ((Symbol)($1.obj)).getLexema() );
                Vector<ParserVal> vect = new Vector<ParserVal>();//$1 es el parser val con el symbolo de ese ID
                vect.add($1);//ver si anda, hay que castear a Symbol?
                $$.obj = vect; }
	| '*' ID {	((Symbol)($2.obj)).setEspuntero(true); //reconoce puntero
				//id.add(((Symbol)($2.obj)).getLexema());//} //agrega a lista de identificadores reconocidos
				Vector<ParserVal> vect = new Vector<ParserVal>();//$2 es el parser val con el symbolo de ese ID
                vect.add($2);//ver si anda, hay que castear a Symbol? .obj
                $$.obj = vect;
                                }
	
	| ID ';' lista_id {//id.add(((Symbol)($1.obj)).getLexema());
                     Vector<ParserVal> vect = (Vector<ParserVal>)($3.obj); //$3 me trae el vector original primero y desp aumenta
                    vect.add($1);//ver si anda, hay que castear a Symbol? .obj
                    $$.obj = vect;
	}
	| '*' ID ';' lista_id {//id.add(((Symbol)($2.obj)).getLexema());
                            ((Symbol)($2.obj)).setEspuntero(true); //reconoce puntero
                            Vector<ParserVal> vect = (Vector<ParserVal>)($4.obj); //$4 me trae el vector original primero y desp aumenta
                            vect.add($2);//ver si anda, hay que castear a Symbol? .obj
                            $$.obj = vect;
	}
        | ID lista_id{yyerror("Se esperaba ';' ",$1.getFila(),$1.getColumna());}
        ;

tipo: INTEGER {$$.sval="integer";}
	| SINGLE {$$.sval="single";}
	| error ','{yyerror("Tipo indefinido",$1.getFila(),$1.getColumna());}
	;

lista_ejecutable: ejecutable {}
	| ejecutable lista_ejecutable {}
	;

ejecutable: asignacion ','{}
          | bloque {//#######Solo llego aca si termino un if o un loop
        Integer i = p.pop();
        if (listaTercetos.get(i).getOperador() == "BI")
        {
        listaTercetos.get(i).setOperando1(contadorTerceto);

            }
        if (listaTercetos.get(i).getOperador() == "BF")
            {	listaTercetos.get(i).setOperando2(contadorTerceto);
            }
                  if (intLoop == 0){
          Terceto t = new T_Fin(contadorTerceto,"FIN_DE_SALTO","trampita","trampita",st);
          contadorTerceto ++;
          listaTercetos.add(t);
          }
          else
          { ((T_BF)listaTercetos.get(i)).invertFlags();
            listaTercetos.get(i).setOperando2(intLoop);
            intLoop=0;
          }
//podriamos hacer un terceto fin aca que sea a donde apunte el salto, este terceto no haria nada solo funcionaria de label del salto
}
          | exp_print ','{}
	  ;

expresion: expresion '+' termino {
                boolean factorCte = false;
                boolean terminoCte = false;
                boolean esFloat = false;
                Integer intCte = null,intCte2=null ;/*= Integer.parseInt(lexema.substring(0,1);*/
                Float floatCte = null,floatCte2=null; /*= Float.parseFloat(lexema);*/

                /*pregunto por el factor*/
                if ((((Symbol)($3.obj)).getTipo() ==276)) {
                  floatCte = Float.parseFloat(((Symbol)$3.obj).getLexema());
                  factorCte = true;
                  esFloat = true;

                }
                if ((((Symbol)($3.obj)).getTipo() ==275)){
                  String lex =  (((Symbol)$3.obj).getLexema());
                  intCte = Integer.parseInt(lex.substring(0,lex.length()-2));
                  factorCte = true;
                }

                /*pregunto por el termino*/

                if (   ($1.obj).toString().charAt(0) == 'C'   ){
                  if ((((Symbol)($1.obj)).getTipo() ==276)) {
                    floatCte2 = Float.parseFloat(((Symbol)$1.obj).getLexema());
                    terminoCte= true;
                    esFloat = true;

                  }
                  if ((((Symbol)($1.obj)).getTipo() ==275)){
                    String lex =  (((Symbol)$1.obj).getLexema());
                    intCte2 = Integer.parseInt(lex.substring(0,lex.length()-2));
                    terminoCte = true;
                  }
                }
                 Symbol s = null;
                /* pregunto por termino y factor*/
                if (factorCte && terminoCte){
                      if (esFloat){
                        s = ((Symbol)($3.obj)).clone();
                        s.setLexema(String.valueOf(floatCte+floatCte2));
                        st.setSymbol(s);

                      }
                      else{
                         s = ((Symbol)($3.obj)).clone();/*new Symbol(String.valueOf(intCte*intCte2)+"_i",((Symbol)($1.obj)).getTipo(),false,false,false);*/
                         s.setLexema(String.valueOf(intCte+intCte2)+"_i");
                         /*((Symbol)($1.obj)).setLexema(String.valueOf(intCte*intCte2)+"_i");*/
                        st.setSymbol(s);
                      }
                  yyval.obj=s;

                }else   
                      {
								Terceto t = new T_Suma_Resta(contadorTerceto,"+",$3.obj,$1.obj,st);
                                //st es la tabla de simbolos, paso lexema porque lo uso para buscar en la tabla de simbolos
                                t.setVariableAux(contadorVarAux);
                                contadorVarAux++;
                                for(int i=0; i< t.errores.size();i++){
                                          yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
                                      ;}
                                contadorTerceto ++;
                                listaTercetos.add(t);
                     System.out.println(t.toString());
                $$=$3;
                $$.obj = t;
                }

}
	| expresion '-' termino {
                boolean factorCte = false;
                boolean terminoCte = false;
                boolean esFloat = false;
                Integer intCte = null,intCte2=null ;/*= Integer.parseInt(lexema.substring(0,1);*/
                Float floatCte = null,floatCte2=null; /*= Float.parseFloat(lexema);*/

                /*pregunto por el termino*/
                if ((((Symbol)($3.obj)).getTipo() ==276)) {
                  floatCte2 = Float.parseFloat(((Symbol)$3.obj).getLexema());
                  factorCte = true;
                  esFloat = true;

                }
                if ((((Symbol)($3.obj)).getTipo() ==275)){
                  String lex =  (((Symbol)$3.obj).getLexema());
                  intCte2 = Integer.parseInt(lex.substring(0,lex.length()-2));
                  factorCte = true;
                }
                /*pregunto por el expresion*/
                if (   ($1.obj).toString().charAt(0) == 'C'   ){
                  if ((((Symbol)($1.obj)).getTipo() ==276)) {
                    floatCte = Float.parseFloat(((Symbol)$1.obj).getLexema());
                    terminoCte= true;
                    esFloat = true;

                  }
                  if ((((Symbol)($1.obj)).getTipo() ==275)){
                    String lex =  (((Symbol)$1.obj).getLexema());
                    intCte = Integer.parseInt(lex.substring(0,lex.length()-2));
                    terminoCte = true;
                  }
                }


                 Symbol s = null;
                /* pregunto por termino y factor*/
                if (factorCte && terminoCte){
                      if (esFloat){
                        s = ((Symbol)($3.obj)).clone();
                        s.setLexema(String.valueOf(floatCte-floatCte2));
                        st.setSymbol(s);

                      }
                      else{
                         s = ((Symbol)($3.obj)).clone();/*new Symbol(String.valueOf(intCte*intCte2)+"_i",((Symbol)($1.obj)).getTipo(),false,false,false);*/
                         s.setLexema(String.valueOf(intCte-intCte2)+"_i");
                         /*((Symbol)($1.obj)).setLexema(String.valueOf(intCte*intCte2)+"_i");*/
                        st.setSymbol(s);
                      }
                  yyval.obj=s;

                }else   
                {
						     Terceto t = new T_Suma_Resta(contadorTerceto,"-",$3.obj,$1.obj,st);
                             //st es la tabla de simbolos, paso lexema porque lo uso para buscar en la tabla de simbolos
                            t.setVariableAux(contadorVarAux);
                            contadorVarAux++;
                            for(int i=0; i< t.errores.size();i++){
                                       yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
                                   ;}
                            contadorTerceto ++;
                            listaTercetos.add(t);
                     System.out.println(t.toString());
              $$=$3;
              $$.obj = t;
              }
}
	| termino {$$=$1;
    $$.obj=$1.obj; //creo que es necesario para que no se pierdan los lexemas, si quieren reveanlo
}
        ;


termino: termino '/' factor {

              
                boolean factorCte = false;
                boolean terminoCte = false;
                boolean esFloat = false;
                Integer intCte = null,intCte2=null ;/*= Integer.parseInt(lexema.substring(0,1);*/
                Float floatCte = null,floatCte2=null; /*= Float.parseFloat(lexema);*/

                /*pregunto por el factor*/
                if ((((Symbol)($3.obj)).getTipo() ==276)) {
                  floatCte = Float.parseFloat(((Symbol)$3.obj).getLexema());
                  factorCte = true;
                  esFloat = true;

                }
                if ((((Symbol)($3.obj)).getTipo() ==275)){
                  String lex =  (((Symbol)$3.obj).getLexema());
                  intCte = Integer.parseInt(lex.substring(0,lex.length()-2));
                  factorCte = true;
                }

                /*pregunto por el termino*/

                if (   ($1.obj).toString().charAt(0) == 'C'   ){
                  if ((((Symbol)($1.obj)).getTipo() ==276)) {
                    floatCte2 = Float.parseFloat(((Symbol)$1.obj).getLexema());
                    terminoCte= true;
                    esFloat = true;

                  }
                  if ((((Symbol)($1.obj)).getTipo() ==275)){
                    String lex =  (((Symbol)$1.obj).getLexema());
                    intCte2 = Integer.parseInt(lex.substring(0,lex.length()-2));
                    terminoCte = true;
                  }
                }
                 Symbol s = null;
                /* pregunto por termino y factor*/
                if (factorCte && terminoCte){
                      if (esFloat){
                        s = ((Symbol)($3.obj)).clone();
                        s.setLexema(String.valueOf(floatCte/floatCte2));
                        st.setSymbol(s);

                      }
                      else{
                         s = ((Symbol)($3.obj)).clone();/*new Symbol(String.valueOf(intCte*intCte2)+"_i",((Symbol)($1.obj)).getTipo(),false,false,false);*/
                         s.setLexema(String.valueOf(intCte/intCte2)+"_i");
                         /*((Symbol)($1.obj)).setLexema(String.valueOf(intCte*intCte2)+"_i");*/
                        st.setSymbol(s);
                      }
                  yyval.obj=s;

                }else            
              {
                Terceto t = new T_Mult_Div(contadorTerceto,"/",$3.obj,$1.obj,st);
                t.setVariableAux(contadorVarAux);
                contadorVarAux++;
                for(int i=0; i< t.errores.size();i++){
                           yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
                       ;}
                contadorTerceto ++;
                listaTercetos.add(t);
                System.out.println(t.toString());
                $$=$3;
                $$.obj = t;
            }
}
	| termino '*' factor{
                boolean factorCte = false;
                boolean terminoCte = false;
                boolean esFloat = false;
                Integer intCte = null,intCte2=null ;/*= Integer.parseInt(lexema.substring(0,1);*/
                Float floatCte = null,floatCte2=null; /*= Float.parseFloat(lexema);*/

                /*pregunto por el factor*/
                if ((((Symbol)($3.obj)).getTipo() ==276)) {
                  floatCte = Float.parseFloat(((Symbol)$3.obj).getLexema());
                  factorCte = true;
                  esFloat = true;

                }
                if ((((Symbol)($3.obj)).getTipo() ==275)){
                  String lex =  (((Symbol)$3.obj).getLexema());
                  intCte = Integer.parseInt(lex.substring(0,lex.length()-2));
                  factorCte = true;
                }

                /*pregunto por el termino*/

                if (   ($1.obj).toString().charAt(0) == 'C'   ){
                  if ((((Symbol)($1.obj)).getTipo() ==276)) {
                    floatCte2 = Float.parseFloat(((Symbol)$1.obj).getLexema());
                    terminoCte= true;
                    esFloat = true;

                  }
                  if ((((Symbol)($1.obj)).getTipo() ==275)){
                    String lex =  (((Symbol)$1.obj).getLexema());
                    intCte2 = Integer.parseInt(lex.substring(0,lex.length()-2));
                    terminoCte = true;
                  }
                }
                 Symbol s = null;
                /* pregunto por termino y factor*/
                if (factorCte && terminoCte){
                      if (esFloat){
                        s = ((Symbol)($3.obj)).clone();
                        s.setLexema(String.valueOf(floatCte*floatCte2));
                        st.setSymbol(s);

                      }
                      else{
                         s = ((Symbol)($3.obj)).clone();/*new Symbol(String.valueOf(intCte*intCte2)+"_i",((Symbol)($1.obj)).getTipo(),false,false,false);*/
                         s.setLexema(String.valueOf(intCte*intCte2)+"_i");
                         /*((Symbol)($1.obj)).setLexema(String.valueOf(intCte*intCte2)+"_i");*/
                        st.setSymbol(s);
                      }
                  yyval.obj=s;

                }else
                      {
                /* lo que ya estaba*/
                Terceto t = new T_Mult_Div(contadorTerceto,"*",$3.obj,$1.obj,st);
                t.setVariableAux(contadorVarAux);
                contadorVarAux++;
                for(int i=0; i< t.errores.size();i++){
                           yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
                       ;}
                contadorTerceto ++;
                listaTercetos.add(t);
                System.out.println(t.toString());
                $$=$3;
                $$.obj = t;
          }
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
}}
                Terceto t = new T_Asignacion(contadorTerceto,":=",$1.obj,$3.obj,st);
                t.setVariableAux(contadorVarAux);//casi seguro que si hay que crearla aca
                contadorVarAux++;//
                for(int i=0; i< t.errores.size();i++){
                           yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
                       ;}
                contadorTerceto ++;
                listaTercetos.add(t);
                System.out.println(t.toString());
                $$=$1;
                $$.obj = t;
	estructuras.add("Asignacion "+" fila "+$1.getFila()+" columna "+$1.getColumna());}

    | LET tipo '*' ID ASIG '&' ID  
    { 
    // Estoy definiendo una variable
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
        if (s.isEsPuntero())
            yyerror("No se permiten punteros multiples ",$1.getFila(),$1.getColumna());
                                }
	  Terceto t = new T_Asignacion(contadorTerceto,"&",$4.obj,$7.obj,st);
    t.setVariableAux(contadorVarAux);
    contadorVarAux++;
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    $$=$1;
    $$.obj = t;
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
            // faltaria mutabilidad de lo apuntado                                }
      Terceto t = new T_Asignacion(contadorTerceto,":=",$3.obj,$5.obj,st);
    t.setVariableAux(contadorVarAux);
    contadorVarAux++;
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    $$=$1;
    $$.obj = t;
}
    estructuras.add("Asignacion "+" fila "+$1.getFila()+" columna "+$1.getColumna());}
	| ASIG expresion  {yyerror("Falta elemento de asignacion y palabra reservada 'let'",$1.getFila(),$1.getColumna());}
	| ID ASIG  {yyerror("Falta elemento de asignacion ",$1.getFila(),$1.getColumna());}
	| ID error  {yyerror("no se encontro ':=' ",$1.getFila(),$1.getColumna());}
	;

exp_print: PRINT '(' CADENA ')' {estructuras.add("Expresion print "+" fila "+$1.getFila()+" columna "+$1.getColumna());
                Terceto t = new T_Print(contadorTerceto,"PRINT",$3.obj,"",st);
               // t.setVariableAux(contadorVarAux);         //un print no tiene resultado por ende no tiene tipo
                //contadorVarAux++;         creo que para los print no es necesario porque los aux son para el resultado
                for(int i=0; i< t.errores.size();i++){
                           yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
                       ;}
                contadorTerceto ++;
                listaTercetos.add(t);
                System.out.println(t.toString());
                $$=$1;
                $$.obj = t;
}
	| PRINT error {yyerror("Linea  Error en la construccion del print",$1.getFila(),$1.getColumna());}
	;

bloque: sent_if {}
	| sent_loop {}
	;


sent_if: IF condicion_salto cuerpo else_ cuerpo END_IF{estructuras.add("Sentencia IF Else" +" fila "+$1.getFila()+" columna "+$1.getColumna());}
        |IF condicion_salto cuerpo END_IF{estructuras.add("Sentencia IF " +" fila "+$1.getFila()+" columna "+$1.getColumna());}
	|  condicion_salto cuerpo else_ cuerpo {yyerror(" falta la palabra reservada IF",$1.getFila(),$1.getColumna());}
	| IF error else_ {yyerror(" Error en la construccion de la sentencia IF ",$1.getFila(),$1.getColumna());}
	| IF condicion_salto cuerpo cuerpo {yyerror(" Falta la palabra reservada ELSE ",$1.getFila(),$1.getColumna());}
	;

sent_loop: loop_ cuerpo UNTIL condicion_salto {estructuras.add("Sentencia Loop " +" fila "+$1.getFila()+" columna "+$1.getColumna());}
	| loop_ cuerpo condicion_salto {yyerror("Linea  Falta palabra reservada UNTIL",$1.getFila(),$1.getColumna());}
	;

cuerpo: ejecutable {}
	| '{' lista_ejecutable '}'{}
	| error lista_ejecutable '}' {yyerror("LInea  Omision de la palabra reservada '{' ",$1.getFila(),$1.getColumna());}
	;

loop_: LOOP {//#### unica forma de marcar donde comienza el loop y ver donde salto (no diferenciamos bloque de loop)
        p.push(contadorTerceto);
                intLoop = contadorTerceto;
        Terceto t = new T_Fin(contadorTerceto,"FIN_DE_SALTO","trampita","trampita",st);

        listaTercetos.add(t);
        contadorTerceto ++;
        }
;


else_: ELSE {//#### aca hacemos el salto incondicional, debimos inventar este no terminal porque no diferenciamos bloque else de bloque if
        //aca ya hicimos el pop cuando termino el cuerpo del if
        Terceto t = new T_BI(contadorTerceto,"BI","trampita","trampita",st);
        contadorTerceto ++;
        listaTercetos.add(t);
        Integer i = p.pop();
        if (listaTercetos.get(i).getOperador() == "BF")
            listaTercetos.get(i).setOperando2(contadorTerceto);
        else
            listaTercetos.get(i).setOperando1(contadorTerceto);
        p.push(contadorTerceto-1);
//podriamos hacer un terceto fin aca que sea a donde apunte el salto, este terceto no haria nada solo funcionaria de label del salto
    System.out.println(t.toString());
    $$=$1;
    $$.obj = t;
															}
;

condicion_salto: '(' condicion ')' {    //#### aca hacemos lo del salto para no repetirlo en todas las condiciones
    p.push(contadorTerceto);
    Terceto t = new T_BF(contadorTerceto,"BF",$2.obj,"trampita",st);//##use trampita por las dudas, ya por deporte, no parece que sea necesario
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    $$=$1;
    $$.obj = t;

};

condicion: expresion '>' expresion {
  Terceto t = new T_Comparador(contadorTerceto,">",$1.obj,$3.obj,st);
   // t.setVariableAux(contadorVarAux);//revisar, creo que aca no va
   // contadorVarAux++;//osea una comparacion SI TIENE RESULTADO, pero no necesito el tipo del resultado?, nose
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    $$=$1;
    $$.obj = t;
    									}
	| expresion '<' expresion {
  Terceto t = new T_Comparador(contadorTerceto,"<",$1.obj,$3.obj,st);
   // t.setVariableAux(contadorVarAux);//revisar, creo que aca no va
   // contadorVarAux++;//osea una comparacion SI TIENE RESULTADO, pero no necesito el tipo del resultado?, nose
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    $$=$1;
    $$.obj = t;										}
	| expresion '=' expresion {  Terceto t = new T_Comparador(contadorTerceto,"=",$1.obj,$3.obj,st);
   // t.setVariableAux(contadorVarAux);//revisar, creo que aca no va
   // contadorVarAux++;//osea una comparacion SI TIENE RESULTADO, pero no necesito el tipo del resultado?, nose
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    $$=$1;
    $$.obj = t;										}
	| expresion DIST expresion {
  Terceto t = new T_Comparador(contadorTerceto,"!=",$1.obj,$3.obj,st);
   // t.setVariableAux(contadorVarAux);//revisar, creo que aca no va
   // contadorVarAux++;//osea una comparacion SI TIENE RESULTADO, pero no necesito el tipo del resultado?, nose
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    $$=$1;
    $$.obj = t;										}
	| expresion MAYIG expresion {
  Terceto t = new T_Comparador(contadorTerceto,">=",$1.obj,$3.obj,st);
   // t.setVariableAux(contadorVarAux);//revisar, creo que aca no va
   // contadorVarAux++;//osea una comparacion SI TIENE RESULTADO, pero no necesito el tipo del resultado?, nose
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    $$=$1;
    $$.obj = t;										}
	| expresion MENIG expresion {
  Terceto t = new T_Comparador(contadorTerceto,"<=",$1.obj,$3.obj,st);
   // t.setVariableAux(contadorVarAux);//revisar, creo que aca no va
   // contadorVarAux++;//osea una comparacion SI TIENE RESULTADO, pero no necesito el tipo del resultado?, nose
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),$1.getFila(),$1.getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    $$=$1;
    $$.obj = t;										}
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
  public ArrayList<Terceto> listaTercetos = new ArrayList<>();
  public Stack<Integer> p = new Stack<Integer>();
  int contadorVarAux=0;
  int contadorTerceto=0;
  int intLoop = 0;

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