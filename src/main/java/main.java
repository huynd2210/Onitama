import data.CardState;
import data.State;
import data.StateData;
import engine.DataController;
import engine.IOEngine;
import engine.LogicEngine;
import game.Game;
import lombok.extern.java.Log;
import pojo.Board;
import pojo.Card;
import pojo.CardList;
import solver.Solver;
import solver.TranspositionTable;

import java.io.IOException;
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

    public static void testGetStateValue() {
        List<Card> blue = new ArrayList<>();
        blue.add(CardList.tigerCard);
        blue.add(CardList.crabCard);
        List<Card> red = new ArrayList<>();
        red.add(CardList.horseCard);
        red.add(CardList.craneCard);
        CardState cardState = new CardState(blue, red, CardList.rabbitCard);
        State root = new State(cardState);
        List<State> children = Solver.getNextStates(root, new TranspositionTable());
        for (State s : children) {
            s.printState();
            s.getCardState().print();
            System.out.println("State Value: " + Solver.evaluateState(s));
            System.out.println("Number of Possible Moves for blue:" + Solver.getNumberOfPossibleMove(s, true));
            System.out.println("Number of Possible Moves for red:" + Solver.getNumberOfPossibleMove(s, false));
            System.out.println("---------------");
        }
    }


    public static void testStateData() {
        List<Card> blue = new ArrayList<>();
        blue.add(CardList.tigerCard);
        blue.add(CardList.crabCard);
        List<Card> red = new ArrayList<>();
        red.add(CardList.horseCard);
        red.add(CardList.craneCard);
        CardState cardState = new CardState(blue, red, CardList.rabbitCard);
        State root = new State(cardState);
        Solver.getNextStates(root, new TranspositionTable());
        for (Integer s : root.getChildrenHash()) {
            System.out.println(s);
        }
        StateData tmp = new StateData(root);
        System.out.println(tmp);
    }

    public static void main(String[] args) throws IOException {
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
        Solver.solveFromRoot(root);



//        IOEngine.readTest("C:\\Onitama State Table\\Table.txt");


////        Game game = new Game(cardState, "blue");
////
//        List<State> children = Solver.getNextStates(root, new TranspositionTable());
//        for (State s : children) {
//            s.getBoard().printBoard();
//            System.out.println(Solver.evaluateState(s));
//        }
//        System.out.println("--------------");
//        System.out.println(Solver.minimax(root, 1, true, new TranspositionTable()));
//        System.out.println("--------------");
//
//        State child = children.get(children.size() - 1);
//        List<State> grandChildren = Solver.getNextStates(child, new TranspositionTable());
//        for (State s : grandChildren) {
//            s.getBoard().printBoard();
//            System.out.println(Solver.minimax(child, 3, false, new TranspositionTable()));
//        }
//        System.out.println("--------------");
//        System.out.println(Solver.minimax(child, 1, false, new TranspositionTable()));


//        Board board = DataController.parseNotation("pp1pm/2M2/5/5/PP1PP");
//        board.printBoard();
//        System.out.println(LogicEngine.isEnd(board));
//
    }
}
