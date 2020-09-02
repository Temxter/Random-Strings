package services;

import dao.EntityDao;
import model.Entity;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

public class EntityFilesServiceTest {

    @Test
    public void createFileTest() {
        int linesAmount = 10;
        String filename = "test";
        EntityFilesService.generateFiles(1, linesAmount, filename);
        File files = new File("./");
        Optional<File> obj = Arrays.stream(files.listFiles())
                .filter(file -> file.getName().contains(filename + "_0.txt"))
                .findAny();

        Assert.assertTrue("File have not created!", obj.isPresent());

        obj.ifPresent(file -> {
            try {
                Long lines = new BufferedReader(new FileReader(file)).lines().count();
                Assert.assertEquals("File not contains " + linesAmount + " strings!",
                        new Long(linesAmount), lines);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        if (obj.isPresent()){
            obj.get().delete();
        }
    }

    @Test
    public void uniteFilesTest() throws IOException {
        int linesAmount = 10;
        int filesAmount = 2;
        String generateFilename = "in";
        String outFilename = "out.txt";
        EntityFilesService.generateFiles(2, linesAmount, generateFilename);
        EntityFilesService.uniteFiles(String.format("%s_%d.txt", generateFilename, 0),
                String.format("%s_%d.txt", generateFilename, 1),
                outFilename);

        File files = new File("./");
        Optional<File> obj = Arrays.stream(files.listFiles())
                .filter(file -> file.getName().contains(outFilename))
                .findAny();

        Assert.assertTrue("File have not created!", obj.isPresent());

        obj.ifPresent(file -> {
            try {
                Long totalExpectedLines = new Long(linesAmount*filesAmount);
                Long actualLines = new BufferedReader(new FileReader(file)).lines().count();
                Assert.assertEquals("File not contains " + totalExpectedLines + " strings!",
                        totalExpectedLines, actualLines);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        if (obj.isPresent()){
            obj.get().delete();
        }
    }

    @Test
    public void uniteFilesWithRemoveTest() throws IOException {
        int linesAmount = 10;
        int filesAmount = 2;
        String generateFilename = "in";
        String outFilename = "out.txt";

        File files = new File("./");

        EntityFilesService.generateFiles(2, linesAmount, generateFilename);

        String inFileFirstName = String.format("%s_%d.txt", generateFilename, 0);
        String inFileSecondName = String.format("%s_%d.txt", generateFilename, 1);

        EntityFileManipulator fileManipulator = new EntityFileManipulator(inFileFirstName);
        Entity firstEntityIn = fileManipulator.next();
        String substringMissing = firstEntityIn.getEnglishText();

        EntityFilesService.uniteFiledWithRemoveStrings(inFileFirstName,
                inFileSecondName,
                outFilename,
                substringMissing);

        Optional<File> obj = Arrays.stream(files.listFiles())
                .filter(file -> file.getName().contains(outFilename))
                .findAny();

        Assert.assertTrue("File have not created!", obj.isPresent());

        obj.ifPresent(file -> {
            try {
                Long totalExpectedLines = new Long(linesAmount*filesAmount) - 1; // -1 is missed string
                Long actualLines = new BufferedReader(new FileReader(file)).lines().count();
                Assert.assertEquals("File not contains " + totalExpectedLines + " strings!",
                        totalExpectedLines, actualLines);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        if (obj.isPresent()){
            obj.get().delete();
        }
    }

    @Test
    public void importToDatabaseTest() throws FileNotFoundException {
        String filename = "test";
        EntityFilesService.generateFiles(1, 10, filename);

        EntityFilesService.importToDatabase(filename + "_0.txt");

        EntityDao entityDao = new EntityDao();
        Entity entity = entityDao.read(1);

        Assert.assertNotNull(entity);
    }

}