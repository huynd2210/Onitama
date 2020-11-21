package game;

import AI.AI;
import data.CardState;
import data.State;
import engine.LogicEngine;
import pojo.Card;
import pojo.Coordinate;
import solver.TranspositionTable;

import java.util.Scanner;

public class Game {
    State currentState;
    AI bot;
    String playerColor;
    boolean isCurrentTurnPlayer;
    final int botDepth = 4;

    public Game(CardState cardState, String playerColor) {
        currentState = new State(cardState);
        this.playerColor = playerColor;
        isCurrentTurnPlayer = !isBotFirstPlayer();

        if (playerColor.equalsIgnoreCase("blue")) {
            this.bot = new AI(new TranspositionTable(), botDepth, false);
        } else {
            this.bot = new AI(new TranspositionTable(), botDepth, true);
        }

        startGame();
    }

    public boolean isBotFirstPlayer() {
        if (this.currentState.getCardState().getCurrentNeutralCard().getColor().equalsIgnoreCase("blue") && playerColor.equalsIgnoreCase("blue")) {
            return false;
        } else
            return this.currentState.getCardState().getCurrentNeutralCard().getColor().equalsIgnoreCase("red") && !playerColor.equalsIgnoreCase("red");
    }

    public void acceptInput(Coordinate origin, Coordinate destination, Card c) {
        boolean isPlayerBlue = false;
        if (playerColor.equalsIgnoreCase("blue")) {
            isPlayerBlue = true;
        }
        if (LogicEngine.isPlayerMoveLegal(origin, destination, currentState.getBoard(), playerColor, c)) {
            LogicEngine.makePlayerMove(currentState, origin, destination, c, isPlayerBlue);
        }
    }

    private void processInput(String input) {
        String[] tokens = input.split(",");
        Coordinate origin = new Coordinate(Character.getNumericValue(tokens[0].charAt(0)), Character.getNumericValue(tokens[0].charAt(1)));
        Coordinate destination = new Coordinate(Character.getNumericValue(tokens[1].charAt(0)), Character.getNumericValue(tokens[1].charAt(1)));
        if (playerColor.equalsIgnoreCase("blue")) {
            Card c = currentState.getCardState().getCurrentBlueHand().get(Character.getNumericValue(tokens[2].charAt(0)));
            acceptInput(origin, destination, c);
        } else {
            Card c = currentState.getCardState().getCurrentRedHand().get(Character.getNumericValue(tokens[2].charAt(0)));
            acceptInput(origin, destination, c);
        }
    }

    public void startGame() {
        Scanner sc = new Scanner(System.in);

        while (!currentState.isEnd()) {
            System.out.println("Current State:");
            this.currentState.printGameState();
            if (isCurrentTurnPlayer) {
                System.out.println("Your Turn");
                String input = sc.nextLine();
                processInput(input);
                this.isCurrentTurnPlayer = false;
            } else {
                this.currentState = this.bot.playTest(this.currentState);
                this.isCurrentTurnPlayer = true;
            }
        }
        System.out.println("Winner: ");
        System.out.println(currentState.getWinner());
    }
}
