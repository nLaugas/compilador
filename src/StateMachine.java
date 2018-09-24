
import java.util.Hashtable;

import static java.lang.Integer.MAX_VALUE;

public class StateMachine {
    public static final int FINAL_STATE = MAX_VALUE;
    private Hashtable<Integer, Hashtable<Character, Pair>> transitionMatrix;

    public StateMachine(){
        transitionMatrix = new Hashtable<>();
    }

    public void addTransition(Integer state, Character symbol, Pair pair){
        Hashtable<Character, Pair> aux = new Hashtable<>();
        aux.put(symbol,pair);
        transitionMatrix.put(state,aux);
    }

    public Pair getPair(Integer state, Character symbol){
        return  (transitionMatrix.get(state)).get(symbol);
    }

    public SemanticAction get(Integer state, Character symbol){
        return  (transitionMatrix.get(state)).get(symbol).getSemanticActions();
    }

    public Integer getState(Integer state, Character symbol){
        return  (transitionMatrix.get(state)).get(symbol).getState();
    }

}
