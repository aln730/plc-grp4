package testing;

import provided.JotlinToken;
import provided.JotlinTokenizer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JotlinTokenizerTester {

    public static void main(String[] args) throws IOException {
        String inputfile = args[0];
        String outputfile = args[1];
        String compareFile = args[2];

        ArrayList<JotlinToken> tokens = JotlinTokenizer.tokenize(inputfile);
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(outputfile));
        for (JotlinToken token : tokens) {
            writer.write(token.toString());
            writer.newLine();
        }
        writer.close();

        FileDiff.compareFiles(compareFile, outputfile);
    }
}
