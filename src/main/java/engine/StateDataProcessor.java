package engine;

import data.State;

public class StateDataProcessor {
    public static char getCurrentPlayerNotation(State state){
        if (state.getCurrentPlayerTurn().equalsIgnoreCase("blue")){
            return 'b';
        }else{
            return 'r';
        }
    }
}
