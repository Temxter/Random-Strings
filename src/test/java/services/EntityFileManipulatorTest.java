package services;

import model.Entity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class EntityFileManipulatorTest {

    private EntityFileManipulator fileManipulator;
    private int linesAmount = 10;
    private String filename = "test";

    @Before
    public void init() throws FileNotFoundException {
        EntityFilesService.generateFiles(1, linesAmount, filename);
        fileManipulator = new EntityFileManipulator(filename + "_0.txt");
    }

    @Test
    public void iteratorTest() throws FileNotFoundException {
        for (int i = 0; i < linesAmount; i++) {
            if (fileManipulator.hasNext()) {
                Entity entity = fileManipulator.next();
                Assert.assertNotNull("Next Entity is null!", entity);
                System.out.println(entity);
            }
        }
    }

    @Test
    public void countRecordsTest() {
        Assert.assertEquals("Lines are incorrectly counted",
                new Long(linesAmount), fileManipulator.countRecords());
    }

    @After
    public void destroy() {
        new File(filename + "_0.txt").delete();
    }
}
