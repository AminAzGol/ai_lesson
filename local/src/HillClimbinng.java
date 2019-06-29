import java.util.ArrayList;

public class HillClimbinng {
    static ArrayList<State> visited = new ArrayList<>();

    public static State run(ArrayList<Chemical> chemicals){
        State current_state= RandomState.create_random_state(chemicals);
        current_state.cost = Eval.eval(current_state);
        while(true){
            ArrayList<State> childs = RandomState.get_neighbor_states(current_state);
            for(State child: childs){
                if(!State.is_duplicate(visited, child))
                    child.cost = Eval.eval(child);
                    if(child.cost < current_state.cost)
                        current_state = child;
                        visited.add(child);
                        continue;
            }
            break;
        }
        return current_state;
    }
}
