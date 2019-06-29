import java.util.ArrayList;
import java.util.Random;

public class RandomState {
    public static State create_random_state(ArrayList<Chemical> chemicals) {
        State random_state = new State();
        ArrayList<Integer> chemicals_indexes = new ArrayList();
        for (Chemical chem : chemicals) {
            chemicals_indexes.add(chemicals.indexOf(chem));
        }
        Random rand = new Random();
        while (chemicals_indexes.size() > 0) {
            Tank new_tank = new Tank();
            random_state.tanks.add(new_tank);
            while (true) {
                int rand_index = rand.nextInt(chemicals_indexes.size());
                int rand_chem_index = chemicals_indexes.get(rand_index);
                Chemical rand_chem = chemicals.get(rand_chem_index);
                new_tank.chemicals.add(rand_chem);
                chemicals_indexes.remove(chemicals_indexes.indexOf(rand_chem_index));
                if (chemicals_indexes.size() == 0)
                    break;
                int probability_of_new_tank = rand.nextInt(100);
                if (probability_of_new_tank > 50)
                    break;
            }
        }
        return random_state;
    }

    public static State perform_one_random_change(State state) {
        State new_state = new State();
        for (Tank tank : state.tanks) {
            Tank new_tank = new Tank();
            new_state.tanks.add(new_tank);
            new_tank.chemicals.addAll(tank.chemicals);
        }
        Random rand = new Random();
        Tank rand_tank = new_state.tanks.get(rand.nextInt(new_state.tanks.size()));
        Chemical rand_chem = rand_tank.chemicals.get(rand.nextInt(rand_tank.chemicals.size()));
        Tank new_rand_tank = new_state.tanks.get(rand.nextInt(new_state.tanks.size()));
        rand_tank.chemicals.remove(rand_chem);
        new_rand_tank.chemicals.add(rand_chem);
        if (rand_tank.chemicals.size() == 0) {
            new_state.tanks.remove(rand_tank);
        }
        return new_state;
    }

    private static void change_tank_and_add_new_state(ArrayList<State> neigbor_states, State new_state, Tank old_tank, Tank new_tank, Chemical chem) {
        old_tank.chemicals.remove(chem);
        new_tank.chemicals.add(chem);
        if (old_tank.chemicals.size() == 0) {
            new_state.tanks.remove(old_tank);
        }
        if (!State.is_duplicate(neigbor_states, new_state))
            neigbor_states.add(new_state);
    }

    public static ArrayList<State> get_neighbor_states(State state) {
        ArrayList<State> neigbor_states = new ArrayList<>();
        for (Tank tank : state.tanks) {
            for (Chemical chem : tank.chemicals) {
                // put the chemical in a new tank
                State new_state = State.copy_state(state);
                Tank new_tank = new Tank();
                new_state.tanks.add(new_tank);
                Tank old_tank = new_state.tanks.get(state.tanks.indexOf(tank));
                change_tank_and_add_new_state(neigbor_states, new_state, old_tank, new_tank, chem);

                //put chemical in another tank
                for (Tank other_tank : state.tanks) {
                    if (other_tank == tank)
                        continue;
                    new_state = State.copy_state(state);
                    old_tank = new_state.tanks.get(state.tanks.indexOf(tank));
                    new_tank = new_state.tanks.get(state.tanks.indexOf(other_tank));
                    change_tank_and_add_new_state(neigbor_states, new_state, old_tank, new_tank, chem);

                }
            }
        }
        return neigbor_states;
    }
}
