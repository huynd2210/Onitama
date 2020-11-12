package solver;

import data.StateData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TranspositionTable {
    private Map<Integer, StateData> stateHashMapping;

    public TranspositionTable(){
        this.stateHashMapping = new HashMap<>();
    }

    public void put(StateData state){
        this.stateHashMapping.put(state.hashCode(), state);
    }

    public void put(List<StateData> stateList){
        for (StateData s : stateList){
            put(s);
        }
    }

    public boolean isExists(StateData state){
        return this.stateHashMapping.containsKey(state.hashCode());
    }
}
