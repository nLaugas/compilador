package SymbolTable;


import java.util.Hashtable;

public class SymbolTable {

    private Hashtable<String, Symbol> tb;

    public SymbolTable(){
        tb = new Hashtable<>();
    }

    public void setSymbol(String lexema, int number) {
        if (!tb.containsKey(lexema)){
            Symbol token = new Symbol(lexema, number);
            tb.put(lexema, token);
        }
        //debemos agregar un else para contemplar los casos donde el lexema ya existe pero es otro token
        // por ejemplo 25_i y '25_i' o _palabra y 'palabra' o 2.5F35 y '2.5F35'
        //donde albos token tienen el mismo lexema pero distinto tipo
        //como el probrema viene traido por las cadenas de caracteres, guardamos el primer ' como parte del lexema
    }

    public boolean existsSymbol(String lexema){
        return tb.containsKey(lexema);
    }





}
