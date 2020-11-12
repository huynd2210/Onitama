package data;

import lombok.*;
import pojo.Board;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class State {
    private Board board;
    private String currentPlayerTurn;
    private String winner;
    private boolean isEnd;
    private double stateValue;
    private List<State> children;
    private List<State> parent;
    private CardState cardState;
    private int currentDepth;

    public State(State other){
        this.cardState = new CardState(other.cardState);
        this.board = new Board(other.getBoard());
        this.currentPlayerTurn = other.currentPlayerTurn;
        this.winner = other.winner;
        this.isEnd = other.isEnd;
        this.stateValue = other.stateValue;
        copyChildren(other);
        this.parent = other.parent;
        this.currentDepth = other.currentDepth;
    }

    //Init Root
    public State(CardState cardState){
        this.cardState = cardState;
        this.board = new Board();
        determineFirstPlayer();
        this.winner = "none";
        this.isEnd = false;
        this.stateValue = 0;
        this.children = new ArrayList<>();
        this.parent = new ArrayList<>();
        this.currentDepth = 0;
    }

    private void copyChildren(State other){
        this.children = new ArrayList<>();
        for (State sd : other.children){
            this.children.add(new State(sd));
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

    public void addParent(State parent){
        this.parent.add(parent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (isEnd != state.isEnd) return false;
        if (board != null ? !board.equals(state.board) : state.board != null) return false;
        if (currentPlayerTurn != null ? !currentPlayerTurn.equals(state.currentPlayerTurn) : state.currentPlayerTurn != null)
            return false;
        return cardState != null ? cardState.equals(state.cardState) : state.cardState == null;
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
