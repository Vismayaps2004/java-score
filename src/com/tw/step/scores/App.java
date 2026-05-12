package com.tw.step.scores;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.nio.file.StandardWatchEventKinds.*;

public class App {
    static void main() {
        Path path = Paths.get("resources/incoming");
        Stream<Path> files;
        int totalScore;

        try {
            files = Files.list(path);
            totalScore = files
                    .mapToInt(App::readFileScore)
                    .sum();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeScores(totalScore);
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            WatchKey key;
            for (; ; ) {
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    Thread.sleep(100);
                    try (Stream<Path> updatedFiles = Files.list(path)) {
                        totalScore = updatedFiles
                                .mapToInt(App::readFileScore)
                                .sum();
                        writeScores(totalScore);
                    }
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static int readFileScore(Path file) {
        int score = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextInt()) {
                score += scanner.nextInt();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return score;
    }

    private static void writeScores(int scoreDetails) {
        try (FileWriter writer = new FileWriter("resources/scores.txt", true)) {

            LocalTime time = LocalTime.now();
            writer.write("updated time:" + time + "\n" + scoreDetails + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
