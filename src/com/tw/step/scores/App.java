package com.tw.step.scores;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

import static java.nio.file.StandardWatchEventKinds.*;

public class App {
    static void main() {
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
                System.out.println(score);
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
