package AnalizadorLexico.StateMachine;

import AnalizadorLexico.SemanticAction.SemanticAction;

public class Dupla {
    private Integer state;
    private SemanticAction semanticActions;

    public Dupla(Integer state, SemanticAction semanticActions) {
        this.state = state;
        this.semanticActions = semanticActions;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public SemanticAction getSemanticActions() {
        return semanticActions;
    }

    public void setSemanticActions(SemanticAction semanticActions) {
        this.semanticActions = semanticActions;
    }
}
