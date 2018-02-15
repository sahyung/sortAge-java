import java.io.*;
import java.util.*;

public class Sorter {
    File inputFile, outputFile;

    public Sorter(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void run() {
        try {
            reduce();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * reduce file into array
     * sorted by array keys
     *
     */
    public void reduce()
    {
        try
        {
            //long blockSize = getBlockSize();
            int idx = 0;
            String line = "";
            BufferedReader reader = new BufferedReader(new FileReader(this.inputFile));
            List<Integer> list = new ArrayList<Integer>(Collections.nCopies(100, 0));
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.outputFile));

            //read random data
            while ((line = reader.readLine()) != null) {
                idx = Integer.parseInt(line);
                list.set(idx, list.get(idx)+1);
            }
            reader.close();

            // write sorted output
            idx = 0;
            for (Iterator<Integer> i = list.iterator(); i.hasNext();) {
                int counter = i.next();
                for (int j = 0; j < counter; j++) {
                    bw.write(String.valueOf(idx)+"\n");
                }
                idx++;
            }
            bw.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public long getBlockSize(){
        long fileSize = inputFile.length();
        final int MAXTEMPFILES = 16;
        long blockSize = fileSize / MAXTEMPFILES;
        long available_memory = Runtime.getRuntime().freeMemory();
        if(blockSize < available_memory/2){
            blockSize = available_memory/2;
        } else {
            blockSize /= 2;
        }
        return blockSize;
    }

}
