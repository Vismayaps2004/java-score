package com.tw.step.scores;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {
    static void main() {
        Path scoresInputFile = Paths.get("resources/scores-file1.txt");
        try {
            Scanner scanner = new Scanner(scoresInputFile);
            Integer score;
            while(scanner.hasNext()) {
                System.out.println(scanner.nextInt());
//                score += scanner.nextInt();
            }
            System.out.println(scanner);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
