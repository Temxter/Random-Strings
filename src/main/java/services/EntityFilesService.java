package services;

import creators.EntityRandomCreator;
import dao.EntityDao;
import me.tongfei.progressbar.ProgressBar;
import model.Entity;

import java.io.*;

public class EntityFilesService {

    private EntityFilesService() {}

    public static void generateFiles(int filesAmount, int stringsAmount, String filename) {
        filename = filename == null ? "file" : filename;
        try {
            for (int i = 0; i < filesAmount; i++) {
                EntityFilesService.generateFile(String.format("%s_%d.txt", filename, i), stringsAmount);

                if (i == filesAmount - 1) { // all files created
                    System.out.println("Files have been successfully generated.");
                }
            }
        } catch (IOException e) {
            System.err.printf("Sorry, file can't be created. Exception: %s",  e.getMessage());
            e.printStackTrace();
        }
    }

    private static void generateFile(String filename, int stringsAmount) throws IOException {
        FileWriter writer = new FileWriter(filename, false);
        try {
            for (int i = 0; i < stringsAmount; i++) {
                writer.write(EntityRandomCreator.CreateEntity().toString() + "\n");
            }
        } finally {
            writer.close();
        }
    }

    public static void uniteFiles(String nameFirstFile, String nameSecondFile, String nameOutputFile) throws IOException {
        uniteFiledWithRemoveStrings(nameFirstFile, nameSecondFile, nameOutputFile, null);
    }

    public static void uniteFiledWithRemoveStrings(String nameFirstFile, String nameSecondFile, String nameOutputFile,
                                            String skipSubstring) throws IOException {
        BufferedReader firstReader = new BufferedReader(new FileReader(nameFirstFile));
        BufferedReader secondReader = new BufferedReader(new FileReader(nameSecondFile));
        PrintWriter bufferedWriter = new PrintWriter(new FileWriter(nameOutputFile));

        copyWithRemove(firstReader, bufferedWriter, skipSubstring);
        copyWithRemove(secondReader, bufferedWriter, skipSubstring);

        firstReader.close();
        secondReader.close();
        bufferedWriter.close();
    }

    private static void copyWithRemove(BufferedReader reader, PrintWriter writer,
                               String substringMissing) throws IOException {
        String line = reader.readLine();
        while ( line != null) {
            if (substringMissing == null) {
                writer.println(line);
            } else if (!line.contains(substringMissing)) {
                writer.println(line);
            }
            line = reader.readLine();
        }
    }


    public static void importToDatabase(String filename) throws FileNotFoundException {
        EntityFileManipulator fileManipulator = new EntityFileManipulator(filename);
        EntityDao entityDao = new EntityDao();

        try(ProgressBar pb = new ProgressBar("Importing", fileManipulator.countRecords())) {
            while (fileManipulator.hasNext()) {
                Entity entity = fileManipulator.next();
                entityDao.create(entity);
                pb.step();
            }
        }
    }
}
