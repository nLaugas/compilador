package SymbolTable;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class SymbolTable {

    private Hashtable<String, Symbol> tb;
    private Queue<String> productor;
    public SymbolTable(){
        tb = new Hashtable<>();
        productor = new LinkedList<>();
    }

    public Symbol getSymbol(String key){
        return tb.get(key);
    }


    public boolean existsSymbol(String lexema){
        return tb.containsKey(lexema);
    }

    public String getAtributosNextLexema(){
       return tb.get(productor.remove()).getAtributos();
    }
    public boolean isEmpty(){
        return productor.isEmpty();

    }
    public void setSymbol(String lexema, int number) {
        if (!tb.containsKey(lexema)){
            Symbol token = new Symbol(lexema, number);
            tb.put(lexema, token);
            productor.add(lexema);
        }
        //debemos agregar un else para contemplar los casos donde el lexema ya existe pero es otro token
        // por ejemplo 25_i y '25_i' o _palabra y 'palabra' o 2.5F35 y '2.5F35'
        //donde albos token tienen el mismo lexema pero distinto tipo
        //como el probrema viene traido por las cadenas de caracteres, guardamos el primer ' como parte del lexema
    }
    public void setSymbol(Symbol aux) {
        if (!tb.containsKey(aux.getLexema())){
            tb.put(aux.getLexema(), aux);
            productor.add(aux.getLexema());
        }
    }

    public ArrayList<String> getEntradas(){
        return new ArrayList<String>( tb.keySet());
    }

    public void addcambiarSigno(Symbol aux){
        tb.put("-"+aux.getLexema(),new Symbol("-"+aux.getLexema(),aux.getTipo()));
        setAtributo("-"+aux.getLexema(),"=>",aux.getAtributo("=>"));
        productor.add("-"+aux.getLexema());
    }
    public void addcambiarSigno(String aux){
        tb.put("-"+aux,new Symbol("-"+this.getSymbol(aux).getLexema(),this.getSymbol(aux).getTipo()));
        setAtributo("-"+this.getSymbol(aux).getLexema(),"=>",this.getSymbol(aux).getAtributo("=>"));
        productor.add("-"+this.getSymbol(aux).getLexema());
    }
    public void setAtributo(String lexema, String atributo, Object valor){
        if (tb.containsKey(lexema))
            tb.get(lexema).setAtributos(atributo,valor);
    }

}
