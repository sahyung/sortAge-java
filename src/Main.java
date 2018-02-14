import java.io.*;

public class Main {
    public static void main(String args[]){
        File inputFile = new File("input.txt");
        File outputFile = new File("output.txt");

        InputGenerator generator = new InputGenerator(inputFile, 1000); // generate random age
        generator.generate();
        Sorter sorter = new Sorter(inputFile, outputFile);
        sorter.run();
    }
}
