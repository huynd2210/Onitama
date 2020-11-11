import data.CardState;
import data.StateData;
import engine.LogicEngine;
import pojo.Board;
import pojo.Card;
import pojo.CardList;
import solver.Solver;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args) {
//        Board board = new Board();
//        board.printBoard();
//        LogicEngine.movePiece(board, board.getBluePieces().get(0), CardList.tigerCard, 1);
//        board.printBoard();

        CardState cardState = new CardState(Arrays.asList(CardList.tigerCard, CardList.crabCard), Arrays.asList(CardList.rabbitCard, CardList.craneCard), CardList.horseCard);

        StateData root = new StateData(cardState);
        root.printState();
        System.out.println("------------------");
        List<StateData> children = Solver.getNextStates(root);
        for (StateData s : children) {
            s.printState();
        }
    }
}
