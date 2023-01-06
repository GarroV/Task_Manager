package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {

    private String title;
    private String description;
    private boolean isActive;
    private boolean isFinished;
    private LocalDate creationDate;
    private LocalDate startDate;
    private LocalDate endDate;

    public Task(String title) {
        this.title = title;
        isActive = true;
        isFinished = false;

        creationDate = LocalDate.now();
        System.out.println("Задача создана");

    }

    public void checkTask() {

        System.out.println("===============");
        System.out.println("Заголовок: " + title);
        System.out.println("Описание: " +description);
        System.out.println("Дата создания: " + creationDate);
        System.out.println("Дата начала :" + startDate);
        System.out.println("Дата конца: " + endDate);
        System.out.println("===============");
    }

    public void setTitle (String title) {this.title = title;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate (LocalDate date) {startDate = date;}

    public void setEndDate (LocalDate date) {
        endDate = date;
    }

    public void setStatus (boolean isActive) { this.isActive = isActive;}

    public void finishTask (boolean isFinished ) { this.isFinished = isFinished;}

    public boolean isTaskFinished () {return isFinished;}

    public boolean getStatus() {return isActive;}

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTitle() {return title;}

}
