package services;

import model.Entity;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

public class EntityFileManipulator implements Iterator<Entity> {

    private final String fileName;
    private BufferedReader bufferedReader;
    private boolean hasNextEntity = true;
    static final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");


    public EntityFileManipulator(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        bufferedReader = new BufferedReader(new FileReader(fileName));
    }

    @Override
    public boolean hasNext() {
        return hasNextEntity;
    }

    @Override
    public Entity next() {
        try {
            return parseNextEntity();
        } catch (IOException e) {
            System.err.println("Read of new line of file throw exception!");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Format of string not correct!");
            e.printStackTrace();
        }
        return null;
    }

    private Entity parseNextEntity() throws IOException, ParseException {
        String line = bufferedReader.readLine();
        if (line == null) {
            hasNextEntity = false;
            bufferedReader = null;
            return null;
        }
        String [] strings = line.split("\\|\\|");

        Date recordDate = dateFormat.parse(strings[0]);
        String englishText = strings[1];
        String russianText = strings[2];
        Integer recordInteger = Integer.parseInt(strings[3].replaceAll(" ", ""));
        Double recordDouble = Double.parseDouble(strings[4].replaceAll(",", "."));
        Entity entity = new Entity(recordDate, englishText, russianText, recordInteger, recordDouble);
        return entity;
    }

    public Long countRecords() {
        long lines = 0;
        try {
            lines = new BufferedReader(new FileReader(fileName)).lines().count();
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s cannot be opened!", fileName);
        }
        return lines;
    }

    public void add(Entity entity) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        bufferedWriter.append(entity.toString());
        bufferedWriter.close();
    }
}
