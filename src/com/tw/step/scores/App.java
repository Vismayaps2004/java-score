package com.tw.step.scores;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

import static java.nio.file.StandardWatchEventKinds.*;

public class App {
    static void main() {
        Path scoresInputFile = Paths.get("resources/scores-file1.txt");
        try {
            int score = 0;
            Scanner scanner = new Scanner(scoresInputFile);
            while (scanner.hasNext()) {
                score += scanner.nextInt();
            }
            writeScores(score);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("resources");
            path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            WatchKey key;
            for (; ; ) {
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    int score = 0;
                    Scanner scanner = new Scanner(scoresInputFile);
                    while (scanner.hasNext()) {
                        score += scanner.nextInt();
                    }
//                    System.out.println(score);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeScores(int score) {
        try (FileWriter writer = new FileWriter("resources/scores.txt")) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
