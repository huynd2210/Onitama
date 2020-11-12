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
        LogicEngine.movePiece(board, board.getBluePieces().get(0), CardList.tigerCard, 1);
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
        List<State> children = Solver.getNextStates(root, new TranspositionTable());
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

    public static void testNotation(){
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
        for (State s : children){
            System.out.println(DataController.boardToNotation(s.getBoard()));
        }
    }

    public static void main(String[] args) {
//        Board board = DataController.parseNotation("ppmpp/5/5/2P2/PPM1P");
//        board.printBoard();

//        List<Card> blue = new ArrayList<>();
//        blue.add(CardList.tigerCard);
//        blue.add(CardList.crabCard);
//        List<Card> red = new ArrayList<>();
//        red.add(CardList.horseCard);
//        red.add(CardList.craneCard);
//        CardState cardState = new CardState(blue, red, CardList.rabbitCard);
//        State root = new State(cardState);
//        root.getBoard().printBoard();
//        cardState.print();
//        System.out.print("Number of Possible Moves :");
//        System.out.println(Solver.getNumberOfPossibleMove(root));

        testRootGrandChildren();
    }
}
