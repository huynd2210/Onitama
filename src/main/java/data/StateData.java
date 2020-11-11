package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import pojo.Board;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StateData {
    private Board board;
    private String currentPlayerTurn;
    private String winner;
    private boolean isEnd;
    private double stateValue;
    private List<StateData> children;
    private StateData parent;
    private CardState cardState;

    public StateData(StateData other){
        this.cardState = new CardState(other.cardState);
        this.board = new Board(other.getBoard());
        this.currentPlayerTurn = other.currentPlayerTurn;
        this.winner = other.winner;
        this.isEnd = other.isEnd;
        this.stateValue = other.stateValue;
        copyChildren(other);
        this.parent = other.parent;

    }

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
}
