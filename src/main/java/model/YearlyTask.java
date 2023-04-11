package model;


import java.time.LocalDate;

public class YearlyTask implements Taskable {
    @Override
    public boolean appearsIn(LocalDate localDate, Task task) {
        boolean result = false;
        for (int i = 0; i <= (localDate.getDayOfYear() - task.getDateTime().getDayOfYear()); i += 365) {
            if (task.getDateTime().plusDays(i).equals(localDate)) {
                result = true;
            }
        }
        return result;
    }
}
