package sample.classes;

import java.io.*;

public class CsvWriter {
    private static final char SEPARATOR = ';';
    public void writeCsv(String fileName, long[] points){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < points.length; i++){
            if (points[i]!=0){
                sb.append(points[i]).append(SEPARATOR);}
        }
        File file = new File(fileName);
        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(fw)){
            PrintWriter pw = new PrintWriter(bw);
            pw.print(sb.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
