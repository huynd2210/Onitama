import data.CardState;
import data.StateData;
import engine.DataController;
import engine.LogicEngine;
import pojo.Board;
import pojo.Card;
import pojo.CardList;
import solver.Solver;
import solver.TranspositionTable;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
        StateData root = new StateData(cardState);
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
        StateData root = new StateData(cardState);
        System.out.println("------------------");
        List<StateData> children = Solver.getNextStates(root);
        for (StateData s : children) {
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

        StateData root = new StateData(cardState);
        System.out.println("------------------");
        List<StateData> children = Solver.getNextStates(root);
        System.out.println("First Child: ");
        children.get(0).printState();
        children.get(0).getCardState().print();
        System.out.println("------------------");

        List<StateData> grandchildren = Solver.getNextStates(children.get(0));
        for (StateData s : grandchildren) {
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
        StateData root = new StateData(cardState);
        StateData notRoot = new StateData(cardState);
        TranspositionTable transpositionTable = new TranspositionTable();
        transpositionTable.put(root);
        System.out.println("Does root exists: ");
        System.out.println(transpositionTable.isExists(notRoot));
        List<StateData> children = Solver.getNextStates(root);
        transpositionTable.put(children);
        boolean childrenExist = true;
        for (StateData s : children) {
            StateData copyOfChild = new StateData(s);
            if (!transpositionTable.isExists(copyOfChild)) {
                childrenExist = false;
            }
        }
        System.out.println("Does root children exists: ");
        System.out.println(childrenExist);
    }

    public static void main(String[] args) {
        testRootGrandChildren();

    }
}
