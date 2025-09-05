package io.github.pseudokit.datasorter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.github.pseudokit.datasorter.stats.FullStaticsticsCollector;
import io.github.pseudokit.datasorter.stats.ShortStaticsticsCollector;
import io.github.pseudokit.datasorter.stats.StatisticsCollector;
import io.github.pseudokit.datasorter.utils.AppConstants;

public class DataSorter {

    private List<String> inputFiles;
    private String outputPrefix;
    private String outputPath;
    
    private List<Long> integers = new ArrayList<>();
    private List<Float> floats = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    private boolean appendMode;
    private StatisticsCollector statisticsCollector;

    public DataSorter(List<String> inputFiles, String outputPrefix, boolean appendMode, String outputPath, boolean isFullStatisticMode) {
        this.inputFiles = inputFiles;
        this.outputPrefix = outputPrefix;
        this.appendMode = appendMode;
        this.outputPath = outputPath;
        if (isFullStatisticMode) {
            statisticsCollector = new FullStaticsticsCollector();
        } else {
            statisticsCollector = new ShortStaticsticsCollector();
        }
    }
    public DataSorter(List<String> inputFiles, String outputPrefix, boolean appendMode, boolean isFullStatisticMode) {
        this.inputFiles = inputFiles;
        this.outputPrefix = outputPrefix;
        this.appendMode = appendMode;
        this.outputPath = "";
        if (isFullStatisticMode) {
            statisticsCollector = new FullStaticsticsCollector();
        } else {
            statisticsCollector = new ShortStaticsticsCollector();
        }
    }

    public void run() {
        String outputDirectory = (this.outputPath != "") ? this.outputPath : System.getProperty("user.dir");
        
        for (String filePath : inputFiles) {
            processFile(filePath);
        }
        writeOutputFiles(outputPrefix, outputDirectory, this.appendMode);
        statisticsCollector.print();
    }

    private void processFile(String filePath) {
        File inputFile = new File(filePath);

        if (!inputFile.exists()) {
            System.err.println("Eror: file " + filePath + " is not found in current directory");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            String type = "";
            while ((line = reader.readLine()) != null) {
                type = recognizeType(line);
                switch (type) {
                    case (AppConstants.INTEGER_TYPE):
                        Long currentInteger = Long.parseLong(line);
                        integers.add(currentInteger);
                        statisticsCollector.addInteger(currentInteger);                
                        break;
                    case (AppConstants.FLOAT_TYPE):
                        Float currentFloat= Float.parseFloat(line);
                        floats.add(currentFloat);
                        statisticsCollector.addFloat(currentFloat);
                        break;
                    case (AppConstants.STRING_TYPE):
                        strings.add(line);
                        statisticsCollector.addString(line);
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * @param str входная строка.
     * @return тип данных в строке "Integer", "Float", "String"
     */
   public static String recognizeType(String str) {
        if (str == null || str.trim().isEmpty()) {
            return AppConstants.STRING_TYPE;
        }
        
        str = str.trim();

        if (str.matches("^-?\\d+$")) {
            return AppConstants.INTEGER_TYPE;
        }

        if (str.matches("^-?\\d*\\.\\d+$")) {
            return AppConstants.FLOAT_TYPE;
        }

        return AppConstants.STRING_TYPE;
    }

    /**
     * Создает выходные файлы с заданным префиксом и записывает в них отсортированные данные.
     * @param outputPrefix префикс для имен выходных файлов
     * @param outputPath   путь к директории, где будут сохранены файлы
     */
    public void writeOutputFiles(String outputPrefix, String outputPath, boolean appendMode) {
        Path outputDirectory = Paths.get(outputPath);
        try {
            if (!Files.exists(outputDirectory)) {
                Files.createDirectories(outputDirectory);
            }
        } catch (IOException e) {
            System.err.println("Error of creating directory" + outputPath + ": " + e.getMessage());
            return; 
        }

        String intFilePath = getPath(outputPath, outputPrefix, AppConstants.INTEGER_FILE_SUFFIX);
        DataSorter.writeListToFile(intFilePath, integers, appendMode);

        String floatFilePath = getPath(outputPath, outputPrefix, AppConstants.FLOAT_FILE_SUFFIX);
        DataSorter.writeListToFile(floatFilePath, floats, appendMode);
        
        String stringFilePath = getPath(outputPath, outputPrefix, AppConstants.STRING_FILE_SUFFIX);
        DataSorter.writeListToFile(stringFilePath, strings, appendMode);
    }
    
    public static <T> void writeListToFile(String filePath, List<T> data, boolean appendMode) {
        if (data == null || data.isEmpty()) {
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, appendMode))) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error write to file " + filePath + ": " + e.getMessage());
        }
    }

    private String getPath(String path, String prefix, String suffix) {
        return path + File.separator + prefix + suffix;
    }
}
