package solver;

import data.CardState;
import data.StateData;
import engine.DataController;
import engine.LogicEngine;
import pojo.Board;
import pojo.Card;
import pojo.CardList;
import pojo.Piece;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class Solver {

    private static void setupNextState(StateData copy, Card playedCard, String currentPlayerTurn) {
        DataController.getNextCardState(copy.getCardState(), playedCard);
        if(currentPlayerTurn.equalsIgnoreCase("blue")){
            copy.setCurrentPlayerTurn("red");
        }else{
            copy.setCurrentPlayerTurn("blue");
        }
        copy.setCurrentDepth(copy.getCurrentDepth() + 1);
    }

    public static List<StateData> getNextStates(StateData parent) {
        List<StateData> children = new ArrayList<>();
        if (parent.getCurrentPlayerTurn().equalsIgnoreCase("blue")) {
            for (Card c : parent.getCardState().getCurrentBlueHand()) {
                for (int i = 0; i < c.getAvailableMoves().size(); i++) {
                    for (int j = 0; j < parent.getBoard().getBluePieces().size(); j++) {
                        StateData copy = DataController.offSpring(parent);
                        if (LogicEngine.movePiece(copy.getBoard(), copy.getBoard().getBluePieces().get(j), c, i)) {
                            setupNextState(copy, c, copy.getCurrentPlayerTurn());
                            children.add(copy);
                        }
                    }
                }
            }
        } else {
            for (Card c : parent.getCardState().getCurrentRedHand()) {
                for (int i = 0; i < c.getAvailableMoves().size(); i++) {
                    for (int j = 0; j < parent.getBoard().getRedPieces().size(); j++) {
                        StateData copy = DataController.offSpring(parent);
                        DataController.flipMovement(c);
                        if (LogicEngine.movePiece(copy.getBoard(), copy.getBoard().getRedPieces().get(j), c, i)) {
                            setupNextState(copy, c, copy.getCurrentPlayerTurn());
                            children.add(copy);
                        }
                        DataController.flipMovement(c);
                    }
                }
            }
        }
        return children;
    }


}
