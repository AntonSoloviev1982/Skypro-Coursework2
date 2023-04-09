package model;



import service.TaskService;

import java.time.LocalDate;

public class OneTimeTask implements Taskable {

    TaskService service = new TaskService();
    @Override
    public boolean appearsIn(LocalDate localDate) {
        boolean result = false;
        RepeatabilityEnum repeatabilityEnum = RepeatabilityEnum.ОДНОКРАТНАЯ;
        for (Task task: service.getTaskMap().values()) {
            if (repeatabilityEnum == task.getRepeatability()) {
                if (localDate.equals(task.getDateTime())) {
                    result = true;
                }
            }
        }
        return result;
    }
}
