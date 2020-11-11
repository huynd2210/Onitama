import data.CardState;
import data.StateData;
import engine.DataController;
import engine.LogicEngine;
import pojo.Board;
import pojo.Card;
import pojo.CardList;
import solver.Solver;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args) {
//        Board board = new Board();
//        board.printBoard();
//        LogicEngine.movePiece(board, board.getBluePieces().get(0), CardList.tigerCard, 1);
//        board.printBoard();


        List<Card> blue = new ArrayList<>();
        blue.add(CardList.tigerCard);
        blue.add(CardList.crabCard);
        List<Card> red = new ArrayList<>();
        red.add(CardList.horseCard);
        red.add(CardList.craneCard);

        CardState cardState = new CardState(blue, red, CardList.rabbitCard);

        StateData root = new StateData(cardState);
//        root.printState();
//        root.getCardState().print();
        System.out.println("------------------");
        List<StateData> children = Solver.getNextStates(root);


//        for (StateData s : children) {
//            System.out.println("State: ");
//            s.printState();
//            System.out.println("CardState: ");
//            s.getCardState().print();
//        }

        System.out.println("First Child: ");
        children.get(0).printState();
        children.get(0).getCardState().print();
        System.out.println("------------------");

        List<StateData> grandchildren = Solver.getNextStates(children.get(0));
        for (StateData s : grandchildren){
            System.out.println("State: ");
            s.printState();
            System.out.println("CardState: ");
            s.getCardState().print();
        }

    }
}
