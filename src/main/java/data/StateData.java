package data;

import lombok.Getter;

import java.util.List;

@Getter
public class StateData {
    private String boardNotation;
    private char currentPlayerNotation;
    private boolean isEnd;
    private double stateValue;
    private List<Integer> childrenHash;
    private Integer parentHash;
    private String cardStateNotation;
    private int currentDepth;

    public StateData(State state){

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
}
