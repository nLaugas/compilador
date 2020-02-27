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
_@AUX5 DW ?
_finalizacion_de_programa DB "finalizacion de programa" ,0
_@AUX4 DW ?
_@AUX3 DW ?
_@AUX2 DW ?
_@AUX1 DW ?
_@AUX0 DW ?
_f DW ?
_e DW ?
_d DW ?
_1_i DW 1
_c DD ?
_b DW ?
_a DW ?
_es_distinto DB "es distinto" ,0
_5_i DW 5
_es_igual DB "es igual" ,0
_x DW ?
_@AUX9 DW ?
_@AUX8 DW ?
_@AUX7 DW ?
_@AUX6 DW ?
.CODE
START:

MOV AX, _1_i
MOV _e ,AX

MOV AX, _1_i
MOV _b ,AX

MOV AX, _1_i
MOV _d ,AX

MOV AX, _e
MUL _b
MOV _@AUX3, AX



MOV AX, _@AUX3
ADD AX, _d
JO @OVERFLOW_EN_SUMA
MOV _@AUX4 ,AX



MOV AX, _@AUX4
MOV _x ,AX

MOV AX, _5_i
MOV _b ,AX

MOV AX, _e
MUL _b
MOV _@AUX7, AX



MOV AX, _@AUX7
ADD AX, _d
JO @OVERFLOW_EN_SUMA
MOV _@AUX8 ,AX



MOV AX, _@AUX8
MOV _f ,AX

MOV AX, _x
CMP AX, _f
JNE  Label14

invoke MessageBox, NULL, addr _es_igual, addr TITULO , MB_OK 
JMP Label15
Label14:

invoke MessageBox, NULL, addr _es_distinto, addr TITULO , MB_OK 

Label15:


invoke MessageBox, NULL, addr _finalizacion_de_programa, addr TITULO , MB_OK 
jMP EXIT
@RESULTADO_NEGATIVO_RESTA:
Invoke MessageBox, NULL, addr RESULTADO_NEGATIVO_RESTA, addr RESULTADO_NEGATIVO_RESTA, MB_OK
Invoke ExitProcess, 0
@OVERFLOW_EN_SUMA:
Invoke MessageBox, NULL, addr OVERFLOW_EN_SUMA, addr OVERFLOW_EN_SUMA, MB_OK
Invoke ExitProcess, 0
@DIVISION_POR_CERO:
Invoke MessageBox, NULL, addr DIVISION_POR_CERO, addr DIVISION_POR_CERO, MB_OK
Invoke ExitProcess, 0
EXIT:
END START

