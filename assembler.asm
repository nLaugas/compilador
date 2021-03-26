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
DIVISION_POR_CERO DB "Division por cero, error de ejecucion" , 0

_@cero DW 0,0
_@max_float1 DQ 1.17549435e38
_@min_float1 DQ -1.17549435e38
_@max_float2 DQ 3.40282347e-38 
_@min_float2 DQ -3.40282347e-38
_@max_integer DD 32768
_@min_integer DD -32768
_memaux DW ?
_a DW ?
_@AUX0 DW ?
_1_i DW 1
.CODE
START:

MOV AX, _1_i
MOV _a ,AX
jMP EXIT
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

