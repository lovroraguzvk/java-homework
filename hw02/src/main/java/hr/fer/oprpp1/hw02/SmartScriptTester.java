package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class SmartScriptTester {

    static String filepath = "F:\\Fakultet\\5_semestar\\hw02-0036536032\\src\\main\\java\\hr\\fer\\oprpp1\\hw02\\examples\\doc%d.txt";
    static int NUM_OF_FILES = 2;
    private static final SmartScriptTester tester = new SmartScriptTester();


    public static void main(String[] args) throws IOException {
        for(int i = 1; i <= NUM_OF_FILES; i++) {
            String docBody = Files.readString(Paths.get(String.format(filepath, i)));
            execute(docBody);
        }
    }

    private static void execute(String docBody) {
        SmartScriptParser parser = null;
        try {
            parser = new SmartScriptParser(docBody);
        } catch(
                SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            e.printStackTrace();
            System.exit(-1);
        } catch(Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        System.out.println(originalDocumentBody); // should write something like original
        System.out.println();
        // content of docBody
    }

    private String loader(String filename) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try(InputStream is =
                    this.getClass().getClassLoader().getResourceAsStream(filename)) {
            byte[] buffer = new byte[1024];
            while(true) {
                int read = is.read(buffer);
                if(read<1) break;
                bos.write(buffer, 0, read);
            }
            return bos.toString(StandardCharsets.UTF_8);
        } catch(IOException ex) {
            return null;
        }
    }


}
