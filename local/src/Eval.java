import java.util.ArrayList;

public class Eval {
    public static int eval(State node) {
        int cost = 0;
        ArrayList<Tank> tanks = node.tanks;
        for (int i = 0; i < tanks.size(); i++) {
            cost += 1; // les tanks better answer
            Tank tank = tanks.get(i);
            for (Chemical chem : tank.chemicals) {
                for (Chemical chem2 : tank.chemicals) {
                    if (chem != chem2) {
                        if (chem.constrains.indexOf(chem2) > -1) {
                            cost += 10;
                        }
                    }
                }
            }
        }
        return cost;
    }
}
