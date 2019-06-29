import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteOutput {

    public void write_file(ArrayList<Tank> tanks) {
        String root = System.getProperty("user.dir");
        String fileName = root + "/out_" + System.currentTimeMillis();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            for(Tank tank: tanks){
                writer.append(tank.toString());
                writer.append("\n");
            }

            writer.close();

        }
        catch (IOException io){
            io.printStackTrace();
        }
    }
}
