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






//#line 4 "GramaticaGrupo8.y"
package AnalizadorSintactico;
import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;
import SymbolTable.*;
import Tercetos.*;
import java.util.ArrayList;
import java.util.Vector;
//#line 20 "GramaticaGrupo8.y"


  
//#line 29 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

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
public final static short DIST=267;
public final static short FIN=268;
public final static short SINGLE=269;
public final static short END_IF=270;
public final static short LOOP=271;
public final static short UNTIL=272;
public final static short LET=273;
public final static short MUT=274;
public final static short ENTERO=275;
public final static short FLOTANTE=276;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    2,    0,    1,    1,    3,    3,    5,    5,
    7,    7,    7,    7,    7,    6,    6,    6,    8,    8,
    4,    4,    4,   12,   12,   12,   13,   13,   13,   14,
   14,   14,   14,   14,    9,    9,    9,    9,    9,    9,
   11,   11,   10,   10,   15,   15,   15,   15,   15,   16,
   16,   18,   18,   18,   17,   17,   17,   17,   17,   17,
   17,   17,   17,   17,
};
final static short yylen[] = {                            2,
    1,    1,    0,    3,    1,    2,    1,    1,    5,    1,
    1,    2,    3,    4,    2,    1,    1,    2,    1,    2,
    2,    1,    2,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    2,    2,    3,    7,    5,    2,    2,    2,
    4,    2,    1,    1,    8,    6,    6,    3,    6,    6,
    5,    1,    3,    3,    3,    3,    3,    3,    3,    3,
    2,    2,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    2,    0,    0,    0,    0,
    1,    0,    7,    8,    0,   22,    0,   43,   44,    0,
    0,    0,   42,    0,   40,    0,   32,   30,   31,    0,
   38,    0,    0,    0,    0,    0,   52,    0,    0,   16,
   17,    0,    0,    0,    0,    0,    0,    0,    0,   10,
    6,   21,   23,    4,   48,    0,    0,   35,   33,   34,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   18,
    0,    0,    0,   63,   64,   61,   62,    0,    0,    0,
    0,    0,    0,    0,    0,   41,   24,   25,   28,   27,
   20,   54,   53,    0,    0,    0,    0,    0,    0,    0,
   59,   60,   58,   55,   56,   57,    0,    0,    0,   51,
    0,   15,    0,    9,   37,    0,    0,    0,   46,   49,
   50,   13,    0,    0,   47,    0,   14,   36,   45,
};
final static short yydgoto[] = {                         10,
   11,   20,   12,   37,   14,   43,   98,   66,   15,   16,
   17,   48,   32,   33,   18,   19,   49,   38,
};
final static short yysindex[] = {                       -22,
    0,  -33,   -9, -248,   14,    0,    7, -198,   22,    0,
    0,   20,    0,    0,  -27,    0,  -24,    0,    0, -256,
 -232,   22,    0, -228,    0,   14,    0,    0,    0, -224,
    0,   -7,  -14,  -21, -226,  -21,    0,  -31,    2,    0,
    0, -226,  -29,   14,   14,   14,   14,   29,   12,    0,
    0,    0,    0,    0,    0,   15,   33,    0,    0,    0,
   14,   14,   14,   14,  -21,  -81,  -55,   35,   22,    0,
  -17, -187, -182,    0,    0,    0,    0,   14,   14,   14,
   14,   14,   14,    7,    7,    0,    0,    0,    0,    0,
    0,    0,    0,   22,   44,  -32, -169,   49,   14, -170,
    0,    0,    0,    0,    0,    0, -163,  -40,   55,    0,
  -17,    0,   45,    0,    0,   65,    7,    7,    0,    0,
    0,    0,  -17, -156,    0, -164,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  107,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   64,    0,    0,    0,    0,
    0,  -12,  -39,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -16,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   66,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   67,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  100,    0,    0,    3,    0,   71,  -54,  -25,    0,    0,
    0,   19,    9,    0,    0,    0,   -8,  -30,
};
final static int YYTABLESIZE=298;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          9,
   10,   29,   13,   29,   29,   29,   22,   25,   69,   97,
   67,   54,   73,   56,   13,   26,   52,    9,    9,   53,
   29,   29,   29,   31,   97,   55,  111,   63,   26,   39,
   24,   26,   64,   40,   57,   61,   65,   62,   65,   91,
   10,  112,   41,   92,   58,   70,    9,   26,   26,   26,
   59,   60,   84,  107,  108,   85,  122,   39,   30,    9,
   95,   40,   74,   75,   76,   77,   30,   65,  127,   93,
   41,   89,   90,   86,   94,   42,   99,  120,  100,   87,
   88,   47,   36,   46,  110,  109,  125,  126,   82,   83,
   81,  113,  114,  116,  117,  121,  101,  102,  103,  104,
  105,  106,  124,  123,  128,  129,    5,   39,   19,   11,
   12,   51,   71,    0,    0,    0,    0,  115,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   36,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   34,    2,  118,    3,    0,
    4,    0,   21,    5,    0,   29,   29,   29,   96,  119,
    7,   72,   35,    1,    2,    2,    3,    3,    4,    4,
   68,    5,    5,   96,    0,    6,   23,    0,    7,    7,
    8,   35,   26,   26,   26,    0,   10,   10,    0,   10,
    0,   10,   34,    2,   10,    3,    0,    4,    3,    0,
    5,   10,    0,   10,   27,   50,    2,    7,    3,   35,
    4,    0,   27,    5,    0,    0,   44,   45,   28,   29,
    7,    0,    8,   78,   79,   80,   28,   29,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   41,    0,   43,   44,   45,   40,  256,   40,   42,
   36,  268,   42,   22,   12,  264,   44,   40,   40,   44,
   60,   61,   62,    5,   42,  258,   59,   42,   41,  256,
   40,   44,   47,  260,  263,   43,   34,   45,   36,   65,
   40,   96,  269,  125,   26,   44,   40,   60,   61,   62,
  275,  276,   41,   84,   85,   41,  111,  256,   45,   40,
   69,  260,   44,   45,   46,   47,   45,   65,  123,  125,
  269,   63,   64,   41,   40,  274,  264,  108,  261,   61,
   62,   60,  123,   62,   41,   94,  117,  118,   60,   61,
   62,  261,   44,  264,  258,   41,   78,   79,   80,   81,
   82,   83,   38,   59,  261,  270,    0,   44,  125,   44,
   44,   12,   42,   -1,   -1,   -1,   -1,   99,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,  258,  259,   -1,
  261,   -1,  256,  264,   -1,  265,  266,  267,  261,  270,
  271,  261,  273,  256,  257,  257,  259,  259,  261,  261,
  272,  264,  264,  261,   -1,  268,  256,   -1,  271,  271,
  273,  273,  265,  266,  267,   -1,  256,  257,   -1,  259,
   -1,  261,  256,  257,  264,  259,   -1,  261,  268,   -1,
  264,  271,   -1,  273,  261,  256,  257,  271,  259,  273,
  261,   -1,  261,  264,   -1,   -1,  265,  266,  275,  276,
  271,   -1,  273,  265,  266,  267,  275,  276,
};
}
final static short YYFINAL=10;
final static short YYMAXTOKEN=276;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"'&'",null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
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
"CTE","CADENA","ASIG","MAYIG","MENIG","DIST","FIN","SINGLE","END_IF","LOOP",
"UNTIL","LET","MUT","ENTERO","FLOTANTE",
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
"lista_id : '*' ID ';' lista_id",
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
"condicion : expresion '=' expresion",
"condicion : expresion DIST expresion",
"condicion : expresion MAYIG expresion",
"condicion : expresion MENIG expresion",
"condicion : '>' expresion",
"condicion : '<' expresion",
"condicion : MAYIG expresion",
"condicion : MENIG expresion",
};

//#line 450 "GramaticaGrupo8.y"

  LexicalAnalyzer lex;
  SymbolTable st;
  Errors errors;
  public ArrayList<String> estructuras=new ArrayList<>();
  public ArrayList<String> tokens = new ArrayList<>();
  public ArrayList<String> id = new ArrayList<>();
  public ArrayList<Terceto> listaTercetos = new ArrayList<>();
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
//#line 393 "Parser.java"
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
//#line 24 "GramaticaGrupo8.y"
{}
break;
case 2:
//#line 25 "GramaticaGrupo8.y"
{yyerror("No hay sentencia");}
break;
case 3:
//#line 26 "GramaticaGrupo8.y"
{yyerror("No hay sentencia");}
break;
case 4:
//#line 26 "GramaticaGrupo8.y"
{}
break;
case 5:
//#line 29 "GramaticaGrupo8.y"
{}
break;
case 6:
//#line 30 "GramaticaGrupo8.y"
{}
break;
case 7:
//#line 33 "GramaticaGrupo8.y"
{}
break;
case 8:
//#line 34 "GramaticaGrupo8.y"
{}
break;
case 9:
//#line 37 "GramaticaGrupo8.y"
{
                                            Vector<ParserVal> vectorTokens = (Vector<ParserVal>)(val_peek(1).obj);
                                            String tipo = (val_peek(2).sval);/* para que esto ande tocar la regla del no terminal tipo*/
                                            tipo = tipo.toLowerCase();
                                            for(int i=0; i< vectorTokens.size();i++){
                                                ParserVal token = vectorTokens.elementAt(i);
                                                Symbol simbolo =(Symbol) token.obj;
                                                if (!simbolo.isUsada()){
                                                    simbolo.setUsada(true);
                                                    simbolo.setEsMutable(true);
                                                    simbolo.setTipoVar(val_peek(2).sval);}
                                                else{/*se puede poner bien que variable es si id es una lista de parserVal*/
                                                 /*   yyerror("Variable ya definida ",$1.getFila(),$1.getColumna());*/
                                                }
                                                if (simbolo.getTipoVar()=="sin asignar")/*controlar de agregar este por defecto a symbol*/
                                                    simbolo.setTipoVar(tipo);
                                                else
                                                    yyerror("Se esta intentado redeclarar la variable "+simbolo.getLexema(),token.getFila(),token.getColumna());
                                                }

										}
break;
case 10:
//#line 58 "GramaticaGrupo8.y"
{yyerror("Declaracion mal definida ");}
break;
case 11:
//#line 61 "GramaticaGrupo8.y"
{/*  id.add( ((Symbol)($1.obj)).getLexema() );*/
                Vector<ParserVal> vect = new Vector<ParserVal>();/*$1 es el parser val con el symbolo de ese ID*/
                vect.add(val_peek(0));/*ver si anda, hay que castear a Symbol?*/
                yyval.obj = vect; }
break;
case 12:
//#line 65 "GramaticaGrupo8.y"
{	((Symbol)(val_peek(0).obj)).setEspuntero(true); /*reconoce puntero*/
				/*id.add(((Symbol)($2.obj)).getLexema());//} //agrega a lista de identificadores reconocidos*/
				Vector<ParserVal> vect = new Vector<ParserVal>();/*$2 es el parser val con el symbolo de ese ID*/
                vect.add(val_peek(0));/*ver si anda, hay que castear a Symbol? .obj*/
                yyval.obj = vect;
                                }
break;
case 13:
//#line 72 "GramaticaGrupo8.y"
{/*id.add(((Symbol)($1.obj)).getLexema());*/
                     Vector<ParserVal> vect = (Vector<ParserVal>)(val_peek(0).obj); /*$3 me trae el vector original primero y desp aumenta*/
                    vect.add(val_peek(2));/*ver si anda, hay que castear a Symbol? .obj*/
                    yyval.obj = vect;
	}
break;
case 14:
//#line 77 "GramaticaGrupo8.y"
{/*id.add(((Symbol)($2.obj)).getLexema());*/
                            ((Symbol)(val_peek(2).obj)).setEspuntero(true); /*reconoce puntero*/
                            Vector<ParserVal> vect = (Vector<ParserVal>)(val_peek(0).obj); /*$4 me trae el vector original primero y desp aumenta*/
                            vect.add(val_peek(2));/*ver si anda, hay que castear a Symbol? .obj*/
                            yyval.obj = vect;
	}
break;
case 15:
//#line 83 "GramaticaGrupo8.y"
{yyerror("Se esperaba ';' ",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 16:
//#line 86 "GramaticaGrupo8.y"
{yyval.sval="integer";}
break;
case 17:
//#line 87 "GramaticaGrupo8.y"
{yyval.sval="float";}
break;
case 18:
//#line 88 "GramaticaGrupo8.y"
{yyerror("Tipo indefinido",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 19:
//#line 91 "GramaticaGrupo8.y"
{}
break;
case 20:
//#line 92 "GramaticaGrupo8.y"
{}
break;
case 21:
//#line 95 "GramaticaGrupo8.y"
{}
break;
case 22:
//#line 96 "GramaticaGrupo8.y"
{}
break;
case 23:
//#line 97 "GramaticaGrupo8.y"
{}
break;
case 24:
//#line 100 "GramaticaGrupo8.y"
{/*if(!(((Symbol)($1.obj)).getTipoVar().equals(((Symbol)($3.obj)).getTipoVar()))){
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
								Terceto t = new T_Suma_Resta(contadorTerceto,"+",val_peek(2).obj,val_peek(0).obj,st);
                                /*st es la tabla de simbolos, paso lexema porque lo uso para buscar en la tabla de simbolos*/
                                /*contadorVarAux++;         por ahora no lo necesitamos*/
                                /*t.setVariableAux(contadorVarAux);*/
                                for(int i=0; i< t.errores.size();i++){
                                          yyerror(t.errores.elementAt(i),val_peek(2).getFila(),val_peek(2).getColumna());
                                      ;}
                                contadorTerceto ++;
                                listaTercetos.add(t);
                     System.out.println(t.toString());
                yyval=val_peek(2);
                yyval.obj = t;
}
break;
case 25:
//#line 124 "GramaticaGrupo8.y"
{/*
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
						     Terceto t = new T_Suma_Resta(contadorTerceto,"-",val_peek(2).obj,val_peek(0).obj,st);
                             /*st es la tabla de simbolos, paso lexema porque lo uso para buscar en la tabla de simbolos*/
                            /*contadorVarAux++;         por ahora no lo necesitamos*/
                            /*t.setVariableAux(contadorVarAux);*/
                            for(int i=0; i< t.errores.size();i++){
                                       yyerror(t.errores.elementAt(i),val_peek(2).getFila(),val_peek(2).getColumna());
                                   ;}
                            contadorTerceto ++;
                            listaTercetos.add(t);
                     System.out.println(t.toString());
                     yyval=val_peek(2);
            yyval.obj = t;
}
break;
case 26:
//#line 150 "GramaticaGrupo8.y"
{yyval=val_peek(0);
    yyval.obj=val_peek(0).obj; /*creo que es necesario para que no se pierdan los lexemas, si quieren reveanlo*/
}
break;
case 27:
//#line 156 "GramaticaGrupo8.y"
{/*
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
                Terceto t = new T_Mult_Div(contadorTerceto,"/",val_peek(2).obj,val_peek(0).obj,st);
                /*contadorVarAux++;         por ahora no lo necesitamos*/
                /*t.setVariableAux(contadorVarAux);*/
                for(int i=0; i< t.errores.size();i++){
                           yyerror(t.errores.elementAt(i),val_peek(2).getFila(),val_peek(2).getColumna());
                       ;}
                contadorTerceto ++;
                listaTercetos.add(t);
                     System.out.println(t.toString());
yyval=val_peek(2);
yyval.obj = t;

}
break;
case 28:
//#line 185 "GramaticaGrupo8.y"
{/*
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
$$=$1;
        $$.obj = t;
                        */
                Terceto t = new T_Mult_Div(contadorTerceto,"*",val_peek(2).obj,val_peek(0).obj,st);
                /*contadorVarAux++;         por ahora no lo necesitamos*/
                /*t.setVariableAux(contadorVarAux);*/
                for(int i=0; i< t.errores.size();i++){
                           yyerror(t.errores.elementAt(i),val_peek(2).getFila(),val_peek(2).getColumna());
                       ;}
                contadorTerceto ++;
                listaTercetos.add(t);
                     System.out.println(t.toString());
yyval=val_peek(2);
yyval.obj = t;
    }
break;
case 29:
//#line 213 "GramaticaGrupo8.y"
{yyval=val_peek(0);
			  /* terceto*/
			  yyval.obj=val_peek(0).obj;	
			 }
break;
case 30:
//#line 220 "GramaticaGrupo8.y"
{yyval=val_peek(0);}
break;
case 31:
//#line 221 "GramaticaGrupo8.y"
{yyval=val_peek(0);}
break;
case 32:
//#line 222 "GramaticaGrupo8.y"
{if(!((Symbol)(val_peek(0).obj)).isUsada()){
			/*error*/
			yyerror("variable no declarada",val_peek(0).getFila(),val_peek(0).getColumna());
			}
			 yyval=val_peek(0);
	}
break;
case 33:
//#line 228 "GramaticaGrupo8.y"
{    /** Revisar sino pierdo el puntero al elemento qe necesito **/
					  yyval=val_peek(0);
                      /*Symbol aux = st.getSymbol(lex.lastSymbol);*/
                      st.addcambiarSigno(((Symbol)(val_peek(0).obj)));  /*((Symbol))($2.obj))*/
 		              }
break;
case 34:
//#line 233 "GramaticaGrupo8.y"
{			/** Revisar sino pierdo el puntero al elemento qe necesito **/
		             yyval=val_peek(0);
					 /* Antes qedaban atributos sin setear*/
                    /* Symbol aux = st.getSymbol(lex.lastSymbol);*/
                     st.addcambiarSigno(((Symbol)(val_peek(0).obj)));  /*((Symbol))($2.obj))*/
                    }
break;
case 35:
//#line 241 "GramaticaGrupo8.y"
{		/*necesito el tipo de la expresion*/
if (!((Symbol)(val_peek(2).obj)).isUsada()){
    yyerror("La variable no esta definida ",val_peek(2).getFila(),val_peek(2).getColumna());
}else{if (!((Symbol)(val_peek(2).obj)).getEsMutable()){
    yyerror("La variable no es mutable ",val_peek(2).getFila(),val_peek(2).getColumna());
}else{if(!(((Symbol)(val_peek(2).obj)).getTipoVar().equals(val_peek(0).sval))){
    yyerror("Tipos incompatibles en la asignacion ",val_peek(2).getFila(),val_peek(2).getColumna());
									}}}
                Terceto t = new T_Asignacion(contadorTerceto,":=",val_peek(2).obj,val_peek(0).obj,st);
                /*contadorVarAux++;         por ahora no lo necesitamos*/
                /*t.setVariableAux(contadorVarAux);*/
                for(int i=0; i< t.errores.size();i++){
                           yyerror(t.errores.elementAt(i),val_peek(2).getFila(),val_peek(2).getColumna());
                       ;}
                contadorTerceto ++;
                listaTercetos.add(t);
                System.out.println(t.toString());
                yyval=val_peek(2);
                yyval.obj = t;
	estructuras.add("Asignacion "+" fila "+val_peek(2).getFila()+" columna "+val_peek(2).getColumna());}
break;
case 36:
//#line 262 "GramaticaGrupo8.y"
{ /* Estoy definiendo una variable*/
    if (((Symbol)(val_peek(3).obj)).isUsada()){
        yyerror("La variable ya esta definida ",val_peek(6).getFila(),val_peek(6).getColumna());
    }else{
        Symbol s = ((Symbol)(val_peek(3).obj));
        s.setUsada(true);
        s.setEsMutable(false);
        s.setEspuntero(true);
        s.setTipoVar(val_peek(5).sval);
        /* faltaria mutabilidad de lo apuntado*/
    }
    if (!((Symbol)(val_peek(0).obj)).isUsada()){
        yyerror("La variable no esta definida, &ID ",val_peek(6).getFila(),val_peek(6).getColumna());
    }else{
        Symbol s = ((Symbol)(val_peek(0).obj));
        Symbol sy = ((Symbol)(val_peek(3).obj));
        if (!(s.getTipoVar().equals(sy.getTipoVar()))){
            yyerror("incompatibilidad de tipos en la asignacion ",val_peek(6).getFila(),val_peek(6).getColumna());
        }
        if (s.isEsPuntero())
            yyerror("No se permiten punteros multiples ",val_peek(6).getFila(),val_peek(6).getColumna());
                                }
	  Terceto t = new T_Asignacion(contadorTerceto,":=",val_peek(3).obj,val_peek(0).obj,st);
    /*contadorVarAux++;         por ahora no lo necesitamos*/
    /*t.setVariableAux(contadorVarAux);*/
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),val_peek(6).getFila(),val_peek(6).getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    yyval=val_peek(6);
    yyval.obj = t;
									estructuras.add("Asignacion de puntero "+" fila "+val_peek(6).getFila()+" columna "+val_peek(6).getColumna());}
break;
case 37:
//#line 296 "GramaticaGrupo8.y"
{/*Estoy definiendo una variable*/
        if (((Symbol)(val_peek(2).obj)).isUsada()){
            yyerror("La variable ya esta definida ",val_peek(4).getFila(),val_peek(4).getColumna());
        }else{
            Symbol s = ((Symbol)(val_peek(2).obj));
            s.setUsada(true);
            s.setEsMutable(false);
            s.setEspuntero(false);
            s.setTipoVar(val_peek(3).sval);
            /* faltaria mutabilidad de lo apuntado*/
            if(!(s.getTipoVar().equals(val_peek(0).sval))){
            yyerror("Tipos incompatibles en la asignacion ",val_peek(4).getFila(),val_peek(4).getColumna());
        }
                                }
      Terceto t = new T_Asignacion(contadorTerceto,":=",val_peek(2).obj,val_peek(0).obj,st);
    /*contadorVarAux++;         por ahora no lo necesitamos*/
    /*t.setVariableAux(contadorVarAux);*/
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),val_peek(4).getFila(),val_peek(4).getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    yyval=val_peek(4);
    yyval.obj = t;

    estructuras.add("Asignacion "+" fila "+val_peek(4).getFila()+" columna "+val_peek(4).getColumna());}
break;
case 38:
//#line 323 "GramaticaGrupo8.y"
{yyerror("Falta elemento de asignacion y palabra reservada 'let'",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 39:
//#line 324 "GramaticaGrupo8.y"
{yyerror("Falta elemento de asignacion ",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 40:
//#line 325 "GramaticaGrupo8.y"
{yyerror("no se encontro ':=' ",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 41:
//#line 328 "GramaticaGrupo8.y"
{estructuras.add("Expresion print "+" fila "+val_peek(3).getFila()+" columna "+val_peek(3).getColumna());
  Terceto t = new T_Print(contadorTerceto,"PRINT",val_peek(1).obj,"",st);
                /*contadorVarAux++;         por ahora no lo necesitamos*/
                /*t.setVariableAux(contadorVarAux);*/
                for(int i=0; i< t.errores.size();i++){
                           yyerror(t.errores.elementAt(i),val_peek(3).getFila(),val_peek(3).getColumna());
                       ;}
                contadorTerceto ++;
                listaTercetos.add(t);
                System.out.println(t.toString());
                yyval=val_peek(3);
                yyval.obj = t;
}
break;
case 42:
//#line 341 "GramaticaGrupo8.y"
{yyerror("Linea  Error en la construccion del print",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 43:
//#line 344 "GramaticaGrupo8.y"
{}
break;
case 44:
//#line 345 "GramaticaGrupo8.y"
{}
break;
case 45:
//#line 349 "GramaticaGrupo8.y"
{estructuras.add("Sentencia IF Else" +" fila "+val_peek(7).getFila()+" columna "+val_peek(7).getColumna());}
break;
case 46:
//#line 350 "GramaticaGrupo8.y"
{estructuras.add("Sentencia IF " +" fila "+val_peek(5).getFila()+" columna "+val_peek(5).getColumna());}
break;
case 47:
//#line 351 "GramaticaGrupo8.y"
{yyerror(" falta la palabra reservada IF",val_peek(5).getFila(),val_peek(5).getColumna());}
break;
case 48:
//#line 352 "GramaticaGrupo8.y"
{yyerror(" Error en la construccion de la sentencia IF ",val_peek(2).getFila(),val_peek(2).getColumna());}
break;
case 49:
//#line 353 "GramaticaGrupo8.y"
{yyerror(" Falta la palabra reservada ELSE ",val_peek(5).getFila(),val_peek(5).getColumna());}
break;
case 50:
//#line 356 "GramaticaGrupo8.y"
{estructuras.add("Sentencia Loop " +" fila "+val_peek(5).getFila()+" columna "+val_peek(5).getColumna());}
break;
case 51:
//#line 357 "GramaticaGrupo8.y"
{yyerror("Linea  Falta palabra reservada UNTIL",val_peek(4).getFila(),val_peek(4).getColumna());}
break;
case 52:
//#line 360 "GramaticaGrupo8.y"
{}
break;
case 53:
//#line 361 "GramaticaGrupo8.y"
{}
break;
case 54:
//#line 362 "GramaticaGrupo8.y"
{yyerror("LInea  Omision de la palabra reservada '{' ",val_peek(2).getFila(),val_peek(2).getColumna());}
break;
case 55:
//#line 365 "GramaticaGrupo8.y"
{if(!(((Symbol)(val_peek(2).obj)).getTipoVar().equals(((Symbol)(val_peek(0).obj)).getTipoVar())))
										yyerror("tipos incompatibles ",val_peek(2).getFila(),val_peek(2).getColumna());
  Terceto t = new T_Comparador(contadorTerceto,">",val_peek(2).obj,val_peek(0).obj,st);
    /*contadorVarAux++;         por ahora no lo necesitamos*/
    /*t.setVariableAux(contadorVarAux);*/
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),val_peek(2).getFila(),val_peek(2).getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    yyval=val_peek(2);
    yyval.obj = t;										}
break;
case 56:
//#line 378 "GramaticaGrupo8.y"
{if(!(((Symbol)(val_peek(2).obj)).getTipoVar().equals(((Symbol)(val_peek(0).obj)).getTipoVar())))
										yyerror("tipos incompatibles ",val_peek(2).getFila(),val_peek(2).getColumna());
  Terceto t = new T_Comparador(contadorTerceto,"<",val_peek(2).obj,val_peek(0).obj,st);
    /*contadorVarAux++;         por ahora no lo necesitamos*/
    /*t.setVariableAux(contadorVarAux);*/
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),val_peek(2).getFila(),val_peek(2).getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    yyval=val_peek(2);
    yyval.obj = t;										}
break;
case 57:
//#line 391 "GramaticaGrupo8.y"
{if(!(((Symbol)(val_peek(2).obj)).getTipoVar().equals(((Symbol)(val_peek(0).obj)).getTipoVar())))
										yyerror("tipos incompatibles ",val_peek(2).getFila(),val_peek(2).getColumna());
  Terceto t = new T_Comparador(contadorTerceto,"=",val_peek(2).obj,val_peek(0).obj,st);
    /*contadorVarAux++;         por ahora no lo necesitamos*/
    /*t.setVariableAux(contadorVarAux);*/
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),val_peek(2).getFila(),val_peek(2).getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    yyval=val_peek(2);
    yyval.obj = t;										}
break;
case 58:
//#line 404 "GramaticaGrupo8.y"
{if(!(((Symbol)(val_peek(2).obj)).getTipoVar().equals(((Symbol)(val_peek(0).obj)).getTipoVar())))
										yyerror("tipos incompatibles ",val_peek(2).getFila(),val_peek(2).getColumna());
  Terceto t = new T_Comparador(contadorTerceto,"!=",val_peek(2).obj,val_peek(0).obj,st);
    /*contadorVarAux++;         por ahora no lo necesitamos*/
    /*t.setVariableAux(contadorVarAux);*/
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),val_peek(2).getFila(),val_peek(2).getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    yyval=val_peek(2);
    yyval.obj = t;										}
break;
case 59:
//#line 417 "GramaticaGrupo8.y"
{if(!(((Symbol)(val_peek(2).obj)).getTipoVar().equals(((Symbol)(val_peek(0).obj)).getTipoVar())))
										yyerror("tipos incompatibles ",val_peek(2).getFila(),val_peek(2).getColumna());
  Terceto t = new T_Comparador(contadorTerceto,">=",val_peek(2).obj,val_peek(0).obj,st);
    /*contadorVarAux++;         por ahora no lo necesitamos*/
    /*t.setVariableAux(contadorVarAux);*/
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),val_peek(2).getFila(),val_peek(2).getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    yyval=val_peek(2);
    yyval.obj = t;										}
break;
case 60:
//#line 430 "GramaticaGrupo8.y"
{if(!(((Symbol)(val_peek(2).obj)).getTipoVar().equals(((Symbol)(val_peek(0).obj)).getTipoVar())))
										yyerror("tipos incompatibles ",val_peek(2).getFila(),val_peek(2).getColumna());
  Terceto t = new T_Comparador(contadorTerceto,"<=",val_peek(2).obj,val_peek(0).obj,st);
    /*contadorVarAux++;         por ahora no lo necesitamos*/
    /*t.setVariableAux(contadorVarAux);*/
    for(int i=0; i< t.errores.size();i++){
               yyerror(t.errores.elementAt(i),val_peek(2).getFila(),val_peek(2).getColumna());
           ;}
    contadorTerceto ++;
    listaTercetos.add(t);
    System.out.println(t.toString());
    yyval=val_peek(2);
    yyval.obj = t;										}
break;
case 61:
//#line 443 "GramaticaGrupo8.y"
{yyerror("Linea  se esperaba una expresion y se encontro '>'",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 62:
//#line 444 "GramaticaGrupo8.y"
{yyerror("Linea  se esperaba una expresion y se encontro '<'",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 63:
//#line 445 "GramaticaGrupo8.y"
{yyerror("Linea  se esperaba una expresion y se encontro '>='",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
case 64:
//#line 446 "GramaticaGrupo8.y"
{yyerror("Linea  se esperaba una expresion y se encontro '<='",val_peek(1).getFila(),val_peek(1).getColumna());}
break;
//#line 1119 "Parser.java"
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
