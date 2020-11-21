package AI;

import data.State;
import lombok.Getter;
import solver.Solver;
import solver.TranspositionTable;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Getter
public class AI {
    TranspositionTable table;
    int depth;
    boolean isBlue;

    public AI(TranspositionTable table, int depth, boolean isBlue) {
        this.table = table;
        System.out.println("sadsadasddsadas");
        System.out.println(isBlue);
        this.isBlue = isBlue;
        this.depth = depth;
    }


    private State detectImmediateWin(List<State> possibleMoves) {
        for (State s : possibleMoves) {
            Solver.minimax(s, 1, this.isBlue, this.table);
        }
        State bestMove = possibleMoves
                .stream()
                .min(Comparator.comparing(State::getStateValue))
                .orElseThrow(NoSuchElementException::new);
        if (bestMove.getStateValue() == Double.NEGATIVE_INFINITY && this.isBlue) {
            return bestMove;
        } else if (bestMove.getStateValue() == Double.POSITIVE_INFINITY && !this.isBlue) {
            return bestMove;
        } else {
            return null;
        }
    }

    public State play(State currentState) {
        List<State> possibleMoves = Solver.getNextStates(currentState, this.table);
        if (detectImmediateWin(possibleMoves) != null) {
            return detectImmediateWin(possibleMoves);
        } else {
            for (State s : possibleMoves) {
                Solver.minimax(s, this.depth, this.isBlue, this.table);
            }
            if (isBlue) {
                return possibleMoves
                        .stream()
                        .max(Comparator.comparing(State::getStateValue))
                        .orElseThrow(NoSuchElementException::new);
            } else {
                return possibleMoves
                        .stream()
                        .min(Comparator.comparing(State::getStateValue))
                        .orElseThrow(NoSuchElementException::new);
            }
        }
    }


    public State playTest(State currentState) {
        return null;
    }


}
