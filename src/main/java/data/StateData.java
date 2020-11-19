package data;

import engine.DataController;
import engine.StateDataProcessor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StateData {
    private int hash;
    private String boardNotation;
    private char currentPlayerNotation;
    private boolean isEnd;
    private double stateValue;
    private List<Integer> childrenHash;
    private List<Integer> parentHash;
    private String cardStateNotation;
    private int currentDepth;

    public StateData(State state){
        this.hash = state.getHash();
        this.boardNotation = DataController.boardToNotation(state.getBoard());
        this.currentPlayerNotation = StateDataProcessor.getCurrentPlayerNotation(state);
        this.isEnd = state.isEnd();
        this.stateValue = state.getStateValue();
        this.childrenHash = new ArrayList<>();
        this.childrenHash.addAll(state.getChildrenHash());
        this.parentHash = new ArrayList<>();
        this.parentHash.addAll(state.getParentHash());
        this.cardStateNotation = DataController.getCardStateNotation(state.getCardState());
        this.currentDepth = state.getCurrentDepth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateData)) return false;

        StateData stateData = (StateData) o;

        if (currentPlayerNotation != stateData.currentPlayerNotation) return false;
        if (isEnd != stateData.isEnd) return false;
        if (boardNotation != null ? !boardNotation.equals(stateData.boardNotation) : stateData.boardNotation != null)
            return false;
        return cardStateNotation != null ? cardStateNotation.equals(stateData.cardStateNotation) : stateData.cardStateNotation == null;
    }

    @Override
    public int hashCode() {
        int result = boardNotation != null ? boardNotation.hashCode() : 0;
        result = 31 * result + (int) currentPlayerNotation;
        result = 31 * result + (isEnd ? 1 : 0);
        result = 31 * result + (cardStateNotation != null ? cardStateNotation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.hash);
        sb.append(",");
        sb.append(this.boardNotation);
        sb.append(",");
        sb.append(this.currentPlayerNotation);
        sb.append(",");
        sb.append(this.isEnd);
        sb.append(",");
        sb.append(this.stateValue);
        sb.append(",");
        sb.append(this.childrenHash);
        sb.append(",");
        sb.append(this.parentHash);
        sb.append(",");
        sb.append(this.cardStateNotation);
        sb.append(",");
        sb.append(this.currentDepth);
        return sb.toString();
    }
}
