package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {

    String title;
    String description;
    Boolean isActive;
    LocalDate creationDate;
    LocalDate startDate;
    LocalDate endDate;
    transient DateTimeFormatter formatter;

    public Task(String title) {
        this.title = title;
        isActive = true;
        this.formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        creationDate = LocalDate.now();
        System.out.println("Задача создана");

    }

    public void checkTask() {

        System.out.println("Заголовок: " + title);
        System.out.println("Описание: " +description);
        System.out.println("Дата создания: " + creationDate);
        System.out.println("Дата начала :" + startDate);
        System.out.println("Дата конца: " + endDate);
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setStartDate (String date) {
        startDate = LocalDate.parse(date, formatter);
    }
    public void setEndDate (String date) {
        endDate = LocalDate.parse(date, formatter);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTitle() {return title;}

    public String getStringEndDate() {
        String date = endDate.format(formatter);
        return date; }

    public String getStringStartDate() {
        String date = startDate.format(formatter);
        return date; }
}
