package com.tw.step.scores;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {
    static void main() {
        int score = 0;
        Path scoresInputFile = Paths.get("resources/scores-file1.txt");
        try {
            Scanner scanner = new Scanner(scoresInputFile);
            while (scanner.hasNext()) {
                score += scanner.nextInt();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
