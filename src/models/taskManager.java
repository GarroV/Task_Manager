package models;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class taskManager implements Serializable {

    private File taskFile;
    private Scanner scanner;
    private ArrayList<Task> tasks;
    private ArrayList<Task> missedTasks;
    private ArrayList<Task> finishedTasks;
    private DateTimeFormatter formatter;
    private LocalDate currentDate;
    private Task nearestTask;

    public taskManager() {

        taskFile = new File("C:\\GitRepository\\Task_Manager\\src\\models\\tasks.txt");
        tasks = new ArrayList<>(10);
        missedTasks = new ArrayList<>();
        finishedTasks = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        currentDate = LocalDate.now();

    }

    public void run() {

        loadTasks();
        checkStatus();

        System.out.println("Молви Друг и войди!");
        System.out.println("Что нужно сделать?");

        int i = 1;
        while (i != 0) {

            //Главное меню
            System.out.println("1.Добавить задачу");
            System.out.println("2.Отобразить задачу");
            System.out.println("3.Сохранить задачи");
            System.out.println("4.Завершить");
            scanner = new Scanner(System.in);
            int mainMenu = scanner.nextInt();

            if (mainMenu > 0 && mainMenu < 5) {
                switch (mainMenu) {
                    case 1:
                        //добавить задачу
                        createTask();
                        break;
                    case 2:
                        //отобразить задачи
                        showRoom();
                        break;
                    case 3:
                        //сохранить задачи, записать в файл
                        saveTasks();
                        break;
                    default:
                        i = 0;
                }
            } else System.out.println("Такого варианта нет");
        }
    }

    //меню задач
    public void showRoom () {

        int ii = 1;
        while (ii != 0) {
            System.out.println();
            System.out.println("1.Все задачи");
            System.out.println("2.По дате");
            System.out.println("3.По названию");
            System.out.println("4.Ближайшая");
            System.out.println("5.Просроченые задачи");
            System.out.println("6.Завершенные задачи");
            System.out.println("7.Вернуться в главное меню");

            Scanner scanner2 = new Scanner(System.in);
            int showTask = scanner.nextInt();

            switch (showTask) {
                case 1:
                    showAllTasks();
                    ii = 0;
                    break;
                case 2:
                    System.out.println("Введите дату в формате 11.11.1111");
                    String date = scanner2.nextLine();
                    showTasksByDate(date);
                    break;
                case 3:
                    System.out.println("Введите название");
                    String title = scanner2.nextLine();
                    showTasksByTitle(title);
                    break;
                case 4:
                    showNearestTask();
                    break;
                case 5:
                    showMissedTasks();
                    break;
                case 6:
                    showFinishedTasks();
                    break;
                default:
                    ii = 0;
            }
        }
    }

    //вывести все задачи
    public void showAllTasks () {

        for (Task task: tasks) {

            if (task.getStatus()) {

                System.out.println((tasks.indexOf(task)+1) + ": "
                    + task.getTitle()
                    + " c "
                    + task.getStartDate()
                    + " по "
                    + task.getEndDate()
                );
            }
        }

        workWithTask();
    }

    //поиск по конечной дате
    public void showTasksByDate (String date) {

        boolean ok = false;

        LocalDate searchDate = LocalDate.parse(date, formatter);

        for (Task task: tasks) {

            if (searchDate.equals(task.getEndDate())) {

                System.out.print((tasks.indexOf(task) + 1) + " ");
                System.out.println(task.getTitle());
                ok = true;
            }
        }

        if(ok) {
            workWithTask();
        } else System.out.println("Подходящих задач нет");
    }

    //поиск по названию
    public void showTasksByTitle (String title) {

        boolean ok = false;

        for (Task task: tasks) {

            if (title.equals(task.getTitle())) {

                System.out.print((tasks.indexOf(task) + 1) + " ");
                System.out.println(task.getTitle());
                ok = true;
            }
        }

        if(ok) {
            workWithTask();
        } else System.out.println("Подходящих задач нет");
    }

    //ближайшая задача
    public void showNearestTask () {

        nearestTask = tasks.get(0);

        for (Task task: tasks) {

            if (task.getStatus()) {
                if (nearestTask.getEndDate().isAfter(task.getEndDate())) {
                    nearestTask = task;
                }
            }
        }

        System.out.println((tasks.indexOf(nearestTask)+1) + ": "
                + nearestTask.getTitle()
                + " c "
                + nearestTask.getStartDate()
                + " по "
                + nearestTask.getEndDate()
        );

        workWithTask();
    }

    public void showMissedTasks () {

        for (Task task: missedTasks) {

            System.out.println((tasks.indexOf(task)+1) + ": "
                    + task.getTitle()
                    + " c "
                    + task.getStartDate()
                    + " по "
                    + task.getEndDate()
            );
        }

        workWithTask();
    }

    public void showFinishedTasks () {

        for (Task task: finishedTasks) {

                System.out.println((finishedTasks.indexOf(task)+1) + ": "
                        + task.getTitle()
                        + " c "
                        + task.getStartDate()
                        + " по "
                        + task.getEndDate()
                );
        }
    }

    //работа с выбранной  задачей
    public void workWithTask () {

        System.out.println("Введите номер задачи");
        System.out.println("Или 0 для выхода");
        int taskNumber = (scanner.nextInt() -1 );

        if (taskNumber < tasks.size() && taskNumber > -1) {
            tasks.get(taskNumber).checkTask();

            Scanner scanner2 = new Scanner(System.in);
            int i = 1;
                while (i != 0) {

                System.out.println("1.Изменить название");
                System.out.println("2.Изменить описание");
                System.out.println("3.Изменить дату начала");
                System.out.println("4.Изменить дату завершения");
                System.out.println("5.Завершить задачу");
                System.out.println("0.Выйти");

                int taskMenu = scanner.nextInt();

                switch (taskMenu) {
                    case 1:
                        System.out.println("Введите новое название");
                        tasks.get(taskNumber).setTitle(scanner2.nextLine());
                        i = 0;
                        break;
                    case 2:
                        System.out.println("Введите новое описание");
                        tasks.get(taskNumber).setDescription(scanner2.nextLine());
                        i = 0;
                        break;
                    case 3:
                        System.out.println("Введите новую дату начала");

                        LocalDate date = LocalDate.parse(scanner2.nextLine(), formatter);
                        tasks.get(taskNumber).setStartDate(date);
                        i = 0;
                        break;
                    case 4:
                        System.out.println("Введите новую дату завершения");

                        LocalDate date2 = LocalDate.parse(scanner2.nextLine(), formatter);
                        tasks.get(taskNumber).setEndDate(date2);
                        i = 0;
                        break;
                    case 5:
                        System.out.println(" Вы уверены? y/n");
                        if (scanner2.nextLine() == "y") {
                            tasks.get(taskNumber).finishTask(true);
                        }
                    default:
                        i = 0;
                }
            }
        }
    }

    //загрузить задачи из файла
    //переместить завершенные задачи в отдельный массив
    public void loadTasks () {

        try {
            FileInputStream fis = new FileInputStream(taskFile);
            ObjectInputStream ois = new ObjectInputStream(fis);

           this.tasks = (ArrayList) ois.readObject();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Task task: tasks) {

            if (task.isTaskFinished()) {
                finishedTasks.add(task);

            }
        }

        for (Task task: finishedTasks) {

            tasks.remove(task);
        }

    }

    public void saveTasks () {

            tasks.addAll(finishedTasks);

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

        LocalDate date = LocalDate.parse(scanner.nextLine(), formatter);
        task.setStartDate(date);

        System.out.println("Когда закончить? в формате 11.11.1111");
        LocalDate date2 = LocalDate.parse(scanner.nextLine(), formatter);
        task.setEndDate(date2);

        System.out.println("Задача создана");
        task.checkTask();

        tasks.add(task);

    }

    //проверить дату, если задача пропущена -  поменять статус
    //задачу добавить в список пропущенных
    public void checkStatus () {

        for (Task task: tasks) {

            if (currentDate.isAfter(task.getEndDate())) {

                task.setStatus(false);
                missedTasks.add(task);
            }
        }
    }
}
