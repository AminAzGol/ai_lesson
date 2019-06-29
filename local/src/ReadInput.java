import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadInput {

    static ArrayList<Chemical> chemicals;

    public static ArrayList<Chemical> read_file() {
        String root = System.getProperty("user.dir");
        BufferedReader reader;
        chemicals = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(
                   root + "/inputs/1.txt" ));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                parse_line(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return chemicals;
    }
    private static void parse_line(String line){
        String[] sub_strs = line.split("\\.");
        boolean first_chem = true;
        Chemical new_chem = new Chemical(0);
        for (String sub_str:
                sub_strs) {
            int num = Integer.parseInt(sub_str);
            if(first_chem){
                new_chem = new Chemical(num);
                chemicals.add(new_chem);
                first_chem = false;

            }
            else{
                new_chem.add_constrins(new Chemical(num));
            }
        }
    }

}