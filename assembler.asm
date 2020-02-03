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
_@AUX5 DD ?
_finalizacion_de_programa DB "finalizacion de programa" ,0
_l DD ?
_@AUX4 DD ?
_@AUX3 DD ?
_@AUX2 DD ?
_@AUX1 DD ?
_@AUX0 DD ?
_f DD ?
_e DD ?
_d DD ?
_c DD ?
_b DD ?
_a DD ?
_2000p0 DD 2000.0
_6000000p0 DD 6000000.0
_3000p0 DD 3000.0
_x DD ?
_@AUX8 DD ?
_@AUX7 DD ?
_@AUX6 DD ?
.CODE
START:

FLD _2000p0
FSTP _l

FILD _2000p0
FMUL _3000p0
FSTP _@AUX1



FILD _a
FMUL _@AUX1
FSTP _@AUX2



FILD _e
FMUL _f
FSTP _@AUX3



FILD _b
FMUL _c
FSTP _@AUX4



FILD _d
FMUL _@AUX4
FSTP _@AUX5



FLD _@AUX3
FLD _@AUX5
FADD
JO @OVERFLOW_EN_SUMA
FSTP _@AUX6



FLD _@AUX2
FLD _@AUX6
FADD
JO @OVERFLOW_EN_SUMA
FSTP _@AUX7



FLD _@AUX7
FSTP _x

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

