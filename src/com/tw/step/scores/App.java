package com.tw.step.scores;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class App {
    static void main() {
        Path path = Paths.get("resources/incoming");
        Stream<Path> files;
        try {
            files = Files.list(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        calculateTotalScore(files);
//        try {
//            int score = 0;
//            Scanner scanner = new Scanner(scoresInputFile);
//            while (scanner.hasNext()) {
//                score += scanner.nextInt();
//            }
//            writeScores(score);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            WatchService watchService = FileSystems.getDefault().newWatchService();
//            Path path = Paths.get("resources");
//            path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
//
//            WatchKey key;
//            for (; ; ) {
//                try {
//                    key = watchService.take();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                for (WatchEvent<?> event: key.pollEvents()) {
//                    Path fileName = (Path) event.context();
//                try {
//                    int score = 0;
//                    Scanner scanner = new Scanner(fileName);
//                    while (scanner.hasNext()) {
//                        score += scanner.nextInt();
//                    }
//                    writeScores(score);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//                }
//                boolean valid = key.reset();
//                if (!valid) {
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private static void calculateTotalScore(Stream<Path> files) {
        AtomicInteger score = new AtomicInteger();
        files.forEach(file -> {
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    score.addAndGet(scanner.nextInt());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writeScores(score.get());
    }

    private static void writeScores(Integer scoreDetails) {
        try (FileWriter writer = new FileWriter("resources/scores.txt", true)) {

            LocalTime time = LocalTime.now();
            writer.write("updated time:" + time + "\n" + scoreDetails + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
