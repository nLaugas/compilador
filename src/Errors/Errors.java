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

    private Queue<Terna> errors;
    private Terna terna;
    public final String ERROR_MAX_WORD_SIZE = "supera el maximo permitido";

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
