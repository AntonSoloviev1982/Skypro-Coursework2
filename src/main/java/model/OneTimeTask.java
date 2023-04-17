package model;

import java.time.LocalDate;

public class OneTimeTask implements Taskable {
    @Override
    public boolean appearsIn(LocalDate localDate, Task task) {
        boolean result = false;
        if (task.getDateTime().equals(localDate)) {
            result = true;
        }
        return result;
    }
}
