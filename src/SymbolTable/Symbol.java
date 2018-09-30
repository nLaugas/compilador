package SymbolTable;

import java.util.Hashtable;

public class Symbol {
    private String lexema;
    private short tipo;
    private Hashtable <String,Object> atributos;

    public Symbol(String lexema, int number) {
        this.lexema = lexema;
        this.tipo = (short) number;
        atributos=new Hashtable<>();
    }

    public String getLexema () {
        return this.lexema;
    }

    public void setLexema (String lexema){
        this.lexema = lexema;
    }

    public int getTipo () {
        return (int) tipo;
    }

    public void setTipo (int t){
        this.tipo = (short) t;
    }

    public Object getAtributo(String clave) {
        return atributos.get(clave);
    }

    public void setAtributos(String atributo, Object valor) {
        atributos.put(atributo,valor);
    }
    public void cambiarSigno (){

    }
}