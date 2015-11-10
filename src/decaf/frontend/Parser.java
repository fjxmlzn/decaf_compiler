//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


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
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short NULL=262;
public final static short EXTENDS=263;
public final static short THIS=264;
public final static short WHILE=265;
public final static short FOR=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short RETURN=269;
public final static short BREAK=270;
public final static short NEW=271;
public final static short PRINT=272;
public final static short READ_INTEGER=273;
public final static short READ_LINE=274;
public final static short LITERAL=275;
public final static short IDENTIFIER=276;
public final static short AND=277;
public final static short OR=278;
public final static short STATIC=279;
public final static short INSTANCEOF=280;
public final static short LESS_EQUAL=281;
public final static short GREATER_EQUAL=282;
public final static short EQUAL=283;
public final static short NOT_EQUAL=284;
public final static short SELF_INC=285;
public final static short SELF_DEC=286;
public final static short NUMINSTANCES=287;
public final static short FI=288;
public final static short CASESEP=289;
public final static short DO=290;
public final static short OD=291;
public final static short UMINUS=292;
public final static short EMPTY=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   14,   14,   14,
   23,   23,   23,   25,   25,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   28,   24,   24,   29,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   27,
   27,   26,   26,   30,   30,   16,   17,   20,   15,   31,
   31,   18,   18,   19,   21,   22,   32,   32,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    2,    0,    1,    2,    1,    1,
    1,    2,    2,    2,    1,    1,    1,    3,    1,    0,
    1,    3,    4,    4,    6,    1,    1,    1,    5,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    2,    2,    0,    3,    2,    0,    3,
    2,    3,    3,    1,    4,    5,    6,    5,    4,    1,
    1,    1,    0,    3,    1,    5,    9,    1,    6,    2,
    0,    2,    1,    4,    3,    3,    5,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    7,    8,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   26,   23,   20,   22,    0,   81,   74,    0,    0,    0,
    0,   88,    0,    0,    0,    0,   80,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   24,   27,   35,   25,
    0,   29,   30,   31,    0,    0,    0,   36,   37,    0,
    0,    0,   48,    0,    0,    0,   46,    0,   47,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   68,   71,    0,    0,    0,    0,    0,    0,   28,   32,
   33,   34,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   95,    0,    0,    0,    0,    0,   72,
   73,    0,    0,   67,   70,    0,    0,   96,    0,   63,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   98,    0,   75,    0,    0,   94,   44,    0,   79,    0,
    0,   43,    0,   86,    0,    0,    0,   76,    0,    0,
   78,    0,    0,    0,    0,   89,   97,   77,   45,    0,
   90,    0,   87,
};
final static short yydgoto[] = {                          2,
    3,    4,   68,   20,   33,    8,   11,   22,   34,   35,
   69,   45,   70,   71,   72,   73,   74,   75,   76,   77,
   78,   79,   87,   81,   89,  142,   83,   98,   99,  143,
  196,   90,
};
final static short yysindex[] = {                      -252,
 -260,    0, -252,    0, -236,    0, -232,  -73,    0,    0,
  208,    0,    0,    0,    0, -214,  -49,    0,    0,    8,
  -89,    0,    0,  -87,    0,   32,  -16,   47,  -49,    0,
  -49,    0,  -85,   43,   45,   50,    0,  -35,  -49,  -35,
    0,    0,    0,    0,    2,    0,    0,   53,   54,  124,
  141,    0, -142,   57,   61,   62,    0,   64,   65, -181,
 -169,   68,  141,  141,  141,   90,    0,    0,    0,    0,
   52,    0,    0,    0,   63,   67,   69,    0,    0,   59,
  929,    0,    0,  141,  141,   90,    0,  559,    0, -233,
  929,   81,   33,  141,   88,   91,  141, -154, -153,  141,
    0,    0, -139, -246,  -38,  -38, -138,  589,    0,    0,
    0,    0,  141,  141,  141,  141,  141,  141,  141,  141,
  141,  141,  141,  141,  141,  141, -134,  141,  141,  600,
   86,  640,   36,    0,  141,  108,  107,  929,  -21,    0,
    0,  109,  111,    0,    0,  664,  110,    0,  115,    0,
  929,  981,  970,   -6,   -6,  -32,  -32,  -13,  -13,  -38,
  -38,  -38,   -6,   -6,  121,  802,  843,   36,  141,   70,
    0,  867,    0,  907,  141,    0,    0, -114,    0,  141,
  141,    0,  141,    0,  918, -103,   36,    0,  929,  125,
    0,  131,  929,  141,   36,    0,    0,    0,    0,  132,
    0,   36,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  176,    0,   56,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  118,    0,    0,  139,    0,
  139,    0,    0,    0,  142,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  123,    0,    0,    0,    0,    0,
  126,    0,    0,    0,    0,    0,    0,  388,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  959,
    0,  160,    0,    0,  123,    0,    0,    0,    0,    0,
  133,    0,    0,    0,    0,    0,  143,    0,    0,    0,
    0,    0,    0,    0,  451,  460,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  123,    0,    0,    0,    0,  -19,    0,    0,
    0,    0,  149,    0,    0,    0,    0,    0,    0,    0,
  -40,   95,    5,    7, 1030, 1070, 1112,  994, 1004,  487,
  496,  523, 1050, 1059,  415,    0,    0,  123,    0,  532,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  143,    0,    0,    0,    0,  -33,  123,    0,   16,    0,
    0,    0,   55,   50,  123,    0,    0,    0,    0,    0,
    0,  123,    0,
};
final static short yygindex[] = {                         0,
    0,  191,  184,   44,   21,    0,    0,    0,  165,    0,
   42,    0, -116,  -82,    0,    0,    0,    0,    0,    0,
    0,    0,  -27, 1288,  -24,   17,    0,    0,    0,  105,
    0,  150,
};
final static int YYTABLESIZE=1471;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         91,
   38,   27,  131,   27,  124,   27,   91,  127,    1,  122,
  120,   91,  121,  127,  123,    5,  171,   80,   38,  176,
   82,   85,  175,  124,   85,   91,    7,  126,  122,  125,
  124,   21,  127,  123,   65,  122,  120,   24,  121,  127,
  123,   66,  135,    9,  148,   62,   64,   59,   62,   10,
   59,  184,  128,  186,  134,  135,   84,   80,  128,   84,
   82,   23,   62,   62,   59,   59,   25,   62,   65,   59,
  197,   29,   32,   93,   32,   66,   30,  128,  201,   42,
   64,   44,   43,   38,  128,  203,   31,   41,   39,   91,
   40,   91,   84,   85,  101,   49,   94,   62,   49,   59,
   95,   96,   65,   97,  100,   80,  102,  103,   82,   66,
  109,  200,   49,   49,   12,   13,   14,   15,   16,  113,
  136,  110,   65,  137,   41,  111,   67,  112,  140,   66,
  144,  141,  145,   92,   64,   61,  147,  149,   61,   65,
   80,  165,   80,   82,  169,   82,   66,   49,  173,  177,
  179,   64,   61,   61,  175,  180,   65,   61,   41,   80,
  181,  190,   82,   86,  195,  198,   80,   80,   64,   82,
   82,  199,  202,   65,   80,    1,    5,   82,   14,   19,
   66,   40,   18,   83,   93,   64,   26,   61,   28,   82,
   37,   92,   41,    6,   19,   36,   47,  192,  139,   30,
   39,   47,   47,    0,   47,   47,   47,   12,   13,   14,
   15,   16,  104,    0,    0,    0,    0,    0,   39,   47,
    0,   47,   47,   91,   91,   91,   91,   91,   91,    0,
   91,   91,   91,   91,    0,   91,   91,   91,   91,   91,
   91,   91,   91,    0,    0,    0,   91,    0,  116,  117,
   47,   91,   91,   91,   91,   91,   91,   91,   12,   13,
   14,   15,   16,   46,    0,   47,   48,   49,   50,    0,
   51,   52,   53,   54,   55,   56,   57,   58,    0,    0,
    0,   59,   62,   59,   59,    0,   60,   61,   62,   59,
   59,   63,   12,   13,   14,   15,   16,   46,    0,   47,
   48,   49,   50,    0,   51,   52,   53,   54,   55,   56,
   57,   58,    0,    0,    0,   59,    0,    0,    0,    0,
   60,   61,   62,    0,    0,   63,   12,   13,   14,   15,
   16,   46,   18,   47,   48,   49,   50,    0,   51,   52,
   53,   54,   55,   56,   57,   58,    0,    0,    0,   59,
  107,   46,    0,   47,   60,   61,   62,    0,    0,   63,
   53,    0,   55,   56,   57,   58,    0,    0,   46,   59,
   47,   61,   61,    0,   60,   61,   62,   53,    0,   55,
   56,   57,   58,    0,    0,   46,   59,   47,    0,    0,
    0,   60,   61,   62,   53,    0,   55,   56,   57,   58,
    0,    0,   46,   59,   47,    0,    0,    0,   60,   61,
   62,   53,    0,   55,   56,   57,   58,    0,    0,    0,
   59,    0,    0,    0,   41,   60,   61,   62,   41,   41,
   41,   41,   41,   41,   41,    0,   47,   47,    0,    0,
   47,   47,   47,   47,    0,   41,   41,   41,   41,   41,
   41,   42,    0,    0,    0,   42,   42,   42,   42,   42,
   42,   42,    0,    0,   12,   13,   14,   15,   16,    0,
    0,    0,   42,   42,   42,   42,   42,   42,   41,    0,
   41,    0,    0,    0,    0,    0,   17,   64,    0,    0,
    0,   64,   64,   64,   64,   64,   65,   64,    0,    0,
   65,   65,   65,   65,   65,   42,   65,   42,   64,   64,
   64,    0,   64,   64,    0,    0,    0,   65,   65,   65,
    0,   65,   65,   52,    0,    0,    0,   52,   52,   52,
   52,   52,   53,   52,    0,    0,   53,   53,   53,   53,
   53,    0,   53,   64,   52,   52,   52,    0,   52,   52,
    0,    0,   65,   53,   53,   53,    0,   53,   53,   54,
    0,    0,    0,   54,   54,   54,   54,   54,   63,   54,
    0,    0,    0,   63,   63,    0,   63,   63,   63,   52,
   54,   54,   54,    0,   54,   54,    0,    0,   53,   63,
   40,   63,    0,   63,   63,  124,    0,    0,    0,    0,
  122,  120,    0,  121,  127,  123,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   54,  133,    0,  126,    0,
  125,  129,   63,    0,    0,  124,    0,    0,    0,  150,
  122,  120,    0,  121,  127,  123,  124,    0,    0,    0,
  168,  122,  120,    0,  121,  127,  123,    0,  126,  128,
  125,  129,    0,    0,    0,    0,    0,    0,    0,  126,
    0,  125,  129,    0,   41,   41,    0,    0,   41,   41,
   41,   41,   66,   69,    0,    0,  124,    0,    0,  128,
  170,  122,  120,    0,  121,  127,  123,    0,    0,    0,
  128,   42,   42,    0,    0,   42,   42,   42,   42,  126,
  124,  125,  129,    0,    0,  122,  120,  178,  121,  127,
  123,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  126,    0,  125,  129,   64,   64,    0,
  128,   64,   64,   64,   64,    0,   65,   65,    0,    0,
   65,   65,   65,   65,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  128,    0,    0,    0,    0,    0,
    0,    0,    0,   52,   52,    0,    0,   52,   52,   52,
   52,    0,   53,   53,    0,    0,   53,   53,   53,   53,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   54,
   54,    0,    0,   54,   54,   54,   54,    0,   63,   63,
    0,    0,   63,   63,   63,   63,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  114,  115,    0,  124,  116,
  117,  118,  119,  122,  120,    0,  121,  127,  123,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  126,    0,  125,  129,  114,  115,    0,    0,  116,
  117,  118,  119,    0,    0,    0,  114,  115,    0,  124,
  116,  117,  118,  119,  122,  120,    0,  121,  127,  123,
    0,    0,  128,    0,  182,    0,    0,    0,    0,    0,
  183,    0,  126,  124,  125,  129,    0,    0,  122,  120,
    0,  121,  127,  123,    0,    0,  114,  115,    0,    0,
  116,  117,  118,  119,  187,    0,  126,    0,  125,  129,
    0,    0,    0,  128,    0,    0,    0,    0,    0,    0,
  114,  115,    0,  124,  116,  117,  118,  119,  122,  120,
    0,  121,  127,  123,  124,    0,    0,  128,    0,  122,
  120,    0,  121,  127,  123,  124,  126,    0,  125,  129,
  122,  120,    0,  121,  127,  123,  194,  126,    0,  125,
  129,    0,    0,    0,    0,    0,    0,    0,  126,    0,
  125,  129,    0,    0,    0,   46,    0,  128,    0,  188,
   46,   46,    0,   46,   46,   46,  124,    0,  128,    0,
    0,  122,  120,    0,  121,  127,  123,  124,   46,  128,
   46,   46,  122,  120,    0,  121,  127,  123,    0,  126,
    0,  125,    0,    0,   50,    0,   50,   50,   50,    0,
  126,    0,  125,    0,   51,    0,   51,   51,   51,   46,
    0,   50,   50,   50,    0,   50,   50,    0,    0,    0,
  128,   51,   51,   51,    0,   51,   51,    0,    0,    0,
   60,  128,    0,   60,    0,    0,    0,    0,  114,  115,
    0,    0,  116,  117,  118,  119,   50,   60,   60,    0,
   58,    0,   60,   58,    0,    0,   51,    0,    0,   57,
    0,    0,   57,    0,    0,    0,    0,   58,   58,    0,
   55,    0,   58,   55,    0,    0,   57,   57,    0,  114,
  115,   57,   60,  116,  117,  118,  119,   55,   55,    0,
    0,    0,   55,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   58,  114,  115,    0,    0,  116,  117,  118,
  119,   57,   56,    0,    0,   56,    0,    0,    0,    0,
    0,    0,   55,    0,    0,    0,    0,    0,    0,   56,
   56,    0,    0,    0,   56,    0,    0,    0,    0,    0,
    0,    0,    0,  114,  115,    0,    0,  116,  117,  118,
  119,    0,    0,    0,  114,  115,    0,    0,  116,  117,
  118,  119,    0,    0,   56,  114,  115,    0,    0,  116,
  117,  118,  119,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   46,   46,    0,    0,   46,
   46,   46,   46,    0,    0,    0,  114,    0,    0,    0,
  116,  117,  118,  119,    0,    0,    0,    0,    0,    0,
    0,  116,  117,  118,  119,    0,    0,    0,    0,    0,
   50,   50,    0,    0,   50,   50,   50,   50,    0,    0,
   51,   51,    0,    0,   51,   51,   51,   51,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   60,   60,    0,    0,
    0,    0,   60,   60,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   58,   58,    0,    0,
    0,    0,   58,   58,    0,   57,   57,   88,   91,    0,
    0,   57,   57,    0,    0,    0,   55,   55,    0,    0,
   88,  105,  106,  108,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  130,    0,  132,    0,    0,    0,    0,    0,    0,
    0,  138,    0,    0,  138,    0,    0,  146,   56,   56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  151,  152,  153,  154,  155,  156,  157,  158,  159,  160,
  161,  162,  163,  164,    0,  166,  167,    0,    0,    0,
    0,    0,  172,    0,  174,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  185,    0,    0,    0,
    0,    0,  189,    0,    0,    0,    0,  191,  138,    0,
  193,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   41,   91,   85,   91,   37,   91,   40,   46,  261,   42,
   43,   45,   45,   46,   47,  276,  133,   45,   59,   41,
   45,   41,   44,   37,   44,   59,  263,   60,   42,   62,
   37,   11,   46,   47,   33,   42,   43,   17,   45,   46,
   47,   40,  289,  276,  291,   41,   45,   41,   44,  123,
   44,  168,   91,  170,  288,  289,   41,   85,   91,   44,
   85,  276,   58,   59,   58,   59,   59,   63,   33,   63,
  187,   40,   29,   53,   31,   40,   93,   91,  195,   38,
   45,   40,   39,   41,   91,  202,   40,  123,   44,  123,
   41,  125,   40,   40,  276,   41,   40,   93,   44,   93,
   40,   40,   33,   40,   40,  133,  276,   40,  133,   40,
   59,  194,   58,   59,  257,  258,  259,  260,  261,   61,
   40,   59,   33,   91,  123,   59,  125,   59,   41,   40,
  285,   41,  286,  276,   45,   41,  276,  276,   44,   33,
  168,  276,  170,  168,   59,  170,   40,   93,   41,   41,
   41,   45,   58,   59,   44,   41,   33,   63,  123,  187,
   40,  276,  187,   40,  268,   41,  194,  195,   45,  194,
  195,   41,   41,   33,  202,    0,   59,  202,  123,   41,
   40,   59,   41,   41,   59,   45,  276,   93,  276,   41,
  276,   59,  123,    3,   11,   31,   37,  181,   94,   93,
   41,   42,   43,   -1,   45,   46,   47,  257,  258,  259,
  260,  261,   63,   -1,   -1,   -1,   -1,   -1,   59,   60,
   -1,   62,   63,  257,  258,  259,  260,  261,  262,   -1,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  273,
  274,  275,  276,   -1,   -1,   -1,  280,   -1,  281,  282,
   91,  285,  286,  287,  288,  289,  290,  291,  257,  258,
  259,  260,  261,  262,   -1,  264,  265,  266,  267,   -1,
  269,  270,  271,  272,  273,  274,  275,  276,   -1,   -1,
   -1,  280,  278,  277,  278,   -1,  285,  286,  287,  283,
  284,  290,  257,  258,  259,  260,  261,  262,   -1,  264,
  265,  266,  267,   -1,  269,  270,  271,  272,  273,  274,
  275,  276,   -1,   -1,   -1,  280,   -1,   -1,   -1,   -1,
  285,  286,  287,   -1,   -1,  290,  257,  258,  259,  260,
  261,  262,  125,  264,  265,  266,  267,   -1,  269,  270,
  271,  272,  273,  274,  275,  276,   -1,   -1,   -1,  280,
  261,  262,   -1,  264,  285,  286,  287,   -1,   -1,  290,
  271,   -1,  273,  274,  275,  276,   -1,   -1,  262,  280,
  264,  277,  278,   -1,  285,  286,  287,  271,   -1,  273,
  274,  275,  276,   -1,   -1,  262,  280,  264,   -1,   -1,
   -1,  285,  286,  287,  271,   -1,  273,  274,  275,  276,
   -1,   -1,  262,  280,  264,   -1,   -1,   -1,  285,  286,
  287,  271,   -1,  273,  274,  275,  276,   -1,   -1,   -1,
  280,   -1,   -1,   -1,   37,  285,  286,  287,   41,   42,
   43,   44,   45,   46,   47,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,   58,   59,   60,   61,   62,
   63,   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,
   46,   47,   -1,   -1,  257,  258,  259,  260,  261,   -1,
   -1,   -1,   58,   59,   60,   61,   62,   63,   91,   -1,
   93,   -1,   -1,   -1,   -1,   -1,  279,   37,   -1,   -1,
   -1,   41,   42,   43,   44,   45,   37,   47,   -1,   -1,
   41,   42,   43,   44,   45,   91,   47,   93,   58,   59,
   60,   -1,   62,   63,   -1,   -1,   -1,   58,   59,   60,
   -1,   62,   63,   37,   -1,   -1,   -1,   41,   42,   43,
   44,   45,   37,   47,   -1,   -1,   41,   42,   43,   44,
   45,   -1,   47,   93,   58,   59,   60,   -1,   62,   63,
   -1,   -1,   93,   58,   59,   60,   -1,   62,   63,   37,
   -1,   -1,   -1,   41,   42,   43,   44,   45,   37,   47,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   93,
   58,   59,   60,   -1,   62,   63,   -1,   -1,   93,   58,
   59,   60,   -1,   62,   63,   37,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   93,   58,   -1,   60,   -1,
   62,   63,   91,   -1,   -1,   37,   -1,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   37,   -1,   -1,   -1,
   41,   42,   43,   -1,   45,   46,   47,   -1,   60,   91,
   62,   63,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   60,
   -1,   62,   63,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,  285,  286,   -1,   -1,   37,   -1,   -1,   91,
   41,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   91,  277,  278,   -1,   -1,  281,  282,  283,  284,   60,
   37,   62,   63,   -1,   -1,   42,   43,   44,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   60,   -1,   62,   63,  277,  278,   -1,
   91,  281,  282,  283,  284,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   37,  281,
  282,  283,  284,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   60,   -1,   62,   63,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   -1,  277,  278,   -1,   37,
  281,  282,  283,  284,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   91,   -1,   93,   -1,   -1,   -1,   -1,   -1,
   58,   -1,   60,   37,   62,   63,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   -1,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   58,   -1,   60,   -1,   62,   63,
   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   37,  281,  282,  283,  284,   42,   43,
   -1,   45,   46,   47,   37,   -1,   -1,   91,   -1,   42,
   43,   -1,   45,   46,   47,   37,   60,   -1,   62,   63,
   42,   43,   -1,   45,   46,   47,   59,   60,   -1,   62,
   63,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   60,   -1,
   62,   63,   -1,   -1,   -1,   37,   -1,   91,   -1,   93,
   42,   43,   -1,   45,   46,   47,   37,   -1,   91,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   37,   60,   91,
   62,   63,   42,   43,   -1,   45,   46,   47,   -1,   60,
   -1,   62,   -1,   -1,   41,   -1,   43,   44,   45,   -1,
   60,   -1,   62,   -1,   41,   -1,   43,   44,   45,   91,
   -1,   58,   59,   60,   -1,   62,   63,   -1,   -1,   -1,
   91,   58,   59,   60,   -1,   62,   63,   -1,   -1,   -1,
   41,   91,   -1,   44,   -1,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   93,   58,   59,   -1,
   41,   -1,   63,   44,   -1,   -1,   93,   -1,   -1,   41,
   -1,   -1,   44,   -1,   -1,   -1,   -1,   58,   59,   -1,
   41,   -1,   63,   44,   -1,   -1,   58,   59,   -1,  277,
  278,   63,   93,  281,  282,  283,  284,   58,   59,   -1,
   -1,   -1,   63,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   93,  277,  278,   -1,   -1,  281,  282,  283,
  284,   93,   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,
   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,   58,
   59,   -1,   -1,   -1,   63,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   93,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   -1,  277,   -1,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
   -1,   -1,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
   -1,   -1,  283,  284,   -1,  277,  278,   50,   51,   -1,
   -1,  283,  284,   -1,   -1,   -1,  277,  278,   -1,   -1,
   63,   64,   65,   66,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   84,   -1,   86,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   94,   -1,   -1,   97,   -1,   -1,  100,  277,  278,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  113,  114,  115,  116,  117,  118,  119,  120,  121,  122,
  123,  124,  125,  126,   -1,  128,  129,   -1,   -1,   -1,
   -1,   -1,  135,   -1,  137,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  169,   -1,   -1,   -1,
   -1,   -1,  175,   -1,   -1,   -1,   -1,  180,  181,   -1,
  183,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN","BREAK",
"NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR",
"STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL",
"SELF_INC","SELF_DEC","NUMINSTANCES","FI","CASESEP","DO","OD","UMINUS","EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"Stmt : GuardedIfStmt",
"Stmt : GuardedDoStmt",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"LValue : IDENTIFIER",
"LValue : Expr '.' IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : IDENTIFIER '(' Actuals ')'",
"Call : Expr '.' IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Expr '?' Expr ':' Expr",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"$$1 :",
"Expr : IDENTIFIER $$1 SELF_INC",
"Expr : SELF_INC IDENTIFIER",
"$$2 :",
"Expr : IDENTIFIER $$2 SELF_DEC",
"Expr : SELF_DEC IDENTIFIER",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Expr : NUMINSTANCES '(' IDENTIFIER ')'",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"GuardedIfStmt : IF GuardedStmts FI",
"GuardedDoStmt : DO GuardedStmts OD",
"GuardedStmts : GuardedStmts CASESEP Expr ':' Stmt",
"GuardedStmts : Expr ':' Stmt",
};

//#line 488 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 706 "Parser.java"
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
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
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
      //if (yydebug) debug("reduce");
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
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
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
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 59 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 65 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 69 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 79 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 85 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 89 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 93 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 97 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 101 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 105 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 111 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 117 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 14:
//#line 121 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 15:
//#line 127 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 16:
//#line 131 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 17:
//#line 135 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 19:
//#line 143 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 20:
//#line 150 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 21:
//#line 154 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 22:
//#line 161 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 23:
//#line 165 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 171 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 25:
//#line 177 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 26:
//#line 181 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 27:
//#line 188 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 28:
//#line 193 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 38:
//#line 210 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 39:
//#line 214 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 40:
//#line 218 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 41:
//#line 224 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(new SemValue().expr, val_peek(0).ident, val_peek(0).loc);
					}
break;
case 42:
//#line 228 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(2).expr, val_peek(0).ident, val_peek(0).loc);
					}
break;
case 43:
//#line 232 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 44:
//#line 238 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(new SemValue().expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
					}
break;
case 45:
//#line 242 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(5).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
					}
break;
case 46:
//#line 248 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 49:
//#line 254 "Parser.y"
{
                		yyval.expr = new Tree.Ternary(Tree.COND, val_peek(4).expr, val_peek(2).expr, val_peek(0).expr, val_peek(3).loc);
                	}
break;
case 50:
//#line 258 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 262 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 266 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 270 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 274 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 278 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 282 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 286 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 290 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 294 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 298 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 302 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 306 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 310 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 64:
//#line 314 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 318 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 322 "Parser.y"
{
						yyval.expr = new Tree.Ident(new SemValue().expr, val_peek(0).ident, val_peek(0).loc);
					}
break;
case 67:
//#line 326 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.POSTINC, val_peek(1).expr, val_peek(0).loc);
                	}
break;
case 68:
//#line 331 "Parser.y"
{
                		Tree.Expr tmpExpr = new Tree.Ident(new SemValue().expr, val_peek(0).ident, val_peek(0).loc);
                		yyval.expr = new Tree.Unary(Tree.PREINC, tmpExpr, val_peek(1).loc);
                	}
break;
case 69:
//#line 336 "Parser.y"
{
                		yyval.expr = new Tree.Ident(new SemValue().expr, val_peek(0).ident, val_peek(0).loc);
                	}
break;
case 70:
//#line 340 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.POSTDEC, val_peek(1).expr, val_peek(0).loc);
                	}
break;
case 71:
//#line 345 "Parser.y"
{
                		Tree.Expr tmpExpr = new Tree.Ident(new SemValue().expr, val_peek(0).ident, val_peek(0).loc);
                		yyval.expr = new Tree.Unary(Tree.PREDEC, tmpExpr, val_peek(1).loc);
                	}
break;
case 72:
//#line 350 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 73:
//#line 354 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 74:
//#line 358 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 75:
//#line 362 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 76:
//#line 366 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 77:
//#line 370 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 78:
//#line 374 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 79:
//#line 378 "Parser.y"
{
                		yyval.expr = new Tree.NumInstances(Tree.NUMINSTANCES, val_peek(1).ident, val_peek(3).loc);
                	}
break;
case 80:
//#line 384 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 81:
//#line 388 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 83:
//#line 395 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 84:
//#line 402 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 85:
//#line 406 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 86:
//#line 413 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 87:
//#line 419 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 88:
//#line 425 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 89:
//#line 431 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 90:
//#line 437 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 91:
//#line 441 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 92:
//#line 447 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 93:
//#line 451 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 94:
//#line 457 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 95:
//#line 463 "Parser.y"
{
						yyval.stmt = new Tree.GuardedBlock(Tree.GUARDEDIF, val_peek(1).stmt, val_peek(2).loc);
					}
break;
case 96:
//#line 469 "Parser.y"
{
						yyval.stmt = new Tree.GuardedBlock(Tree.GUARDEDDO, val_peek(1).stmt, val_peek(2).loc);
					}
break;
case 97:
//#line 475 "Parser.y"
{
						yyval = new SemValue();
						yyval.stmt = new Tree.GuardedStmts(val_peek(4).stmt, val_peek(2).expr, val_peek(0).stmt, val_peek(1).loc);
					
					}
break;
case 98:
//#line 481 "Parser.y"
{
						yyval = new SemValue();
						yyval.stmt = new Tree.GuardedStmts(null, val_peek(2).expr, val_peek(0).stmt, val_peek(1).loc);
					}
break;
//#line 1370 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
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
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
