package solver;

import data.State;
import engine.DataController;
import engine.LogicEngine;
import pojo.Card;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private static void setupNextState(State copy, Card playedCard, String currentPlayerTurn) {
        if (LogicEngine.isEnd(copy.getBoard())){
            copy.setEnd(true);
            copy.setWinner(LogicEngine.determineWinner(copy.getBoard()));
        }else{
            DataController.getNextCardState(copy.getCardState(), playedCard);
            if(currentPlayerTurn.equalsIgnoreCase("blue")){
                copy.setCurrentPlayerTurn("red");
            }else{
                copy.setCurrentPlayerTurn("blue");
            }
            copy.setCurrentDepth(copy.getCurrentDepth() + 1);
        }

    }

    public static List<State> getNextStates(State parent, TranspositionTable table) {
        if (parent.isEnd()){
            return new ArrayList<>();
        }
        List<State> children = new ArrayList<>();
        if (parent.getCurrentPlayerTurn().equalsIgnoreCase("blue")) {
            for (Card c : parent.getCardState().getCurrentBlueHand()) {
                for (int i = 0; i < c.getAvailableMoves().size(); i++) {
                    for (int j = 0; j < parent.getBoard().getBluePieces().size(); j++) {
                        State copy = DataController.offSpring(parent);
                        if (LogicEngine.movePiece(copy.getBoard(), copy.getBoard().getBluePieces().get(j), c, i)) {
                            setupNextState(copy, c, copy.getCurrentPlayerTurn());
                            if (!table.isExists(copy)){
                                children.add(copy);
                            }
                        }
                    }
                }
            }
        } else {
            for (Card c : parent.getCardState().getCurrentRedHand()) {
                for (int i = 0; i < c.getAvailableMoves().size(); i++) {
                    for (int j = 0; j < parent.getBoard().getRedPieces().size(); j++) {
                        State copy = DataController.offSpring(parent);
                        DataController.flipMovement(c);
                        if (LogicEngine.movePiece(copy.getBoard(), copy.getBoard().getRedPieces().get(j), c, i)) {
                            setupNextState(copy, c, copy.getCurrentPlayerTurn());
                            if (!table.isExists(copy)){
                                children.add(copy);
                            }
                        }
                        DataController.flipMovement(c);
                    }
                }
            }
        }
        return children;
    }


}
