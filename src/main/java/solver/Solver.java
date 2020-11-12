package solver;

import data.State;
import engine.DataController;
import engine.LogicEngine;
import pojo.Card;
import pojo.Piece;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private static final double pieceValueWeight = 1;
    private static final double numberOfAvailbleMoveWeight = 1;


    private static void setupNextState(State copy, Card playedCard, String currentPlayerTurn) {
        if (LogicEngine.isEnd(copy.getBoard())) {
            copy.setEnd(true);
            copy.setWinner(LogicEngine.determineWinner(copy.getBoard()));
        } else {
            DataController.getNextCardState(copy.getCardState(), playedCard);
            if (currentPlayerTurn.equalsIgnoreCase("blue")) {
                copy.setCurrentPlayerTurn("red");
            } else {
                copy.setCurrentPlayerTurn("blue");
            }
            copy.setCurrentDepth(copy.getCurrentDepth() + 1);
        }

    }

    public static List<State> getNextStates(State parent, TranspositionTable table) {
        if (parent.isEnd()) {
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
                            if (!table.isExists(copy)) {
                                children.add(copy);
                            } else {
                                //table get child add parent
                                table.get(copy.hashCode()).addParent(parent);
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
                            if (!table.isExists(copy)) {
                                children.add(copy);
                            } else {
                                //table get child add parent
                                table.get(copy.hashCode()).addParent(parent);
                            }
                        }
                        DataController.flipMovement(c);
                    }
                }
            }
        }
        return children;
    }

    //first player is maximizer
    private static double getEndValue(State state, boolean isBlueFirstPlayer) {
        String winner = LogicEngine.determineWinner(state.getBoard());
        if ((winner.equalsIgnoreCase("red") && isBlueFirstPlayer) || (winner.equalsIgnoreCase("blue") && !isBlueFirstPlayer)) {
            return Double.NEGATIVE_INFINITY;
        } else if ((winner.equalsIgnoreCase("red") && !isBlueFirstPlayer) || (winner.equalsIgnoreCase("blue") && isBlueFirstPlayer)) {
            return Double.POSITIVE_INFINITY;
        } else {
            System.out.println("Bug at GetEndValue");
            return 0;
        }
    }

    public static int getNumberOfPossibleMove(State state){
        int totalMoves = 0;
        if (state.getCurrentPlayerTurn().equalsIgnoreCase("blue")){
            for (Piece p : state.getBoard().getBluePieces()){
                for (Card c : state.getCardState().getCurrentBlueHand()){
                    for (int i = 0; i < c.getAvailableMoves().size(); i++){
                        if (LogicEngine.isLegalMove(p.getCurrentCoordinate().addOffset(c.getAvailableMoves().get(i)), state.getBoard())){
                            totalMoves++;
                        }
                    }
                }
            }
        }else{
            for (Piece p : state.getBoard().getRedPieces()){
                for (Card c : state.getCardState().getCurrentRedHand()){
                    for (int i = 0; i < c.getAvailableMoves().size(); i++){
                        if (LogicEngine.isLegalMove(p.getCurrentCoordinate().addOffset(c.getAvailableMoves().get(i)), state.getBoard())){
                            totalMoves++;
                        }
                    }
                }
            }
        }
        return totalMoves;
    }

    public static double getStateValue(State state, boolean isBlueFirstPlayer){
        if (state.isEnd()){
            return getEndValue(state, isBlueFirstPlayer);
        }else{
            double stateValue = state.getBoard().getBluePieces().size();
            stateValue -= state.getBoard().getRedPieces().size();



            if (!isBlueFirstPlayer){
                stateValue *= -1;
                return stateValue;
            }else{
                return stateValue;
            }
        }
    }

//    public static double negamax() {
//
//    }
}
