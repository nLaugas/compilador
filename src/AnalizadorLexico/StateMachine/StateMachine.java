package AnalizadorLexico.StateMachine;
import AnalizadorLexico.SemanticAction.SemanticAction;

import java.util.Hashtable;

import static java.lang.Integer.MAX_VALUE;

public final class StateMachine {
    public static final int FINAL_STATE = MAX_VALUE;
    public static final int INITIAL_STATE = 0;
    public static final int ERROR_STATE = 0;
    private static Hashtable<Integer, Hashtable<Character, Dupla>> transitionMatrix= new Hashtable<>();

    private static Character convert(Character c){
        /*este metodo mapea cualquier letra a una 'a'
        //y cualquier numero a '1'
        para tener la matriz de transicion con menos columnas*/
        int asciiChar = (int )c;

        if (asciiChar==11 || asciiChar==9)
            return ' ';

        if ((asciiChar>=65 && asciiChar<=69)||(asciiChar>=71 && asciiChar<=90)||(asciiChar>=97 && asciiChar<=104)||(asciiChar>=106 && asciiChar<=122)){
            //{universo de letras minusculas y mayusculas} - {i} -{F}
            return 'a';
        }
        if (asciiChar>= 48 && asciiChar<=57){
            //numero de 0-9
            return '1';
        }



        return c;
    }

    public static void addTransition(Integer state, Character symbol, Integer nextState, SemanticAction semanticAction){
        Dupla pair = new Dupla(nextState, semanticAction);

        if (transitionMatrix.containsKey(state)) {
            transitionMatrix.get(state).put(symbol,pair);
        }else{
            transitionMatrix.put(state,new Hashtable<Character, Dupla>());
            transitionMatrix.get(state).put(symbol,pair);
        }

    }

    public static Dupla getPair(Integer state, Character symbol){
        return  (transitionMatrix.get(state)).get(symbol);
    }

    public static SemanticAction getSemanticAction(Integer state, Character symbol){
        return  (transitionMatrix.get(state)).get(convert(symbol)).getSemanticActions();
    }

    public static Integer getNextState(Integer state, Character symbol){

        return  (transitionMatrix.get(state)).get(convert(symbol)).getState();
    }

}
