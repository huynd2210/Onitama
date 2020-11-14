package solver;

import data.State;
import engine.DataController;
import engine.LogicEngine;
import lombok.extern.java.Log;
import pojo.Card;
import pojo.Piece;

import javax.xml.crypto.Data;
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
                        if (LogicEngine.movePiece(copy.getBoard(), copy.getBoard().getBluePieces().get(j), c, i, true)) {
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
                        if (LogicEngine.movePiece(copy.getBoard(), copy.getBoard().getRedPieces().get(j), c, i, false)) {
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
        parent.setChildren(children);
        return children;
    }

    //first player is maximizer
    private static double getEndValue(State state, boolean isBlueMaximizer) {
        String winner = LogicEngine.determineWinner(state.getBoard());
        if ((winner.equalsIgnoreCase("red") && isBlueMaximizer) || (winner.equalsIgnoreCase("blue") && !isBlueMaximizer)) {
            return Double.NEGATIVE_INFINITY;
        } else if ((winner.equalsIgnoreCase("red") && !isBlueMaximizer) || (winner.equalsIgnoreCase("blue") && isBlueMaximizer)) {
            return Double.POSITIVE_INFINITY;
        } else {
            System.out.println("Bug at GetEndValue");
            return 0;
        }
    }

    public static int getNumberOfPossibleMove(State state, boolean isBlueTurn) {
        int totalMoves = 0;
        if (isBlueTurn) {
            for (Piece p : state.getBoard().getBluePieces()) {
                for (Card c : state.getCardState().getCurrentBlueHand()) {
                    for (int i = 0; i < c.getAvailableMoves().size(); i++) {
                        if (LogicEngine.isLegalMove(p.getCurrentCoordinate().addOffset(c.getAvailableMoves().get(i)), state.getBoard())
                                || LogicEngine.canCapture(p.getCurrentCoordinate().addOffset(c.getAvailableMoves().get(i)), state.getBoard(), true)) {
                            totalMoves++;
                        }
                    }
                }
            }
        } else {
            for (Piece p : state.getBoard().getRedPieces()) {
                for (Card c : state.getCardState().getCurrentRedHand()) {
                    DataController.flipMovement(c);
                    for (int i = 0; i < c.getAvailableMoves().size(); i++) {
                        if (LogicEngine.isLegalMove(p.getCurrentCoordinate().addOffset(c.getAvailableMoves().get(i)), state.getBoard())
                                || LogicEngine.canCapture(p.getCurrentCoordinate().addOffset(c.getAvailableMoves().get(i)), state.getBoard(), false)) {
                            totalMoves++;
                        }
                    }
                    DataController.flipMovement(c);
                }
            }
        }
        return totalMoves;
    }

    public static double getStateValue(State state, boolean isBlueMaximizer) {
        if (state.isEnd()) {
            return getEndValue(state, isBlueMaximizer);
        } else {
            /*value for maximizer =
                (Amount of maximizer pieces * weight) - (Amount of minimizer pieces * weight)
                 + (number of possible moves for maximizer - number of possible moves for minimizer)
            */
            double stateValue = state.getBoard().getBluePieces().size();
            stateValue -= state.getBoard().getRedPieces().size();
            stateValue += getNumberOfPossibleMove(state, isBlueMaximizer);
            stateValue -= getNumberOfPossibleMove(state, !isBlueMaximizer);
            if (!isBlueMaximizer) {
                stateValue *= -1;
                return stateValue;
            } else {
                return stateValue;
            }
        }
    }

    public static void explainStateValue(State state, boolean isBlueMaximizer){
        if (state.isEnd()) {
            System.out.println("Terminal State value is: " + getEndValue(state, isBlueMaximizer));
        } else {
            /*value for maximizer =
                (Amount of maximizer pieces * weight) - (Amount of minimizer pieces * weight)
                 + (number of possible moves for maximizer - number of possible moves for minimizer)
            */
            double stateValue = state.getBoard().getBluePieces().size();
            System.out.println("State Value from number of blue pieces is: " + state.getBoard().getBluePieces().size());
            stateValue -= state.getBoard().getRedPieces().size();
            System.out.println("Remaining redpieces: " + state.getBoard().getRedPieces().size());

            stateValue += getNumberOfPossibleMove(state, isBlueMaximizer);
            System.out.println("Plus number of Possible Move for blue: " + getNumberOfPossibleMove(state, isBlueMaximizer));
            stateValue -= getNumberOfPossibleMove(state, !isBlueMaximizer);
            System.out.println("minus number of possible move for red " + getNumberOfPossibleMove(state, !isBlueMaximizer));
        }
    }

    public static double negamax(State state, int depth, boolean isMaximizer) {
//        if (depth == 0){
//            state.printState();
//        }

        if (depth == 0) {
            double value = getStateValue(state, isMaximizer);
            state.setStateValue(value);
            state.printState();
            state.getCardState().print();
            System.out.println("----------");
            return value;
        }
        if (state.isEnd()) {
            double value = getEndValue(state, isMaximizer);
            state.setStateValue(value);
            return value;
        }
        double value = Double.NEGATIVE_INFINITY;
        List<State> children = getNextStates(state, new TranspositionTable());
        for (State child : children) {
            value = Math.max(value, -negamax(child, depth - 1, !isMaximizer));
        }
        state.setStateValue(value);
        return value;
    }
}
