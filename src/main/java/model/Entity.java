package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

@javax.persistence.Entity
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date recordDate;
    private String englishText;
    private String russianText;
    private Integer recordInteger;
    private Double recordDouble;
    @Transient
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");


    public Entity() {
    }

    public Entity(Date recordDate, String englishText, String russianText,
                  Integer recordInteger, Double recordDouble) {
        this.recordDate = recordDate;
        this.englishText = englishText;
        this.russianText = russianText;
        this.recordInteger = recordInteger;
        this.recordDouble = recordDouble;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getEnglishText() {
        return englishText;
    }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }

    public String getRussianText() {
        return russianText;
    }

    public void setRussianText(String russianText) {
        this.russianText = russianText;
    }

    public Integer getRecordInteger() {
        return recordInteger;
    }

    public void setRecordInteger(Integer recordInteger) {
        this.recordInteger = recordInteger;
    }

    public Double getRecordDouble() {
        return recordDouble;
    }

    public void setRecordDouble(Double recordDouble) {
        this.recordDouble = recordDouble;
    }

    @Override
    public String toString() {
        return String.format("%s||%s||%s||%8d||%.8f",
                dateFormat.format(recordDate),
                englishText,
                russianText,
                recordInteger,
                recordDouble);
    }
}
