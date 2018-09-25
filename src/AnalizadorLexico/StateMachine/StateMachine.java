package AnalizadorLexico.StateMachine;
import AnalizadorLexico.SemanticAction.SemanticAction;

import java.util.Hashtable;

import static java.lang.Integer.MAX_VALUE;

public final class StateMachine {
    public static final int FINAL_STATE = MAX_VALUE;
    public static final int INITIAL_STATE = 0;
    private static Hashtable<Integer, Hashtable<Character, Pair>> transitionMatrix= new Hashtable<>();


    public static void addTransition(Integer state, Character symbol, Integer nextState, SemanticAction semanticAction){
        Pair pair = new Pair(nextState, semanticAction);

        if (transitionMatrix.containsKey(state)) {
            transitionMatrix.get(state).put(symbol,pair);
        }else{
            transitionMatrix.put(state,new Hashtable<>());
            transitionMatrix.get(state).put(symbol,pair);
        }
 /*
        if (transitionMatrix.get(state) == null) {
            Hashtable<Character, Pair> aux = new Hashtable<>();
            Pair pair = new Pair(nextState, semanticAction);
            aux.put(symbol, pair);
            transitionMatrix.put(state, aux);
        }else{
            Pair pair = new Pair(nextState, semanticAction);
            transitionMatrix.get(state).put(symbol,pair);
        }

*/

    }

    public static Pair getPair(Integer state, Character symbol){
        return  (transitionMatrix.get(state)).get(symbol);
    }

    public static SemanticAction getSemanticAction(Integer state, Character symbol){
        return  (transitionMatrix.get(state)).get(symbol).getSemanticActions();
    }

    public static Integer getNextState(Integer state, Character symbol){
        return  (transitionMatrix.get(state)).get(symbol).getState();
    }

}
