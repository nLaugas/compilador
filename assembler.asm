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
_50p0 DD 50.0
_@AUX1 DD ?
_@AUX0 DD ?
_f DD ?
_e DD ?
_d DD ?
_c DD ?
_b DD ?
_a DD ?
_1500p0 DD 1500.0
_45000p0 DD 45000.0
_es_distinto DB "es distinto" ,0
_20p0 DD 20.0
_600p0 DD 600.0
_es_igual DB "es igual" ,0
_x DD ?
_30p0 DD 30.0
_24000p0 DD 24000.0
_40p0 DD 40.0
_900000p0 DD 900000.0
.CODE
START:

FLD _900000p0
FSTP _f

FLD _24000p0
FSTP _x

FLD _x

FCOMP _f

FSTSW _memaux

MOV AX, _memaux

SAHF
JNE  Label6

invoke MessageBox, NULL, addr _es_igual, addr TITULO , MB_OK 
JMP Label7
Label6:

invoke MessageBox, NULL, addr _es_distinto, addr TITULO , MB_OK 

Label7:


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

