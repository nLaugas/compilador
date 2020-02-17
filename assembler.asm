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
_1_i DW 1
_@AUX3 DW ?
_finalizacion_de_programa DB "finalizacion de programa" ,0
_@AUX2 DW ?
_@AUX1 DW ?
_@AUX0 DW ?
_2_i DW 2
_es_distinto DB "es distinto" ,0
_c DD ?
_b DW ?
_a DW ?
_es_igual DB "es igual" ,0
.CODE
START:

MOV AX, _1_i
MOV _b ,AX

MOV AX, _2_i
MOV _a ,AX

MOV EAX, OFFSET _a
MOV _c, EAX

MOV AX, _b
CMP AX, _a
JE  Label7

invoke MessageBox, NULL, addr _es_igual, addr TITULO , MB_OK 
JMP Label8
Label7:

invoke MessageBox, NULL, addr _es_distinto, addr TITULO , MB_OK 

Label8:

MOV BX, _1_i
MOV EAX, _c
mov word ptr [EAX], BX


MOV AX, _b
CMP AX, _a
JE  Label14

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

