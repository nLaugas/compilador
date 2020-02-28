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
_@AUX4 DD ?
_@AUX3 DD ?
_@AUX2 DD ?
_@AUX1 DD ?
_@AUX0 DD ?
_f DD ?
_e DD ?
_d DD ?
_1000p0 DD 1000.0
_@AUX12 DD ?
_c DD ?
_@AUX11 DD ?
_b DD ?
_@AUX10 DD ?
_a DD ?
_2000p0 DD 2000.0
_es_distinto DB "es distinto" ,0
_es_igual DB "es igual" ,0
_5000p0 DD 5000.0
_x DD ?
_@AUX9 DD ?
_@AUX8 DD ?
_@AUX7 DD ?
_@AUX6 DD ?
.CODE
START:

FLD _1000p0
FSTP _e

FLD _2000p0
FSTP _b

FLD _1000p0
FSTP _d

FILD _e
FMUL _d
FSTP _@AUX3



FLD _@AUX3
FLD _b
FADD
JO @OVERFLOW_EN_SUMA
FSTP _@AUX4



FLD _@AUX4
FSTP _x

FLD _@AUX3
FLD _b
FADD
JO @OVERFLOW_EN_SUMA
FSTP _@AUX7



FLD _@AUX7
FSTP _f

FLD _x

FCOMP _f

FSTSW _memaux

MOV AX, _memaux

SAHF
JNE  Label13

invoke MessageBox, NULL, addr _es_igual, addr TITULO , MB_OK 
JMP Label14
Label13:

invoke MessageBox, NULL, addr _es_distinto, addr TITULO , MB_OK 

Label14:


FLD _5000p0
FSTP _b

FLD _@AUX6
FLD _b
FADD
JO @OVERFLOW_EN_SUMA
FSTP _@AUX11



FLD _@AUX11
FSTP _x

FLD _x

FCOMP _f

FSTSW _memaux

MOV AX, _memaux

SAHF
JNE  Label23

invoke MessageBox, NULL, addr _es_igual, addr TITULO , MB_OK 
JMP Label24
Label23:

invoke MessageBox, NULL, addr _es_distinto, addr TITULO , MB_OK 

Label24:


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

