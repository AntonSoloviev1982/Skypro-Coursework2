package model;

import java.time.LocalDate;

public interface Taskable {
    public boolean appearsIn(LocalDate localDate);
}
