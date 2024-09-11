package univr.ing.carconfigurator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Valutazione {

    private String userID;
    private String quotationID;

    public Valutazione(String userID, String quotationID) {
        this.userID = userID;
        this.quotationID = quotationID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getQuotationID() {
        return quotationID;
    }

    public void setQuotationID(String quotationID) {
        this.quotationID = quotationID;
    }

    public void save() {
        try {
            FileWriter fw = new FileWriter("database/valutazioni.csv", true);
            String eval = this.userID +","+this.quotationID+",\n";
            fw.append(eval);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            List<String> evaluationFile = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("database/valutazioni.csv"));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // Rimuovo dal file la riga notificata
                if (currentLine.split(",")[0].equals(this.userID) &&
                currentLine.split(",")[1].equals(this.quotationID)) {

                } else {
                    evaluationFile.add(currentLine);
                }
            }
            reader.close();

            // Riscrivo il file
            BufferedWriter writer = new BufferedWriter(new FileWriter("database/valutazioni.csv"));
            for (String row : evaluationFile) {
                writer.write(row);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
