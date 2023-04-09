package test;

import model.RepeatabilityEnum;
import model.TypeEnum;
import service.TaskService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        TaskService service = new TaskService();
        service.add(RepeatabilityEnum.ОДНОКРАТНАЯ, TypeEnum.ЛИЧНЫЕ);
        service.add(RepeatabilityEnum.ЕЖЕДНЕВНАЯ, TypeEnum.ЛИЧНЫЕ);
        service.add(RepeatabilityEnum.ЕЖЕНЕДЕЛЬНАЯ, TypeEnum.РАБОЧИЕ);
        service.add(RepeatabilityEnum.ЕЖЕМЕСЯЧНАЯ, TypeEnum.РАБОЧИЕ);

        service.printTaskMap();

        System.out.println("getAllByData" + service.getAllByData(LocalDate.of(2023, 5, 6)));

        service.updateTitle(1);
        service.updateDescription(1);

        service.remove(2);

        service.printCollection(service.getRemovedTasks());

        service.getAllGroupByData();
    }
}
