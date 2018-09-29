package SymbolTable;

import java.util.Hashtable;

public class SymbolTable {

    private Hashtable<String,Integer> tb;

    public SymbolTable(){
        tb = new Hashtable<>();
    }

    public void setSymbol(String lexema, int number){
        tb.put(lexema,number);

    }

    public boolean existsSymbol(String lexema){
        return tb.containsKey(lexema);
    }





}
