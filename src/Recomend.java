import java.io.*;
import java.lang.reflect.Array;
import java.security.Timestamp;
import java.util.*;

public class Recomend {
    String uid;
    File inputFile1, inputFile2;
    List<List<String>> listProduct, listUserPref;
    List<Double> recomendation = new ArrayList<Double>();
    List<Integer> topProduct = new ArrayList<Integer>();
    List<String> blacklist = new ArrayList<String>();

    public Recomend(File inputFile1, File inputFile2, String uid) {
        this.inputFile1 = inputFile1;
        this.inputFile2 = inputFile2;
        this.uid = uid;
    }

    public void run() {
        try {
            listProduct();
            getUserPref(this.uid);
            getRecomendation();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void getRecomendation(){
        try {
            double tmpScore, productScore;
            for(List<String> line: this.listUserPref) {
                productScore = getScoreProduct(line.get(1));
                double uScore = Double.parseDouble(line.get(2));
                int d = (int) (System.currentTimeMillis() / 1000L);
                int dayPassed = (int) (d - Integer.parseInt(line.get(3)))/(24*3600);
                String dps = String.valueOf(dayPassed)+".0";
                tmpScore = productScore * (1 + (uScore * Math.pow(0.95, Double.parseDouble(dps))));
                line.set(2, String.valueOf(tmpScore));

                this.recomendation.add(tmpScore);
                Collections.sort(this.recomendation);
                Collections.reverse(this.recomendation);
                if(recomendation.size() > 5){
                    this.recomendation.remove(5);
                }
            }

            for (Double d: this.recomendation){
                for(List<String> line: this.listUserPref) {
                    if(line.get(2).matches(String.valueOf(d))){
                        System.out.println(line.get(1));
                        this.blacklist.add(line.get(1));
                    }
                }
            }

            if(this.recomendation.size() < 5){
                int diff = 5 - this.recomendation.size(), c = 0;
                for (Integer tp: this.topProduct) {
                    for (List<String> line: this.listProduct){
                        if(c < diff){
                            if(line.get(1).matches(String.valueOf(tp))){
                                if(!this.blacklist.contains(line.get(0))){
                                    System.out.println(line.get(0));
                                    c++;
                                }
                            }
                        }
                    }
                }
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void getUserPref(String uid){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.inputFile2));
            List<List<String>> lines = new ArrayList<>();
            String lineInput = "";
            while ((lineInput = reader.readLine()) != null) {
                if(lineInput.matches(this.uid+"(.*)")){
                    String[] values = lineInput.split("\\t");
                    lines.add(Arrays.asList(values));
                }
            }
            reader.close();
            this.listUserPref = lines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Double getScoreProduct(String pid) {
        try{
            for(List<String> line: this.listProduct) {
                if(line.get(0).matches(pid)){
                    return Double.parseDouble(line.get(1));
                }
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return 0.0;
    }

    public void listProduct(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.inputFile1));
            List<List<String>> lines = new ArrayList<>();
            String lineInput = "";
            while ((lineInput = reader.readLine()) != null) {
                String[] values = lineInput.split("\\t");
                lines.add(Arrays.asList(values));
                this.topProduct.add(Integer.parseInt(values[1]));
            }
            reader.close();
            this.listProduct = lines;
            Collections.sort(this.topProduct);
            Collections.reverse(this.topProduct);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
