.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.DATA
TITULO DB "Mensaje" , 0
OVERFLOW_EN_SUMA DB "Overflow en suma" , 0
OVERFLOW_EN_PRODUCTO DB "Overflow en producto" , 0
RESULTADO_NEGATIVO_RESTA DB "Resultado negativo en resta" , 0
DIVISION_POR_CERO DB "Division por cero, error de ejecucion" , 0

_@cero DW 0,0
_@max_float1 DQ 1.17549435e38
_@min_float1 DQ -1.17549435e38
_@max_float2 DQ 3.40282347e-38 
_@min_float2 DQ -3.40282347e-38
_@max_integer DD 32768
_@min_integer DD -32768
_memaux DW ?
_70000p0 DD 70000.0
_@AUX5 DW ?
_l DD ?
_@AUX4 DW ?
_@AUX3 DW ?
_@AUX2 DW ?
_20_i DW 20
_@AUX1 DW ?
_@AUX0 DW ?
_g DW ?
_f DW ?
_127000p0 DD 127000.0
_d DW ?
_b DW ?
_@AUX10 DW ?
_a DW ?
_55000p0 DD 55000.0
_3_i DW 3
_2000p0 DD 2000.0
_es_distinto DB "es distinto" ,0
_2p45001277E10 DD 2.45001277E10
_57000p0 DD 57000.0
_2p45000008E10 DD 2.45000008E10
_es_igual DB "es igual" ,0
_u DW ?
_fin_de_programa DB "fin de programa" ,0
_@AUX9 DW ?
_@AUX8 DW ?
_@AUX7 DW ?
_@AUX6 DD ?
.CODE
START:

MOV AX, _3_i
MOV _b ,AX

MOV AX, _20_i
MOV _a ,AX

MOV AX, _a
ADD AX, _b
JO @OVERFLOW_EN_SUMA
MOV _@AUX2 ,AX



MOV AX, _@AUX2
MOV _d ,AX

MOV AX, _a
MUL _d
MOV _@AUX4, AX



MOV AX, _@AUX4
MOV _g ,AX

FLD _2p45001277E10
FSTP _l

MOV AX, _@AUX2
MOV _f ,AX

MOV AX, _@AUX4
MOV _u ,AX

MOV AX, _d
CMP AX, _f
JNE  Label15

invoke MessageBox, NULL, addr _es_igual, addr TITULO , MB_OK 
JMP Label16
Label15:

invoke MessageBox, NULL, addr _es_distinto, addr TITULO , MB_OK 

Label16:


invoke MessageBox, NULL, addr _fin_de_programa, addr TITULO , MB_OK 
jMP EXIT
@RESULTADO_NEGATIVO_RESTA:
Invoke MessageBox, NULL, addr RESULTADO_NEGATIVO_RESTA, addr RESULTADO_NEGATIVO_RESTA, MB_OK
Invoke ExitProcess, 0
@OVERFLOW_EN_SUMA:
Invoke MessageBox, NULL, addr OVERFLOW_EN_SUMA, addr OVERFLOW_EN_SUMA, MB_OK
Invoke ExitProcess, 0
@OVERFLOW_EN_PRODUCTO:
Invoke MessageBox, NULL, addr OVERFLOW_EN_PRODUCTO, addr OVERFLOW_EN_PRODUCTO, MB_OK
Invoke ExitProcess, 0
@DIVISION_POR_CERO:
Invoke MessageBox, NULL, addr DIVISION_POR_CERO, addr DIVISION_POR_CERO, MB_OK
Invoke ExitProcess, 0
EXIT:
END START

