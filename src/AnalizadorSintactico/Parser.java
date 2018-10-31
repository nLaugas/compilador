//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package AnalizadorSintactico;



//#line 3 "GramaticaGrupo8.y"

import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;
import SymbolTable.*;

import java.util.ArrayList;

//#line 13 "GramaticaGrupo8.y"

  
//#line 57 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

  LexicalAnalyzer lex;
  SymbolTable st;
  Errors errors;
  public ArrayList<String> estructuras=new ArrayList<>();
  public ArrayList<String> tokens = new ArrayList<>();

  int yylex(){
    /* yylval = lex.getVal();*/
/**Lo dejamos para despues!!!!**/
    /*Para mostrar los tokens encontrados*/
    int a = lex.getNextToken();
    /*    tokens.add(lex.yylval.toString()+" fila: "+lex.yylval.getFila()+" columna: "+lex.yylval.getColumna());*/

    if (lex.yylval != null){
      yylval = lex.yylval;
      lex.yylval = null;
    }else{
      yylval = new ParserVal();
    }
    tokens.add(yylval.toString()+" fila: "+yylval.getFila()+" columna: "+yylval.getColumna());
    return a;
  }

  public Parser(LexicalAnalyzer lex, SymbolTable st, Errors er)
  {
    this.lex = lex;
    this.st = st;
    this.errors=er;
    /*nothing to do*/
  }

  void yyerror(String s){
    errors.setError(lex.row, lex.column,s);
  }

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short ELSE=258;
public final static short PRINT=259;
public final static short INTEGER=260;
public final static short ID=261;
public final static short CTE=262;
public final static short CADENA=263;
public final static short ASIG=264;
public final static short MAYIG=265;
public final static short MENIG=266;
public final static short IGUAL=267;
public final static short DIST=268;
public final static short FIN=269;
public final static short SINGLE=270;
public final static short END_IF=271;
public final static short LOOP=272;
public final static short UNTIL=273;
public final static short LET=274;
public final static short MUT=275;
public final static short ENTERO=276;
public final static short FLOTANTE=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    2,    0,    1,    1,    3,    3,    5,    5,
    7,    7,    7,    7,    6,    6,    6,    8,    8,    4,
    4,    4,   12,   12,   12,   13,   13,   13,   14,   14,
   14,   14,   14,    9,    9,    9,    9,    9,    9,   11,
   11,   10,   10,   15,   15,   15,   15,   15,   16,   16,
   18,   18,   18,   17,   17,   17,   17,   17,   17,   17,
   17,   17,   17,
};
final static short yylen[] = {                            2,
    1,    1,    0,    3,    1,    2,    1,    1,    5,    1,
    1,    2,    3,    2,    1,    1,    2,    1,    2,    2,
    1,    2,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    2,    2,    3,    7,    5,    2,    2,    2,    4,
    2,    1,    1,    8,    6,    6,    3,    6,    6,    5,
    1,    3,    3,    3,    3,    3,    3,    3,    3,    2,
    2,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    2,    0,    0,    0,    0,
    1,    0,    7,    8,    0,   21,    0,   42,   43,    0,
    0,    0,   41,    0,   39,    0,   31,   29,   30,    0,
   37,    0,    0,    0,    0,    0,   51,    0,    0,   15,
   16,    0,    0,    0,    0,    0,    0,    0,    0,   10,
    6,   20,   22,    4,   47,    0,    0,   34,   32,   33,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   17,
    0,    0,    0,   62,   63,   60,   61,    0,    0,    0,
    0,    0,    0,    0,    0,   40,   23,   24,   27,   26,
   19,   53,   52,    0,    0,    0,    0,    0,    0,    0,
   58,   59,   56,   57,   54,   55,    0,    0,    0,   50,
    0,   14,   12,    9,   36,    0,    0,    0,   45,   48,
   49,   13,    0,   46,    0,   35,   44,
};
final static short yydgoto[] = {                         10,
   11,   20,   12,   37,   14,   43,   98,   66,   15,   16,
   17,   48,   32,   33,   18,   19,   49,   38,
};
final static short yysindex[] = {                       -34,
    0,  -36,  -13, -231,    6,    0,  -20, -238,   11,    0,
    0,  -11,    0,    0,   -6,    0,   -5,    0,    0, -250,
 -230,   11,    0, -220,    0,    6,    0,    0,    0, -261,
    0,    5,  -16,    7, -226,    7,    0,  -31,    8,    0,
    0, -226,  -32,    6,    6,    6,    6,  -57,   17,    0,
    0,    0,    0,    0,    0,   25,   29,    0,    0,    0,
    6,    6,    6,    6,    7,  -76,  -61,   34,   11,    0,
  -28, -188, -182,    0,    0,    0,    0,    6,    6,    6,
    6,    6,    6,  -20,  -20,    0,    0,    0,    0,    0,
    0,    0,    0,   11,   40,  -35, -179,   41,    6, -177,
    0,    0,    0,    0,    0,    0, -169,  -40,   49,    0,
  -28,    0,    0,    0,    0,   54,  -20,  -20,    0,    0,
    0,    0, -161,    0, -170,    0,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  102,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   60,    0,    0,    0,    0,
    0,   31,   24,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -19,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   61,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   95,    0,    0,   23,    0,   66,  -94,  -25,    0,    0,
    0,   16,  -10,    0,    0,    0,  -14,  -72,
};
final static int YYTABLESIZE=299;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          9,
   10,  112,   83,   22,   82,    9,   97,   56,   69,   73,
   67,  107,  108,   97,   59,   60,  122,   39,   54,    9,
   31,   40,   13,  111,   25,   63,   24,   55,    9,   39,
   64,   41,   26,   40,   13,  120,   42,   52,   53,   91,
   10,   58,   57,   41,  124,  125,    9,   61,   92,   62,
   30,   70,   89,   90,   95,   30,   65,   84,   65,   74,
   75,   76,   77,   93,   28,   85,   28,   28,   28,   86,
   47,   25,   46,   94,   25,   99,   87,   88,  100,  109,
  110,  113,   36,   28,  114,   28,  116,   65,  117,  121,
   25,  123,   25,  101,  102,  103,  104,  105,  106,  126,
  127,    5,   36,   38,   11,   18,   51,   71,    0,    0,
    0,    0,    0,    0,  115,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   78,   79,   80,
   81,    0,    0,    0,    0,   34,    2,  118,    3,   21,
    4,    1,    2,    5,    3,   96,    4,    0,   72,    5,
  119,    7,   96,   35,    6,   34,    2,    7,    3,    8,
    4,   68,   23,    5,   50,    2,    0,    3,    0,    4,
    0,    7,    5,   35,    0,    0,   10,   10,    0,   10,
    7,   10,    8,    2,   10,    3,   27,    4,    0,    3,
    5,   27,   10,    0,   10,   44,   45,    0,    7,    0,
   35,   28,   29,    0,    0,    0,   28,   29,   28,   28,
   28,   28,    0,    0,    0,   25,   25,   25,   25,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   96,   60,   40,   62,   40,   42,   22,   40,   42,
   36,   84,   85,   42,  276,  277,  111,  256,  269,   40,
    5,  260,    0,   59,  256,   42,   40,  258,   40,  256,
   47,  270,  264,  260,   12,  108,  275,   44,   44,   65,
   40,   26,  263,  270,  117,  118,   40,   43,  125,   45,
   45,   44,   63,   64,   69,   45,   34,   41,   36,   44,
   45,   46,   47,  125,   41,   41,   43,   44,   45,   41,
   60,   41,   62,   40,   44,  264,   61,   62,  261,   94,
   41,  261,  123,   60,   44,   62,  264,   65,  258,   41,
   60,   38,   62,   78,   79,   80,   81,   82,   83,  261,
  271,    0,  123,   44,   44,  125,   12,   42,   -1,   -1,
   -1,   -1,   -1,   -1,   99,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,  266,  267,
  268,   -1,   -1,   -1,   -1,  256,  257,  258,  259,  256,
  261,  256,  257,  264,  259,  261,  261,   -1,  261,  264,
  271,  272,  261,  274,  269,  256,  257,  272,  259,  274,
  261,  273,  256,  264,  256,  257,   -1,  259,   -1,  261,
   -1,  272,  264,  274,   -1,   -1,  256,  257,   -1,  259,
  272,  261,  274,  257,  264,  259,  261,  261,   -1,  269,
  264,  261,  272,   -1,  274,  265,  266,   -1,  272,   -1,
  274,  276,  277,   -1,   -1,   -1,  276,  277,  265,  266,
  267,  268,   -1,   -1,   -1,  265,  266,  267,  268,
};
}
final static short YYFINAL=10;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"'&'",null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"IF","ELSE","PRINT","INTEGER","ID",
"CTE","CADENA","ASIG","MAYIG","MENIG","IGUAL","DIST","FIN","SINGLE","END_IF",
"LOOP","UNTIL","LET","MUT","ENTERO","FLOTANTE",
};
final static String yyrule[] = {
"$accept : programa",
"programa : lista_sentencia",
"programa : FIN",
"$$1 :",
"programa : error $$1 FIN",
"lista_sentencia : sentencia",
"lista_sentencia : sentencia lista_sentencia",
"sentencia : ejecutable",
"sentencia : declaracion",
"declaracion : LET MUT tipo lista_id ','",
"declaracion : error",
"lista_id : ID",
"lista_id : '*' ID",
"lista_id : ID ';' lista_id",
"lista_id : ID lista_id",
"tipo : INTEGER",
"tipo : SINGLE",
"tipo : error ','",
"lista_ejecutable : ejecutable",
"lista_ejecutable : ejecutable lista_ejecutable",
"ejecutable : asignacion ','",
"ejecutable : bloque",
"ejecutable : exp_print ','",
"expresion : termino '+' expresion",
"expresion : termino '-' expresion",
"expresion : termino",
"termino : factor '/' termino",
"termino : factor '*' termino",
"termino : factor",
"factor : ENTERO",
"factor : FLOTANTE",
"factor : ID",
"factor : '-' ENTERO",
"factor : '-' FLOTANTE",
"asignacion : ID ASIG expresion",
"asignacion : LET tipo '*' ID ASIG '&' ID",
"asignacion : LET tipo ID ASIG expresion",
"asignacion : ASIG expresion",
"asignacion : ID ASIG",
"asignacion : ID error",
"exp_print : PRINT '(' CADENA ')'",
"exp_print : PRINT error",
"bloque : sent_if",
"bloque : sent_loop",
"sent_if : IF '(' condicion ')' cuerpo ELSE cuerpo END_IF",
"sent_if : IF '(' condicion ')' cuerpo END_IF",
"sent_if : '(' condicion ')' cuerpo ELSE cuerpo",
"sent_if : IF error ELSE",
"sent_if : IF '(' condicion ')' cuerpo cuerpo",
"sent_loop : LOOP cuerpo UNTIL '(' condicion ')'",
"sent_loop : LOOP cuerpo '(' condicion ')'",
"cuerpo : ejecutable",
"cuerpo : '{' lista_ejecutable '}'",
"cuerpo : error lista_ejecutable '}'",
"condicion : expresion '>' expresion",
"condicion : expresion '<' expresion",
"condicion : expresion IGUAL expresion",
"condicion : expresion DIST expresion",
"condicion : expresion MAYIG expresion",
"condicion : expresion MENIG expresion",
"condicion : '>' expresion",
"condicion : '<' expresion",
"condicion : MAYIG expresion",
"condicion : MENIG expresion",
};

//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 51 "GramaticaGrupo8.y"
{}
break;
case 2:
//#line 52 "GramaticaGrupo8.y"
{yyerror("No hay sentencia");}
break;
case 3:
//#line 53 "GramaticaGrupo8.y"
{yyerror("No hay sentencia");}
break;
case 4:
//#line 53 "GramaticaGrupo8.y"
{}
break;
case 5:
//#line 56 "GramaticaGrupo8.y"
{}
break;
case 6:
//#line 57 "GramaticaGrupo8.y"
{}
break;
case 7:
//#line 60 "GramaticaGrupo8.y"
{}
break;
case 8:
//#line 61 "GramaticaGrupo8.y"
{}
break;
case 9:
//#line 64 "GramaticaGrupo8.y"
{}
break;
case 10:
//#line 65 "GramaticaGrupo8.y"
{yyerror("Declaracion mal definida ");}
break;
case 11:
//#line 68 "GramaticaGrupo8.y"
{}
break;
case 12:
//#line 69 "GramaticaGrupo8.y"
{}
break;
case 13:
//#line 70 "GramaticaGrupo8.y"
{}
break;
case 14:
//#line 71 "GramaticaGrupo8.y"
{yyerror("Se esperaba ';' ");}
break;
case 15:
//#line 74 "GramaticaGrupo8.y"
{}
break;
case 16:
//#line 75 "GramaticaGrupo8.y"
{}
break;
case 17:
//#line 76 "GramaticaGrupo8.y"
{yyerror("Tipo indefinido");}
break;
case 18:
//#line 79 "GramaticaGrupo8.y"
{}
break;
case 19:
//#line 80 "GramaticaGrupo8.y"
{}
break;
case 20:
//#line 83 "GramaticaGrupo8.y"
{}
break;
case 21:
//#line 84 "GramaticaGrupo8.y"
{}
break;
case 22:
//#line 85 "GramaticaGrupo8.y"
{}
break;
case 23:
//#line 88 "GramaticaGrupo8.y"
{}
break;
case 24:
//#line 89 "GramaticaGrupo8.y"
{}
break;
case 25:
//#line 90 "GramaticaGrupo8.y"
{}
break;
case 26:
//#line 93 "GramaticaGrupo8.y"
{}
break;
case 27:
//#line 94 "GramaticaGrupo8.y"
{}
break;
case 28:
//#line 95 "GramaticaGrupo8.y"
{}
break;
case 29:
//#line 98 "GramaticaGrupo8.y"
{}
break;
case 30:
//#line 99 "GramaticaGrupo8.y"
{}
break;
case 31:
//#line 100 "GramaticaGrupo8.y"
{}
break;
case 32:
//#line 101 "GramaticaGrupo8.y"
{
                      Symbol aux = st.getSymbol(lex.lastSymbol);
                      st.addcambiarSigno(aux);
                     
 		              }
break;
case 33:
//#line 106 "GramaticaGrupo8.y"
{
                     Symbol aux = st.getSymbol(lex.lastSymbol);
                     st.addcambiarSigno(aux);
                    
                    }
break;
case 34:
//#line 113 "GramaticaGrupo8.y"
{estructuras.add("Asignacion "+" fila "+val_peek(2).getFila()+" columna "+val_peek(2).getColumna());}
break;
case 35:
//#line 114 "GramaticaGrupo8.y"
{estructuras.add("Asignacion de puntero "+" fila "+val_peek(6).getFila()+" columna "+val_peek(6).getColumna());}
break;
case 36:
//#line 115 "GramaticaGrupo8.y"
{estructuras.add("Asignacion "+" fila "+val_peek(4).getFila()+" columna "+val_peek(4).getColumna());}
break;
case 37:
//#line 116 "GramaticaGrupo8.y"
{yyerror("Falta elemento de asignacion y palabra reservada 'let'");}
break;
case 38:
//#line 117 "GramaticaGrupo8.y"
{yyerror("Falta elemento de asignacion ");}
break;
case 39:
//#line 118 "GramaticaGrupo8.y"
{yyerror("no se encontro ':=' ");}
break;
case 40:
//#line 121 "GramaticaGrupo8.y"
{estructuras.add("Expresion print "+" fila "+val_peek(3).getFila()+" columna "+val_peek(3).getColumna());}
break;
case 41:
//#line 122 "GramaticaGrupo8.y"
{yyerror("Linea  Error en la construccion del print");}
break;
case 42:
//#line 125 "GramaticaGrupo8.y"
{estructuras.add("Sentencia IF " +" fila "+val_peek(0).getFila()+" columna "+val_peek(0).getColumna());}
break;
case 43:
//#line 126 "GramaticaGrupo8.y"
{estructuras.add("Sentencia Loop " +" fila "+val_peek(0).getFila()+" columna "+val_peek(0).getColumna());}
break;
case 44:
//#line 130 "GramaticaGrupo8.y"
{}
break;
case 45:
//#line 131 "GramaticaGrupo8.y"
{}
break;
case 46:
//#line 132 "GramaticaGrupo8.y"
{yyerror(" falta la palabra reservada IF");}
break;
case 47:
//#line 133 "GramaticaGrupo8.y"
{yyerror(" Error en la construccion de la sentencia IF ");}
break;
case 48:
//#line 134 "GramaticaGrupo8.y"
{yyerror(" Falta la palabra reservada ELSE ");}
break;
case 49:
//#line 137 "GramaticaGrupo8.y"
{}
break;
case 50:
//#line 138 "GramaticaGrupo8.y"
{yyerror("Linea  Falta palabra reservada UNTIL");}
break;
case 51:
//#line 141 "GramaticaGrupo8.y"
{}
break;
case 52:
//#line 142 "GramaticaGrupo8.y"
{}
break;
case 53:
//#line 143 "GramaticaGrupo8.y"
{yyerror("LInea  Omision de la palabra reservada '{' ");}
break;
case 54:
//#line 146 "GramaticaGrupo8.y"
{}
break;
case 55:
//#line 147 "GramaticaGrupo8.y"
{}
break;
case 56:
//#line 148 "GramaticaGrupo8.y"
{}
break;
case 57:
//#line 149 "GramaticaGrupo8.y"
{}
break;
case 58:
//#line 150 "GramaticaGrupo8.y"
{}
break;
case 59:
//#line 151 "GramaticaGrupo8.y"
{}
break;
case 60:
//#line 152 "GramaticaGrupo8.y"
{yyerror("Linea  se esperaba una expresion y se encontro '>'");}
break;
case 61:
//#line 153 "GramaticaGrupo8.y"
{yyerror("Linea  se esperaba una expresion y se encontro '<'");}
break;
case 62:
//#line 154 "GramaticaGrupo8.y"
{yyerror("Linea  se esperaba una expresion y se encontro '>='");}
break;
case 63:
//#line 155 "GramaticaGrupo8.y"
{yyerror("Linea  se esperaba una expresion y se encontro '<='");}
break;
//#line 790 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
