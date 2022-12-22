package hr.fer.oprpp1.hw04.db.demo;

import hr.fer.oprpp1.hw04.db.QueryFilter;
import hr.fer.oprpp1.hw04.db.QueryParser;
import hr.fer.oprpp1.hw04.db.StudentDatabase;
import hr.fer.oprpp1.hw04.db.StudentRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo {

    private static final String PATH_OF_DATABASE = "F:\\Fakultet\\5_semestar\\hw04-0036536032\\database.txt";

    public static void main(String[] args) {


        List<String> lines = null;
        try {
            lines = Files.readAllLines(Path.of(PATH_OF_DATABASE));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        List<StudentRecord> records = new ArrayList<>();

        for(String line : lines) {
            String[] elems = line.split("\t");
            records.add(new StudentRecord(elems[0], elems[1], elems[2], elems[3]));
        }

        StudentDatabase db = new StudentDatabase(records);

        try {

            InputStreamReader in= new InputStreamReader(System.in);

            BufferedReader input = new BufferedReader(in);

            String query;

            while (!(query = input.readLine()).equals("exit")) {
                QueryParser parser;
                try{
                    parser = new QueryParser(query);


                    if(parser.isDirectQuery()) {
                        StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());

                        System.out.println("Using index for record retrieval");

                        if(r != null) {
                            System.out.print(getFormattedRecords(r));
                            System.out.println("Records selected: 1");
                        } else
                            System.out.println("Records selected: 0");
                    } else {
                        List<StudentRecord> r = db.filter(new QueryFilter(parser.getQuery()));

                        if(r.size() != 0)
                            System.out.print(getFormattedRecords(r.toArray(new StudentRecord[]{})));
                        System.out.println("Records selected: " + r.size());
                    }
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

            System.out.println("Goodbye!");

        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    private static String getFormattedRecords(StudentRecord... records) {
        StringBuilder sb = new StringBuilder();

        int longestJMBAG = 0;
        int longestLastName = 0;
        int longestFirstName = 0;

        for(StudentRecord record : records) {
            if(record.getFirstName().length() > longestFirstName) longestFirstName = record.getFirstName().length();
            if(record.getLastName().length() > longestLastName) longestLastName = record.getLastName().length();
            if(record.getJmbag().length() > longestJMBAG) longestJMBAG = record.getJmbag().length();
        }

        sb.append("+").append("=".repeat(longestJMBAG + 2));
        sb.append("+").append("=".repeat(longestLastName + 2));
        sb.append("+").append("=".repeat(longestFirstName + 2));
        sb.append("+").append("=".repeat(3)).append("+");
        sb.append("\n");

        for(StudentRecord record : records) {
            sb.append("| ");
            sb.append(String.format("%-" + (longestJMBAG + 1) + "s", record.getJmbag()));
            sb.append("| ");
            sb.append(String.format("%-" + (longestLastName + 1) +"s", record.getLastName()));
            sb.append("| ");
            sb.append(String.format("%-" + (longestFirstName + 1) +"s", record.getFirstName()));
            sb.append("| ");
            sb.append(String.format("%-2s", record.getFinalGrade()));
            sb.append("| ");
            sb.append("\n");
        }

        sb.append("+").append("=".repeat(longestJMBAG + 2));
        sb.append("+").append("=".repeat(longestLastName + 2));
        sb.append("+").append("=".repeat(longestFirstName + 2));
        sb.append("+").append("=".repeat(3)).append("+");
        sb.append("\n");

        return sb.toString();
    }
}
