
#!/bin/bash
./yacc.linux -J GramaticaGrupo8.y
mv Parser.java src/AnalizadorSintactico/
rm ParserVal.java