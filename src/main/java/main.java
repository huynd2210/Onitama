import data.CardState;
import data.State;
import engine.DataController;
import engine.LogicEngine;
import pojo.Board;
import pojo.Card;
import pojo.CardList;
import solver.Solver;
import solver.TranspositionTable;

import java.util.ArrayList;
import java.util.List;

public class main {

    public static void testPieceMovement() {
        Board board = new Board();
        board.printBoard();
        LogicEngine.movePiece(board, board.getBluePieces().get(0), CardList.tigerCard, 1, true);
        board.printBoard();
    }

    public static void testRoot() {
        List<Card> blue = new ArrayList<>();
        blue.add(CardList.tigerCard);
        blue.add(CardList.crabCard);
        List<Card> red = new ArrayList<>();
        red.add(CardList.horseCard);
        red.add(CardList.craneCard);
        CardState cardState = new CardState(blue, red, CardList.rabbitCard);
        State root = new State(cardState);
        root.printState();
        root.getCardState().print();
    }

    public static void testRootChildren() {
        List<Card> blue = new ArrayList<>();
        blue.add(CardList.tigerCard);
        blue.add(CardList.crabCard);
        List<Card> red = new ArrayList<>();
        red.add(CardList.horseCard);
        red.add(CardList.craneCard);
        CardState cardState = new CardState(blue, red, CardList.rabbitCard);
        State root = new State(cardState);
        System.out.println("------------------");
        TranspositionTable transpositionTable = new TranspositionTable();
        List<State> children = Solver.getNextStates(root, transpositionTable);
        for (State s : children) {
            System.out.println("State: ");
            s.printState();
            System.out.println("CardState: ");
            s.getCardState().print();
        }
    }

    public static void testRootGrandChildren() {
        List<Card> blue = new ArrayList<>();
        blue.add(CardList.tigerCard);
        blue.add(CardList.crabCard);
        List<Card> red = new ArrayList<>();
        red.add(CardList.horseCard);
        red.add(CardList.craneCard);

        CardState cardState = new CardState(blue, red, CardList.rabbitCard);

        State root = new State(cardState);
        System.out.println("------------------");
        List<State> children = Solver.getNextStates(root, new TranspositionTable());
        System.out.println("First Child: ");
        children.get(0).printState();
        children.get(0).getCardState().print();
        System.out.println("------------------");

        List<State> grandchildren = Solver.getNextStates(children.get(0), new TranspositionTable());
        for (State s : grandchildren) {
            System.out.println("State: ");
            s.printState();
            System.out.println("CardState: ");
            s.getCardState().print();
        }
    }

    public static void testTranspositionTable() {
        List<Card> blue = new ArrayList<>();
        blue.add(CardList.tigerCard);
        blue.add(CardList.crabCard);
        List<Card> red = new ArrayList<>();
        red.add(CardList.horseCard);
        red.add(CardList.craneCard);
        CardState cardState = new CardState(blue, red, CardList.rabbitCard);
        State root = new State(cardState);
        State notRoot = new State(cardState);
        TranspositionTable transpositionTable = new TranspositionTable();
        transpositionTable.put(root);
        System.out.println("Does root exists: ");
        System.out.println(transpositionTable.isExists(notRoot));
        List<State> children = Solver.getNextStates(root, new TranspositionTable());
        transpositionTable.put(children);
        boolean childrenExist = true;
        for (State s : children) {
            State copyOfChild = new State(s);
            if (!transpositionTable.isExists(copyOfChild)) {
                childrenExist = false;
            }
        }
        System.out.println("Does root children exists: ");
        System.out.println(childrenExist);
    }

    public static void testNotation() {
        List<Card> blue = new ArrayList<>();
        blue.add(CardList.tigerCard);
        blue.add(CardList.crabCard);
        List<Card> red = new ArrayList<>();
        red.add(CardList.horseCard);
        red.add(CardList.craneCard);
        CardState cardState = new CardState(blue, red, CardList.rabbitCard);
        State root = new State(cardState);
        System.out.println(DataController.boardToNotation(root.getBoard()));

        List<State> children = Solver.getNextStates(root, new TranspositionTable());
        for (State s : children) {
            System.out.println(DataController.boardToNotation(s.getBoard()));
        }
    }

    public static void testGetStateValue(){
        List<Card> blue = new ArrayList<>();
        blue.add(CardList.tigerCard);
        blue.add(CardList.crabCard);
        List<Card> red = new ArrayList<>();
        red.add(CardList.horseCard);
        red.add(CardList.craneCard);
        CardState cardState = new CardState(blue, red, CardList.rabbitCard);
        State root = new State(cardState);
        List<State> children = Solver.getNextStates(root,new TranspositionTable());
        for (State s : children){
            s.printState();
            s.getCardState().print();
            System.out.println("State Value: " +  Solver.evaluateState(s));
            System.out.println("Number of Possible Moves for blue:" + Solver.getNumberOfPossibleMove(s, true));
            System.out.println("Number of Possible Moves for red:" + Solver.getNumberOfPossibleMove(s, false));
            System.out.println("---------------");
        }
    }


    public static void main(String[] args) {
//        Board board = DataController.parseNotation("ppmpp/5/5/2P2/PPM1P");
//        board.printBoard();

        List<Card> blue = new ArrayList<>();
        blue.add(CardList.tigerCard);
        blue.add(CardList.crabCard);
        List<Card> red = new ArrayList<>();
        red.add(CardList.horseCard);
        red.add(CardList.craneCard);
        CardState cardState = new CardState(blue, red, CardList.rabbitCard);
        State root = new State(cardState);



//        Solver.getNextStates(root,new TranspositionTable());

//        System.out.println(Solver.negamax(root,6,true, new TranspositionTable()));

//        int depthMax = 5;
//        System.out.println("Iterative Negamax for blue for depth 5: ");
//        for (int i = 1; i <= depthMax; i++) {
//            System.out.println("Depth: " + i + " Score: " + Solver.negamax(root, i, true));
//        }




    }
}
