import java.util.ArrayList;

public class Chemical {
    ArrayList<Chemical> constrains;
    int name;

    public Chemical(int name) {
        constrains = new ArrayList<>();
        this.name = name;
    }

    public void add_constrins(Chemical c) {
        constrains.add(c);
    }

    @Override
    public String toString() {
        return name+"";
    }

    @Override
    public boolean equals(Object obj) {

        return this.name == ((Chemical)obj).name;
    }
}
