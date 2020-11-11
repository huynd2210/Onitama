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
//    public static List<StateData> getNextStates(StateData parent) {
//        List<StateData> children = new ArrayList<>();
//        if (parent.getCurrentPlayerTurn().equalsIgnoreCase("blue")) {
//            for (int i = 0; i < parent.getBoard().getBluePieces().size(); i++) {
//                StateData copy = DataController.offSpring(parent);
//                LogicEngine.movePiece(copy.getBoard(), copy.getBoard().getBluePieces().get(i), CardList.tigerCard, 1);
//                children.add(copy);
//            }
//        }else{
//            for (int i = 0; i < parent.getBoard().getRedPieces().size(); i++){
//                StateData copy = DataController.offSpring(parent);
//                DataController.flipMovement(CardList.tigerCard);
//                LogicEngine.movePiece(copy.getBoard(), copy.getBoard().getRedPieces().get(i), CardList.tigerCard, 1);
//                children.add(copy);
//                DataController.flipMovement(CardList.tigerCard);
//            }
//
//        }
//
//        return children;
//    }

    public static List<StateData> getNextStates(StateData parent) {
        List<StateData> children = new ArrayList<>();
        if (parent.getCurrentPlayerTurn().equalsIgnoreCase("blue")) {
            for (Card c : parent.getCardState().getCurrentBlueHand()) {
                for (int i = 0; i < c.getAvailableMoves().size(); i++) {
                    for (int j = 0; j < parent.getBoard().getBluePieces().size(); j++) {
                        StateData copy = DataController.offSpring(parent);
                        if (LogicEngine.movePiece(copy.getBoard(), copy.getBoard().getBluePieces().get(j), c, i)){
//                            DataController.getNextCardState(parent.getCardState(), c);
                            children.add(copy);
                        }
                    }
                }
            }
        }else{
            for (Card c : parent.getCardState().getCurrentRedHand()) {
                for (int i = 0; i < c.getAvailableMoves().size(); i++) {
                    for (int j = 0; j < parent.getBoard().getRedPieces().size(); j++) {
                        StateData copy = DataController.offSpring(parent);
                        DataController.flipMovement(c);
                        if (LogicEngine.movePiece(copy.getBoard(), copy.getBoard().getRedPieces().get(j), c, i)){
//                            DataController.getNextCardState(parent.getCardState(), c);
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
