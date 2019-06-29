import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Chemical> chems = ReadInput.read_file();
        State state = HillClimbinng.run(chems);
        System.out.println(state);
    }
}
