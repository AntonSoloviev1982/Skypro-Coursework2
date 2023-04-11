package service;

import exception.TaskNotFoundException;
import model.*;

import java.time.LocalDate;
import java.util.*;


public class TaskService {

    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final List<Task> removedTasks = new ArrayList<>();


    public Map<Integer, Task> getTaskMap() {
        return taskMap;
    }



    public void add(RepeatabilityEnum repeatability, TypeEnum type) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите заголовок:");
        String title = scanner.nextLine();
        System.out.println("Введите описание:");
        String description = scanner.nextLine();
        Task newTask = new Task(
                title,
                repeatability,
                description,
                type);
        taskMap.put(newTask.getId(), newTask);
//        scanner.close();
    }


    public void remove(int id) {
        if (taskMap.containsKey(id)) {
            removedTasks.add(taskMap.get(id));
            taskMap.remove(id);
        } else {
            throw new TaskNotFoundException("Задача с id " + id + " не существует");
        }
        System.out.println("Была удалена задача c id: " + id);
    }

    public Set<Task> getAllByData(LocalDate localDate) {
        Set<Task> tasks = new HashSet<>();
        for (Task task : taskMap.values()) {
            switch (task.getRepeatability()) {
                case ОДНОКРАТНАЯ:
                    Taskable oneTimeTask = new OneTimeTask();
                    if (oneTimeTask.appearsIn(localDate, task)) {
                        tasks.add(task);
                    }
                    break;
                case ЕЖЕДНЕВНАЯ:
                    Taskable dailyTask = new DailyTask();
                    if (dailyTask.appearsIn(localDate, task)) {
                        tasks.add(task);
                    }
                    break;
                case ЕЖЕНЕДЕЛЬНАЯ:
                    Taskable weeklyTask = new WeeklyTask();
                    if (weeklyTask.appearsIn(localDate, task)) {
                        tasks.add(task);
                    }
                    break;
                case ЕЖЕМЕСЯЧНАЯ:
                    Taskable monthlyTask = new MonthlyTask();
                    if (monthlyTask.appearsIn(localDate, task)) {
                        tasks.add(task);
                    }
                    break;
                case ЕЖЕГОДНАЯ:
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


    public Collection<Task> getRemovedTasks() {
        return removedTasks;
    }


    public void updateDescription(int id) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите новое описание для задачи id: " + id);
        String newDescription = scanner.nextLine();
        if (taskMap.containsKey(id)) {
            taskMap.get(id).setDescription(newDescription);
        }
    }

    public void updateTitle(int id) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите новый заголовок для задачи id: " + id);
        String newTitle = scanner.nextLine();
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
                    allTasksByData = getAllByData(startDate.plusDays(i));
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





    public void printTaskMap() {
        for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void printCollection(Collection<?> collection) {
        System.out.println(collection);
    }




}
