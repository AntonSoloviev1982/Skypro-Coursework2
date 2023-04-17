package test;


import service.TaskService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskService service = new TaskService();
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                service.printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextLine()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            service.add(scanner);
                            break;
                        case 2:
                            service.remove(scanner);
                            break;
                        case 3:
                            service.getAllByDay(scanner);
                            break;
                        case 4:
                            service.getRemovedTasks();
                            break;
                        case 5:
                            service.updateDescription(scanner);
                            break;
                        case 6:
                            service.updateTitle(scanner);
                            break;
                        case 7:
                            service.getAllGroupByData();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }

    }
}
