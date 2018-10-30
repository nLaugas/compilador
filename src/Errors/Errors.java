package Errors;

import java.util.LinkedList;
import java.util.Queue;

public class Errors {
    private class Terna{
        public int row;
        public int column;
        public String error;

        public Terna(int row, int column, String error){
            this.row = row;
            this.column = column;
            this.error = error;
        }
    }
 // table
    private Queue<Terna> errors;
    private Terna terna;
    public final static String ERROR_MAX_WORD_SIZE = "supera el maximo permitido";
    public final static String ERROR_FAIL_CHARACTER= "lexema no valido";
    public final static String ERROR_RANGE= "fuera de rango";
    public Errors(){
        errors = new LinkedList<>();
    }

    public void setError(int row, int column, String error){
        errors.add(new Terna(row,column,error));
    }

    public String getError(){
        terna = errors.remove();
        return terna.error;
    }

    public String getAll(){
        String out = new String();

        for (Terna elem : errors){
            out += elem.error + " fila " + elem.row + " columna " + elem.column + "\n";
        }
        return out;
    }

    public int getRow(){
        return terna.row;
    }

    public int getColumn(){
        return terna.column;
    }

    public boolean isEmpty(){
        return errors.isEmpty();
    }
}
