package io.github.pseudokit.datasorter;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
            boolean isFullStatistic = false;
            boolean isAppendMode = false;
            String prefix = "";
            String outputDir = "";
            List<String> inputFiles = new ArrayList<>();
            
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-s":
                        isFullStatistic = false;
                        break;
                    case "-f":
                        isFullStatistic = true;
                        break;
                    case "-a":
                        isAppendMode = true;
                        break;
                    case "-o":
                        if (i + 1 < args.length) {
                            outputDir = args[i + 1];
                            i++;
                        } else {
                            System.err.println("Ошибка: для флага -o не указан директория выходных файлов");
                            return;
                        }
                        break;
                    case "-p":
                        if (i + 1 < args.length) {
                            prefix = args[i + 1];
                            i++;
                        } else {
                            System.err.println("Ошибка: для флага -p не указан префикс");
                            return;
                        }
                        break;
                    default:
                        inputFiles.add(args[i]);
                        break;
                }
            }
            
            DataSorter ds;
            if (outputDir != "") {
                ds = new DataSorter(inputFiles, prefix, isAppendMode, outputDir, isFullStatistic);
            } else {
                ds = new DataSorter(inputFiles, outputDir, isAppendMode, isFullStatistic);
            }
            ds.run();
    }
}
