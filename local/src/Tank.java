import java.util.ArrayList;

public class Tank {
    ArrayList<Chemical> chemicals;
    public Tank(){
        chemicals = new ArrayList<>();
    }
    @Override
    public String toString() {
        String s = "[ ";

        for(Chemical chem: chemicals){
            s += chem.name;
            if(chemicals.indexOf(chem) != chemicals.size() - 1)
                s += ", ";
        }
        s+= "]";
        return s;
    }
}
