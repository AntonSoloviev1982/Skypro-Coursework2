package service;

import exception.IncorrectArgumentException;
import exception.TaskNotFoundException;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;


public class TaskService {

    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final List<Task> removedTasks = new ArrayList<>();



    public void add(Scanner scanner) {
        System.out.println("Введите заголовок:");
        String title = scanner.next();
        System.out.println("Введите повторяемость:");
        RepeatabilityEnum repeatability;
        try {
            repeatability = RepeatabilityEnum.valueOf(scanner.next());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Нужно указать один из вариантов повторяемости : " +
                    "ONETIME, DAILY, WEEKLY, MONTHLY, YEARLY");
        }
        System.out.println("Введите описание:");
        String description = scanner.next();
        System.out.println("Введите тип:");
        TypeEnum type;
        try {
            type = TypeEnum.valueOf(scanner.next());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Нужно указать один из двух типов задач: PERSONAL, WORK");
        }
        Task newTask = new Task(
                title,
                repeatability,
                description,
                type);
        taskMap.put(newTask.getId(), newTask);
        System.out.println(newTask);
    }


    public void remove(Scanner scanner) {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        if (taskMap.containsKey(id)) {
            removedTasks.add(taskMap.get(id));
            taskMap.remove(id);
        } else {
            throw new TaskNotFoundException("Задача с id " + id + " не существует");
        }
        System.out.println("Была удалена задача c id: " + id);
    }

    public Set<Task> checkAllTasksByDay(LocalDate localDate) {
        Set<Task> tasks = new HashSet<>();
        for (Task task : taskMap.values()) {
            switch (task.getRepeatability()) {
                case ONETIME:
                    Taskable oneTimeTask = new OneTimeTask();
                    if (oneTimeTask.appearsIn(localDate, task)) {
                        tasks.add(task);
                    }
                    break;
                case DAILY:
                    Taskable dailyTask = new DailyTask();
                    if (dailyTask.appearsIn(localDate, task)) {
                        tasks.add(task);
                    }
                    break;
                case WEEKLY:
                    Taskable weeklyTask = new WeeklyTask();
                    if (weeklyTask.appearsIn(localDate, task)) {
                        tasks.add(task);
                    }
                    break;
                case MONTHLY:
                    Taskable monthlyTask = new MonthlyTask();
                    if (monthlyTask.appearsIn(localDate, task)) {
                        tasks.add(task);
                    }
                    break;
                case YEARLY:
                    Taskable yearlyTask = new YearlyTask();
                    if (yearlyTask.appearsIn(localDate, task)) {
                        tasks.add(task);
                    }
                    break;
            }
        }
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("На " + localDate + " задач не запланировано!");
        }
        return tasks;
    }

    public void getAllByDay(Scanner scanner) {
        Pattern DATE_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.println("Введите дату в формате dd.MM.yyyy");
        if (scanner.hasNext(DATE_PATTERN)) {
            String date = scanner.next();
            LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER);
            Set<Task> tasks = checkAllTasksByDay(localDate);
            System.out.println("На " + localDate + " запланированы задачи: \n" + tasks);
        } else {
            throw new IncorrectArgumentException("Не правильный формат даты");
        }
    }


    public Collection<Task> getRemovedTasks() {
        if (removedTasks.isEmpty()) {
            throw new TaskNotFoundException("Удаленные задачи отсутствуют.");
        }
        System.out.println("Список удаленных задач: \n" + removedTasks);
        return removedTasks;
    }


    public void updateDescription(Scanner scanner) {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        System.out.println("Введите новое описание для задачи id: " + id);
        String newDescription = scanner.next();
        if (taskMap.containsKey(id)) {
            taskMap.get(id).setDescription(newDescription);
        }
    }

    public void updateTitle(Scanner scanner) {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        System.out.println("Введите новый заголовок для задачи id: " + id);
        String newTitle = scanner.next();
        if (taskMap.containsKey(id)) {
            taskMap.get(id).setTitle(newTitle);
        }
    }

    public Map<LocalDate, Collection<Task>> getAllGroupByData() {
        Map<LocalDate, Collection<Task>> allGroupByData = new LinkedHashMap<>();
        Collection<Task> allTasksByData = new HashSet<>();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2023, 12, 31);
            for (int i = 0; i < endDate.getDayOfYear() - startDate.getDayOfYear(); i++) {
                try {
                    allTasksByData = checkAllTasksByDay(startDate.plusDays(i));
                } catch (TaskNotFoundException e) {
                    continue;
                }
                if (!allTasksByData.isEmpty()) {
                    allGroupByData.put(startDate.plusDays(i), allTasksByData);
                }
            }
            for (Map.Entry<LocalDate, Collection<Task>> entry : allGroupByData.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        return allGroupByData;
    }

    public static void printMenu() {
        System.out.println("1. Добавить задачу\n2. Удалить задачу\n3. Получить задачу на указанный день\n" +
                "4. Получить удаленные задачи\n5. Изменить описание задачи\n6. Изменить заголовок задачи\n" +
                "7. Получить все задачи, сгруппированные по датам\n0. Выход");
    }

}
