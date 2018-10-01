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



//#line 3 "Gramática.y"
 package AnalizadorSintactico;
//#line 19 "Parser.java"


import AnalizadorLexico.LexicalAnalyzer;
import Errors.Errors;
import SymbolTable.*;

public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character
  LexicalAnalyzer lex;
  SymbolTable st;
  Errors errors;

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
    7,    7,    7,    6,    6,    6,    8,    8,    4,    4,
    4,   12,   12,   12,   13,   13,   13,   14,   14,   14,
   14,   14,    9,    9,    9,    9,   11,   11,   10,   10,
   15,   15,   15,   15,   15,   15,   15,   16,   16,   18,
   18,   18,   17,   17,   17,   17,   17,   17,   17,   17,
   17,   17,   17,   17,
};
final static short yylen[] = {                            2,
    1,    1,    0,    3,    1,    2,    1,    1,    3,    1,
    1,    3,    2,    1,    1,    2,    1,    2,    2,    1,
    2,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    2,    2,    3,    2,    2,    2,    4,    2,    1,    1,
    8,    6,    6,    3,    6,    5,    5,    6,    5,    1,
    3,    3,    3,    3,    3,    3,    3,    3,    2,    2,
    2,    2,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   14,    0,    0,    2,   15,    0,    0,
    0,    1,    0,    7,    8,    0,    0,   20,    0,   39,
   40,   16,    0,    0,   30,    0,    0,    0,    0,   28,
   29,    0,    0,    0,    0,    0,    0,    0,    0,   38,
    0,   36,    0,   34,    0,    0,   50,    0,    0,    0,
    6,    0,    0,   19,   21,    4,   44,   63,   64,   61,
   62,   31,   32,    0,   59,   60,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   33,    0,
    0,    0,    0,    0,    0,    0,   13,    9,    0,    0,
   57,   58,   55,   56,   53,   54,   22,   23,   25,   26,
    0,   37,   18,   52,   51,    0,    0,    0,   12,    0,
   47,   46,    0,   49,    0,    0,   42,   45,   48,   43,
    0,   41,
};
final static short yydgoto[] = {                         11,
   12,   23,   13,   47,   15,   16,   53,   81,   17,   18,
   19,   36,   37,   38,   20,   21,   39,   48,
};
final static short yysindex[] = {                        42,
  -32,  -40,  -37,    0, -247,  -31,    0,    0,  108,   52,
    0,    0,   81,    0,    0, -242,  -23,    0,  -19,    0,
    0,    0, -235, -221,    0,  -31,  -31,  -31,  -31,    0,
    0, -244,   52,  -31,  -31,  -56,  -30,  -11,   -2,    0,
 -225,    0,  -31,    0,   -5,   -5,    0,  -38,   -1,  -32,
    0,  -48,    4,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   99,    0,    0,  -31,  -31,  -31,  -31,
  -31,  -31,  -31,  -31,  -31,  -31,  108,    8,    0,   -5,
  -75,  -74,   12,   52,  108, -242,    0,    0,  108,  108,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  108,    0,    0,    0,    0,   52,   13, -205,    0,   75,
    0,    0,   14,    0,  108,  108,    0,    0,    0,    0,
 -215,    0,
};
final static short yyrindex[] = {                         0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   59,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   28,  -17,    0,    0,
    0,    0,   20,    0,    0,    0,    0,    0,    0,   18,
    0,   21,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -68,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
   47,    0,    0,   16,    0,    0,  -44,  -36,    0,    0,
    0,   57,  -29,    0,    0,    0,   -3,   56,
};
final static int YYTABLESIZE=380;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         33,
   10,   84,   41,   72,   32,   71,   49,   87,   42,   82,
   86,   22,   73,   32,   74,   14,   43,   10,   52,   35,
   54,   34,   27,   27,   55,   27,   27,   27,   14,   64,
   76,   62,   63,   56,   10,   75,   57,   78,   77,   85,
   10,  109,   27,  103,   27,   99,  100,   88,  102,  104,
  105,  106,  115,  114,  119,  122,   17,   10,    5,   51,
   80,   80,   44,   35,   11,    0,    0,   24,   24,    0,
    0,   24,    0,    0,    0,    0,    0,    0,    0,    0,
  107,   10,   58,   59,   60,   61,    0,   24,    0,   24,
   65,   66,    0,    0,    0,   80,   32,    0,    0,   79,
    0,    0,  113,    0,    0,   27,    0,    0,    0,    0,
    0,   35,    0,   34,   10,    0,    0,    0,    0,   90,
   10,    0,    0,   91,   92,   93,   94,   95,   96,   97,
   98,    0,  101,    0,    0,    0,    0,    0,   10,   89,
  108,    0,    0,    0,  110,  111,    0,   10,    0,    0,
   24,    0,    0,    0,    0,    0,  112,    0,    0,    0,
    0,    0,    0,    0,    0,  118,    0,    0,    0,    0,
  120,  121,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   46,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   67,   68,
   69,   70,   52,    0,    0,   24,    0,    0,   40,    0,
   25,   46,    0,    0,   26,   27,   28,   29,    0,   25,
   46,    0,    0,    0,   83,   30,   31,    0,   27,   27,
    0,   27,    0,   27,   30,   31,   27,   27,   27,   27,
   27,    2,    0,    3,   27,    5,   10,   10,    6,   10,
   10,   10,    0,    0,   10,    0,    9,    0,    0,    3,
   10,    0,   10,   10,   10,    0,   10,   10,   10,    0,
    0,   10,    0,   24,   24,    0,   24,   10,   24,   10,
    0,   24,   24,   24,   24,   24,    0,    1,    2,   24,
    3,    4,    5,    0,    0,    6,    0,    0,    0,    0,
    7,    8,   25,    9,    0,    0,   26,   27,   28,   29,
    0,    0,    0,    0,    0,    0,    0,   30,   31,    0,
   45,    2,  116,    3,    0,    5,   50,    2,    6,    3,
    4,    5,    0,    0,    6,  117,    9,    0,    0,    0,
    8,    0,    9,    0,   45,    2,    0,    3,    0,    5,
    0,    0,    6,   45,    2,    0,    3,    0,    5,    0,
    9,    6,    0,    0,    0,    0,    0,    0,    0,    9,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   40,   60,   45,   62,   10,   52,  256,   46,
   59,   44,   43,   45,   45,    0,  264,    0,  261,   60,
   44,   62,   40,   41,   44,   43,   44,   45,   13,   33,
   42,  276,  277,  269,   40,   47,  258,  263,   41,   41,
   40,   86,   60,   80,   62,   75,   76,   44,   41,  125,
  125,   40,  258,   41,   41,  271,  125,   40,    0,   13,
   45,   46,    6,   44,   44,   -1,   -1,   40,   41,   -1,
   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   84,   40,   26,   27,   28,   29,   -1,   60,   -1,   62,
   34,   35,   -1,   -1,   -1,   80,   45,   -1,   -1,   43,
   -1,   -1,  106,   -1,   -1,  123,   -1,   -1,   -1,   -1,
   -1,   60,   -1,   62,   40,   -1,   -1,   -1,   -1,   64,
   40,   -1,   -1,   67,   68,   69,   70,   71,   72,   73,
   74,   -1,   77,   -1,   -1,   -1,   -1,   -1,   40,   41,
   85,   -1,   -1,   -1,   89,   90,   -1,   40,   -1,   -1,
  123,   -1,   -1,   -1,   -1,   -1,  101,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  110,   -1,   -1,   -1,   -1,
  115,  116,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,  266,
  267,  268,  261,   -1,   -1,  256,   -1,   -1,  256,   -1,
  261,  123,   -1,   -1,  265,  266,  267,  268,   -1,  261,
  123,   -1,   -1,   -1,  273,  276,  277,   -1,  256,  257,
   -1,  259,   -1,  261,  276,  277,  264,  265,  266,  267,
  268,  257,   -1,  259,  272,  261,  256,  257,  264,  259,
  260,  261,   -1,   -1,  264,   -1,  272,   -1,   -1,  269,
  270,   -1,  272,  256,  257,   -1,  259,  260,  261,   -1,
   -1,  264,   -1,  256,  257,   -1,  259,  270,  261,  272,
   -1,  264,  265,  266,  267,  268,   -1,  256,  257,  272,
  259,  260,  261,   -1,   -1,  264,   -1,   -1,   -1,   -1,
  269,  270,  261,  272,   -1,   -1,  265,  266,  267,  268,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  276,  277,   -1,
  256,  257,  258,  259,   -1,  261,  256,  257,  264,  259,
  260,  261,   -1,   -1,  264,  271,  272,   -1,   -1,   -1,
  270,   -1,  272,   -1,  256,  257,   -1,  259,   -1,  261,
   -1,   -1,  264,  256,  257,   -1,  259,   -1,  261,   -1,
  272,  264,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  272,
};
}
final static short YYFINAL=11;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","ELSE","PRINT","INTEGER","ID","CTE",
"CADENA","ASIG","MAYIG","MENIG","IGUAL","DIST","FIN","SINGLE","END_IF","LOOP",
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
"declaracion : tipo lista_id ','",
"declaracion : error",
"lista_id : ID",
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
"sent_if : IF condicion ')' cuerpo cuerpo",
"sent_if : IF '(' condicion cuerpo cuerpo",
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
"condicion : IGUAL expresion",
"condicion : DIST expresion",
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


  int yylex(){
   // yylval = lex.getVal();
/**Lo dejamos para despues!!!!**/
    //Para mostrar los tokens encontrados
    int a = lex.getNextToken();
    System.out.print("Token encontrado: ");
    System.out.println(a);
    //

    return a;
  }



//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string

  void yyerror(String s){
    errors.setError(lex.row, lex.column,s);
  }


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
//#line 14 "Gramática.y"
{}
break;
case 2:
//#line 15 "Gramática.y"
{yyerror("LInea  Se esperaba una sentencia");}
break;
case 3:
//#line 16 "Gramática.y"
{yyerror("LInea  Se esperaba una sentencia");}
break;
case 4:
//#line 16 "Gramática.y"
{}
break;
case 5:
//#line 19 "Gramática.y"
{}
break;
case 6:
//#line 20 "Gramática.y"
{}
break;
case 7:
//#line 23 "Gramática.y"
{System.out.println("Programa");}
break;
case 8:
//#line 24 "Gramática.y"
{System.out.println("Programa");}
break;
case 9:
//#line 27 "Gramática.y"
{}
break;
case 10:
//#line 28 "Gramática.y"
{yyerror("Declaracion mal definida ");}
break;
case 11:
//#line 31 "Gramática.y"
{}
break;
case 12:
//#line 32 "Gramática.y"
{}
break;
case 13:
//#line 33 "Gramática.y"
{yyerror("LInea  Se esperaba el caracter de separacion de identificadores ';' ");}
break;
case 14:
//#line 36 "Gramática.y"
{}
break;
case 15:
//#line 37 "Gramática.y"
{}
break;
case 16:
//#line 38 "Gramática.y"
{yyerror("LInea  tipo indefinido");}
break;
case 17:
//#line 41 "Gramática.y"
{}
break;
case 18:
//#line 42 "Gramática.y"
{}
break;
case 19:
//#line 45 "Gramática.y"
{}
break;
case 20:
//#line 46 "Gramática.y"
{}
break;
case 21:
//#line 47 "Gramática.y"
{}
break;
case 22:
//#line 50 "Gramática.y"
{System.out.println("Expresion suma");}
break;
case 23:
//#line 51 "Gramática.y"
{System.out.println("Expresion resta");}
break;
case 24:
//#line 52 "Gramática.y"
{}
break;
case 25:
//#line 55 "Gramática.y"
{System.out.println("Expresion division");}
break;
case 26:
//#line 56 "Gramática.y"
{System.out.println("Expresion producto");}
break;
case 27:
//#line 57 "Gramática.y"
{System.out.println("Factor");}
break;
case 28:
//#line 60 "Gramática.y"
{/*Compilador.tablaSimbolos.get(yylval.sval).setTipoDef('P');*/
             System.out.println("Constante");}
break;
case 30:
//#line 63 "Gramática.y"
{}
break;
case 31:
//#line 65 "Gramática.y"
{
  Symbol aux = st.getSymbol(lex.lastSymbol);
   st.addcambiarSigno(aux);
   System.out.println("Constante negativa");}
break;

  case 32:
//#line 84 "Gramática.y"
  {
    Symbol aux = st.getSymbol(lex.lastSymbol);
    st.addcambiarSigno(aux);
    System.out.println("Constante negativa");}
  break;
case 33:
//#line 87 "Gramática.y"
{System.out.println("Asignacion");}
break;
case 34:
//#line 88 "Gramática.y"
{yyerror("LInea  Se esperaba un identificador y se encontro ':'");}
break;
case 35:
//#line 89 "Gramática.y"
{yyerror("LInea  Se esperaba una expresion y se encontro ';' ");}
break;
case 36:
//#line 90 "Gramática.y"
{yyerror("LInea  no es ':=' ");}
break;
case 37:
//#line 93 "Gramática.y"
{System.out.println("Expresion print");}
break;
case 38:
//#line 94 "Gramática.y"
{yyerror("Linea  Error en la construccion del print");}
break;
case 39:
//#line 97 "Gramática.y"
{System.out.println("Sentencia IF");}
break;
case 40:
//#line 98 "Gramática.y"
{System.out.println("Sentencia loop");}
break;
case 41:
//#line 102 "Gramática.y"
{}
break;
case 42:
//#line 103 "Gramática.y"
{}
break;
case 43:
//#line 104 "Gramática.y"
{yyerror(" falta la palabra reservada IF");}
break;
case 44:
//#line 105 "Gramática.y"
{yyerror(" Error en la construccion de la sentencia IF ");}
break;
case 45:
//#line 106 "Gramática.y"
{yyerror(" Falta la palabra reservada ELSE ");}
break;
case 46:
//#line 107 "Gramática.y"
{yyerror(" Omision del ( .Falta la palabra reservada ELSE ");}
break;
case 47:
//#line 108 "Gramática.y"
{yyerror(" Omision del ) .Falta la palabra reservada ELSE ");}
break;
case 48:
//#line 111 "Gramática.y"
{}
break;
case 49:
//#line 112 "Gramática.y"
{yyerror("Linea  Falta palabra reservada UNTIL");}
break;
case 50:
//#line 115 "Gramática.y"
{}
break;
case 51:
//#line 116 "Gramática.y"
{}
break;
case 52:
//#line 117 "Gramática.y"
{yyerror("LInea  Omision de la palabra reservada '{' ");}
break;
case 53:
//#line 120 "Gramática.y"
{System.out.println("Expresion mayor");}
break;
case 54:
//#line 121 "Gramática.y"
{System.out.println("Expresion menor");}
break;
case 55:
//#line 122 "Gramática.y"
{System.out.println("Expresion igual");}
break;
case 56:
//#line 123 "Gramática.y"
{System.out.println("Expresion distinto");}
break;
case 57:
//#line 124 "Gramática.y"
{System.out.println("Expresion mayor o igual");}
break;
case 58:
//#line 125 "Gramática.y"
{System.out.println("Expresion menor o igual");}
break;
case 59:
//#line 126 "Gramática.y"
{yyerror("LInea  se esperaba una expresion y se encontro '>'");}
break;
case 60:
//#line 127 "Gramática.y"
{yyerror("LInea  se esperaba una expresion y se encontro '<'");}
break;
case 61:
//#line 128 "Gramática.y"
{yyerror("LInea  se esperaba una expresion y se encontro '=='");}
break;
case 62:
//#line 129 "Gramática.y"
{yyerror("LInea  se esperaba una expresion y se encontro '!='");}
break;
case 63:
//#line 130 "Gramática.y"
{yyerror("LInea  se esperaba una expresion y se encontro '>='");}
break;
case 64:
//#line 131 "Gramática.y"
{yyerror("LInea  se esperaba una expresion y se encontro '<='");}
break;
//#line 776 "Parser.java"
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
public Parser(LexicalAnalyzer lex,SymbolTable st, Errors er)
{
  this.lex = lex;
  this.st = st;
  this.errors=er;
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
