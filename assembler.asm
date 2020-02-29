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
_finalizacion_de_programa DB "finalizacion de programa" ,0
_@AUX2 DD ?
_@AUX1 DD ?
_3000p0 DD 3000.0
_@AUX0 DD ?
_f DD ?
_1000p0 DD 1000.0
_e DD ?
_d DD ?
_c DD ?
_b DD ?
_a DD ?
_x DD ?
_2000p0 DD 2000.0
.CODE
START:

FLD _1000p0
FLD _2000p0
FADD
JO @OVERFLOW_EN_SUMA
FSTP _@AUX0



FLD _3000p0
FSTP _e
MOV EAX, OFFSET _e
MOV _c, EAX

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

