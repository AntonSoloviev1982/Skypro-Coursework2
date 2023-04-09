package model;




import service.TaskService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class YearlyTask implements Taskable {
    @Override
    public boolean appearsIn(LocalDate localDate) {
        TaskService service = new TaskService();
        Set<LocalDate> dates = new HashSet<>();
        boolean result = false;
        RepeatabilityEnum repeatabilityEnum = RepeatabilityEnum.ЕЖЕГОДНАЯ;
        for (Task task : service.getTaskMap().values()) {
            if (repeatabilityEnum == task.getRepeatability()) {
                for (int i = 0; i <= (localDate.getDayOfYear() - task.getDateTime().getDayOfYear()); i += 365) {
                    dates.add(task.getDateTime().plusDays(i));
                }
            }
        }
        System.out.println(dates);
        if (dates.contains(localDate)) {
            result = true;
        }
        return result;
    }
}
