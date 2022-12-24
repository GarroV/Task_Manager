package models;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class taskManager implements Serializable {

    File taskFile;
    Scanner scanner;
    ArrayList<Task> tasks;
    ArrayList <Task> tempTasks;
    DateTimeFormatter formatter;

    public taskManager() {

        taskFile = new File("C:\\GitRepository\\Task_Manager\\src\\models\\tasks.txt");
        tasks = new ArrayList(10);
        tempTasks = new ArrayList();
        this.formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    }

    public void run() {

        System.out.println("Молви Друг и войди!");
        System.out.println("Что нужно сделать?");

        int i = 1;
        while (i != 0) {

            //Главное меню
            System.out.println("1. Добавить задачу");
            System.out.println("2. Отобразить задачу");
            System.out.println("3. Завершить");
            scanner = new Scanner(System.in);
            int mainTask = scanner.nextInt();

            if (mainTask > 0 && mainTask < 4) {
                switch (mainTask) {
                    case 1:
                        //добавить задачу
                        createTask();
                    case 2:
                        //отобразить задачи
                        showRoom();
                    default:
                        saveTasks();
                        i = 0;
                }
            } else System.out.println("Такого варианта нет");
        }
    }

    public void showRoom () {

        int ii = 1;
        while (ii != 0) {
            System.out.println("1.Все задачи");
            System.out.println("2.По дате");
            System.out.println("3.По названию");
            System.out.println("4.Завершить");

            int showTask = scanner.nextInt();

            switch (showTask) {
                case 1: showAllTasks();
                case 2:
                    System.out.println("Введите дату в формате 11.11.1111");
                    String date = scanner.nextLine();
                    showTasksByDate(date);
                case 3:
                    System.out.println("Введите название");
                    String title = scanner.nextLine();
                    showTasksByTitle(title);
                default:
                    ii = 0;
            }
        }
    }

    //поиск по названию
    public void showTasksByTitle (String title) {

        for (Task task: tasks) {

            if (title == task.getTitle()) {

                this.tempTasks.add(task);
            }
        }

        for (int i = 0; i < tempTasks.size(); i++ ) {

            System.out.println("Задача №" + i);
            System.out.println(tempTasks.get(i).getTitle());
            System.out.println();

        }

    }

    //поиск по конечной дате
    public void showTasksByDate (String date) {

        LocalDate searchDate = LocalDate.parse(date, formatter);

        for (Task task: tasks) {

            if (searchDate.equals(task.getEndDate())) {

                this.tempTasks.add(task);
            }
        }

        for (int i = 0; i < tempTasks.size(); i++ ) {

            System.out.println("Задача №" + i);
            System.out.println(tempTasks.get(i).getTitle());
            System.out.println();

        }

    }

    //вывести все задачи
    public void showAllTasks () {

        for (Task task: tasks) {

            int count = 1;
            System.out.println(count + ": "
                    + task.getTitle()
                    + " c "
                    + task.getStringStartDate()
                    + " по "
                    + task.getStringEndDate()
            );
            count++;
        }
    }

    public void saveTasks () {

        //for (Task task: tasks) {

            try {
                FileOutputStream fos = new FileOutputStream(taskFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(tasks);
                oos.close();


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        //}

    }

    public void createTask() {

        System.out.println("Название?");
        scanner = new Scanner(System.in);
        String string = scanner.nextLine();

        Task task = new Task(string);

        System.out.println("Что нужно сделать?");
        string = scanner.nextLine();
        task.setDescription(string);

        System.out.println("Когда начать? в формате 11.11.1111");
        string = scanner.nextLine();
        task.setStartDate(string);

        System.out.println("Когда закончить? в формате 11.11.1111");
        string = scanner.nextLine();
        task.setEndDate(string);

        System.out.println("Задача создана");
        task.checkTask();

        tasks.add(task);

    }
}
