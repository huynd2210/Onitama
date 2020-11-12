package solver;

import data.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TranspositionTable {
    private Map<Integer, State> stateHashMapping;

    public TranspositionTable(){
        this.stateHashMapping = new HashMap<>();
    }

    public void put(State state){
        this.stateHashMapping.put(state.hashCode(), state);
    }

    public void put(List<State> stateList){
        for (State s : stateList){
            put(s);
        }
    }

    public State get(Integer hash){
        return this.stateHashMapping.get(hash);
    }

    public boolean isExists(State state){
        return this.stateHashMapping.containsKey(state.hashCode());
    }
}
