package solver;

import data.State;
import data.StateData;
import engine.DataController;
import engine.LogicEngine;
import engine.IOEngine;
import pojo.Card;
import pojo.Piece;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solver {

    private static final double pieceValueWeight = 1;
    private static final double numberOfAvailbleMoveWeight = 1;
    private static final double discountValue = 0.1;


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
        if (parent.isEnd() ) {
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
                            copy.updateHash();
                            if (!table.isExists(copy)) {
                                children.add(copy);
//                                table.put(copy);
                            } else {
                                //table get child add parent
                                table.get(copy.getHash()).addParent(parent);
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
                            copy.updateHash();
                            if (!table.isExists(copy)) {
                                children.add(copy);
//                                table.put(copy);
                            } else {
                                //table get child add parent
                                table.get(copy.getHash()).addParent(parent);
                            }
                        }
                        DataController.flipMovement(c);
                    }
                }
            }
        }
        parent.setChildrenHash(children);
        return children;
    }

    //first player is maximizer
    private static double getEndValue(State state) {
        String winner = LogicEngine.determineWinner(state.getBoard());

        if (winner.equalsIgnoreCase("red")) {
            return Double.NEGATIVE_INFINITY;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    public static void solveFromRoot(State root) throws IOException {
        Queue<State> queue = new LinkedList<>();
        queue.add(root);
        solve(queue, new TranspositionTable());
    }

    public static void solve(Queue<State> queue, TranspositionTable table) throws IOException {
        List<StateData> toSave = new ArrayList<>();
        while(!queue.isEmpty() && queue.size() <= 2500){
            State first = queue.peek();
            if (!table.isExists(first)){
                table.put(first);
                if (first.isEnd()){
                    first.setStateValue(evaluateState(first));
                    toSave.add(new StateData(first));
                }else{
                    List<State> children = getNextStates(first,table);
                    queue.addAll(children);
                    toSave.add(new StateData(first));
                }
            }
            queue.remove();
        }
        System.out.println("Transposition Table size : " + table.getStateHashMapping().size());
        System.out.println("List to Save size: " + toSave.size());
        IOEngine.writeToFile(toSave, "C:\\Onitama State Table\\Table.txt");
        List<StateData> queueToSave = new ArrayList<>();
        for (State s : queue){
            queueToSave.add(new StateData(s));
        }
        IOEngine.writeToFile(queueToSave, "C:\\Onitama State Table\\Queue.txt");
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





    public static double evaluateState(State state) {
        if (state.isEnd()) {
            return getEndValue(state);
        } else {
            /*value for maximizer =
                (Amount of maximizer pieces * weight) - (Amount of minimizer pieces * weight)
                 + (number of possible moves for maximizer - number of possible moves for minimizer)
            */
            double stateValue = state.getBoard().getBluePieces().size() * pieceValueWeight;
            stateValue -= state.getBoard().getRedPieces().size() * pieceValueWeight;
            return stateValue;
        }
    }

    public static double evaluateWinOnly(State state) {
        if (state.isEnd()) {
            return getEndValue(state);
        } else {
            return 0;
        }
    }

    public static double minimax(State state, int depth, boolean isBlue, TranspositionTable table) {
        if (depth == 0) {
            double value = evaluateState(state);
            state.setStateValue(value);
            return value;
        }
        if (state.isEnd()) {
            double value = evaluateState(state);
            state.setStateValue(value);
            return value;
        }
        if (isBlue){
            double value = Double.NEGATIVE_INFINITY;
            List<State> children = getNextStates(state, table);
            for (State child : children){
                value = Math.max(value, minimax(child, depth - 1, false, table));
            }
            return value;
        }else{
            double value = Double.POSITIVE_INFINITY;
            List<State> children = getNextStates(state, table);
            for (State child : children){
                value = Math.min(value, minimax(child, depth - 1, true, table));
            }
            return value;
        }



//        if (depth == 0) {
//            double value = evaluateState(state);
//            state.setStateValue(value);
//            return value;
//        }
//        if (state.isEnd()) {
//            double value = evaluateState(state);
//            state.setStateValue(value);
//            return value;
//        }
//        double value = Double.POSITIVE_INFINITY;
//        List<State> children = getNextStates(state, table);
//        for (State child : children) {
//            value = Math.min(value, -minimax(child, depth - 1, !isBlue, table));
//        }
//        state.setStateValue(value);
//        return value;
    }
}
