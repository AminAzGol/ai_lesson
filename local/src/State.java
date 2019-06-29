import java.util.ArrayList;

public class State {
    ArrayList<Tank> tanks;
    int cost;
    public State(){
        tanks = new ArrayList<>();
    }

    public static boolean is_duplicate(ArrayList<State> state_arr, State state){
        top_loop:
        for(State other_state: state_arr){
            if(other_state == state)
                return true;
            for(Tank tank: state.tanks){
                Tank other_state_tank = new Tank();
                for(Chemical chem: tank.chemicals){

                    //find the tank in other state that has this chem
                    if(other_state_tank.chemicals.size() == 0){
                        for(Tank other_tank: other_state.tanks){
                            if(other_tank.chemicals.indexOf(chem) != -1){
                                other_state_tank = other_tank;
                                break;
                            }
                        }
                    }

                    //if not all chemicals in this tank equals all in other tank then these states are not equal
                    if(other_state_tank.chemicals.indexOf(chem) == -1){
                        continue top_loop;
                    }
                }
            }
            //if all the tanks had the same chemicals then the code won't continue and reaches here
            //and this is mean that states are equal.
            return true;
        }
        return false;
    }
    public static State copy_state(State state){
        State new_state = new State();
        for(Tank copy_tank: state.tanks){
            Tank new_tank = new Tank();
            new_state.tanks.add(new_tank);
            new_tank.chemicals.addAll(copy_tank.chemicals);
        }
        return new_state;
    }

    @Override
    public String toString() {
        String s = "";
        for(Tank tank: tanks)
            s += tank.toString() + "\n";
        return s;
    }

    @Override
    public boolean equals(Object obj) {

        return super.equals(obj);
    }
}
