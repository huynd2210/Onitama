package data;

import lombok.*;
import pojo.Board;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class StateData {
    private Board board;
    private String currentPlayerTurn;
    private String winner;
    private boolean isEnd;
    private double stateValue;
    private List<StateData> children;
    private StateData parent;
    private CardState cardState;
//    private int currentDepth;

    public StateData(StateData other){
        this.cardState = new CardState(other.cardState);
        this.board = new Board(other.getBoard());
        this.currentPlayerTurn = other.currentPlayerTurn;
        this.winner = other.winner;
        this.isEnd = other.isEnd;
        this.stateValue = other.stateValue;
        copyChildren(other);
        this.parent = other.parent;
//        this.currentDepth = other.currentDepth;
    }

    //Init Root
    public StateData(CardState cardState){
        this.cardState = cardState;
        this.board = new Board();
        determineFirstPlayer();
        this.winner = "none";
        this.isEnd = false;
        this.stateValue = 0;
        this.children = new ArrayList<>();
        this.parent = null;
    }

    private void copyChildren(StateData other){
        this.children = new ArrayList<>();
        for (StateData sd : other.children){
            this.children.add(new StateData(sd));
        }
    }

    public void printState(){
        this.board.printBoard();
        System.out.println(this.currentPlayerTurn);
        System.out.println(this.winner);
        System.out.println(this.isEnd);
        System.out.println(this.stateValue);
    }

    public void determineFirstPlayer(){
        if (this.cardState.getCurrentNeutralCard().getColor().equalsIgnoreCase("blue")){
            this.currentPlayerTurn = "blue";
        }else{
            this.currentPlayerTurn = "red";
        }
    }

    public void setParent(StateData parent){
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateData)) return false;

        StateData stateData = (StateData) o;

        if (isEnd != stateData.isEnd) return false;
        if (board != null ? !board.equals(stateData.board) : stateData.board != null) return false;
        if (currentPlayerTurn != null ? !currentPlayerTurn.equals(stateData.currentPlayerTurn) : stateData.currentPlayerTurn != null)
            return false;
        return cardState != null ? cardState.equals(stateData.cardState) : stateData.cardState == null;
    }

    @Override
    public int hashCode() {
        int result = board != null ? board.hashCode() : 0;
        result = 31 * result + (currentPlayerTurn != null ? currentPlayerTurn.hashCode() : 0);
        result = 31 * result + (isEnd ? 1 : 0);
        result = 31 * result + (cardState != null ? cardState.hashCode() : 0);
        return result;
    }
}
