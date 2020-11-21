package data;

import engine.LogicEngine;
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
    private List<Integer> childrenHash;
    private List<Integer> parentHash;
    private CardState cardState;
    private int currentDepth;
    private int hash;

    public State(State other) {
        this.cardState = new CardState(other.cardState);
        this.board = new Board(other.getBoard());
        this.currentPlayerTurn = other.currentPlayerTurn;
        this.winner = other.winner;
        this.isEnd = other.isEnd;
        this.stateValue = other.stateValue;
        copyChildren(other);
        this.parentHash = new ArrayList<>();
        this.parentHash.addAll(other.parentHash);
        this.currentDepth = other.currentDepth;
        this.hash = other.hash;
    }

    //Init Root
    public State(CardState cardState) {
        this.cardState = cardState;
        this.board = new Board();
        determineFirstPlayer();
        this.winner = "none";
        this.isEnd = false;
        this.stateValue = 0;
        this.childrenHash = new ArrayList<>();
        this.parentHash = new ArrayList<>();
        this.currentDepth = 0;
        this.hash = this.hashCode();
    }

    public State(Board board, CardState cardState, String currentPlayerTurn){
        this.cardState = cardState;
        this.board = board;
        this.currentPlayerTurn = currentPlayerTurn;
        this.winner = "none";
        this.isEnd = LogicEngine.isEnd(board);
        this.stateValue = 0;
        this.childrenHash = new ArrayList<>();
        this.parentHash = new ArrayList<>();
        this.currentDepth = 0;
        this.hash = this.hashCode();
    }

    private void copyChildren(State other) {
        this.childrenHash = new ArrayList<>();
        this.childrenHash.addAll(other.childrenHash);
    }

    public void printState() {
        this.board.printBoard();
        System.out.println(this.currentPlayerTurn);
        System.out.println(this.winner);
        System.out.println(this.isEnd);
        System.out.println(this.stateValue);
    }

    public void printGameState() {
        this.board.printBoard();
        System.out.println(this.currentPlayerTurn);
        this.cardState.print();
        System.out.println(this.stateValue);

    }

    public void determineFirstPlayer() {
        if (this.cardState.getCurrentNeutralCard().getColor().equalsIgnoreCase("blue")) {
            this.currentPlayerTurn = "blue";
        } else {
            this.currentPlayerTurn = "red";
        }
    }

    public void setChildrenHash(List<State> children) {
        for (State s : children) {
            this.childrenHash.add(s.hash);
        }
    }

    public void addParent(State parent) {
        this.parentHash.add(parent.hash);
    }

    public void updateHash() {
        this.hash = this.hashCode();
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
