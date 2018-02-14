import java.io.*;
import java.util.Random;

public class InputGenerator {
    File outputFile;
    int inputNumber;


    public InputGenerator (File outputFile, int n){
        this.outputFile = outputFile;
        inputNumber = n;
    }

    public void generate(){
        try {
            if(this.outputFile.exists()){
                this.outputFile.delete();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(this.outputFile));
            Random random = new Random();

            int i=0;
            while (i<inputNumber){
                int toWrite = random.nextInt(Integer.MAX_VALUE);
                writer.write(String.valueOf(toWrite%100)); // assume age data range 0-100
                writer.newLine();
                i++;
            }

            writer.close();
        } catch (IOException e){

        }
    }
}
