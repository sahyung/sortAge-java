import java.io.*;

public class Main {

    public static void main(String[] args) {
        File inputFile1 = new File("product.tsv");
        File inputFile2 = new File("user_pref.tsv");
        String uid = "12341";

        Recomend rcm = new Recomend(inputFile1, inputFile2, uid);
        rcm.run();
    }

}
