package console;

import dao.EntityDao;
import services.EntityFilesService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        Arrays.stream(args).forEach(System.out::println);
        if (args.length == 0) {
            System.out.println("Too few arguments!");
            System.out.println(infoProgramMessage);
            System.exit(0);
        }
        switch (args[0]) {
            case "--generate":
            case "-g":
                generate(args);
                break;
            case "--concat":
            case "-c":
                concat(args);
                break;
            case "--import":
            case "-i":
                importToDatabase(args);
                break;
            case "--sumIntegers":
                Long sumIntegers = new EntityDao().getSumIntegers();
                System.out.println("Sum of integers: " + sumIntegers);
                break;
            case "--medianDoubles":
                Double medianDoubles = new EntityDao().getMedianDoubles();
                System.out.println("Median of doubles: " + medianDoubles);
                break;
            default:
                System.out.println("Please, enter correct arguments.");
                System.out.println(infoProgramMessage);
        }
    }

    private static void generate(String[] args) {
        String correctMessage = "Correct: --generate NumberOfFiles NumberOfStrings";
        if (args.length <= 2) {
            System.err.println("Error parse params. " + correctMessage);
        } else {
            try {
                int numberOfFiles = Integer.parseInt(args[1]);
                int numberOfStrings = Integer.parseInt(args[2]);
                EntityFilesService.generateFiles(numberOfFiles, numberOfStrings, "file");
            } catch (NumberFormatException e) {
                System.err.println("Format of numbers not correct: " + e.getMessage() + correctMessage);
            }
        }
    }

    private static void concat(String[] args) {
        String correctMessage = "Correct: --concat inputFile1 inputFile2 outputFile [skipSubstring]";
        if (args.length <= 2) {
            System.err.println("Error parse params. " + correctMessage);
        } else {
            String inputFile1 = args[1];
            String inputFile2 = args[2];
            String outputFile = args[3];
            try {
                if (args.length == 5) {
                    String skipSubstring = args[4];
                    EntityFilesService.uniteFiledWithRemoveStrings(inputFile1, inputFile2, outputFile,
                            skipSubstring);
                }
                else {
                    EntityFilesService.uniteFiles(inputFile1, inputFile2, outputFile);
                }
                System.out.printf("The files %s and %s were successfully concatenated in %s.",
                        inputFile1, inputFile2, outputFile);
            } catch (IOException e) {
                System.err.printf("Fail to concatenate files: %s, %s. Exception: %s", inputFile1, inputFile2,
                        e.getMessage());
            }
        }
    }

    private static void importToDatabase(String[] args) {
        String correctMessage = "Correct: --import inputFile";
        if (args.length <= 1) {
            System.err.println("Error parse params. " + correctMessage);
        } else {
            String inputFile = args[1];
            try {
                EntityFilesService.importToDatabase(inputFile);
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + inputFile);
            }
        }
    }

    private static String infoProgramMessage = "Program of manipulations with Entity.\n" +
            "Commands:\n" +
            "'--generate n m' or '-g n m' - generate n files with m strings;\n" +
            "'--concat in1 in2 out [skip]' or '-c in1 in2 out [skip]' " +
            "- concatenate in1 and in2 files in out file and skips lines in in1 and in2 that contain skip substring" +
            " [skip - optional param];\n" +
            "'--import in1' or '-i in1' - import lines to database;\n" +
            "'--sumIntegers' - Calculate sum of integers in database;\n" +
            "'--medianDoubles' - Calculate median of doubles in database;\n";
}
