package SymbolTable;

public class SymbolTable {
    private String attribute;
    private int value;


    public SymbolTable(String type){
        this.type = type;
        code = 1;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode(){
        return this.code;
    }




}
