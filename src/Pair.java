public class Pair {
    private Integer state;
    private SemanticAction semanticActions;

    public Pair(Integer state, SemanticAction semanticActions) {
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
