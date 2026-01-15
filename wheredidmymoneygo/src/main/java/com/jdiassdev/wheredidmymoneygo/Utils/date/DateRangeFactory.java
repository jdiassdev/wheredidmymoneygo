package com.jdiassdev.wheredidmymoneygo.Utils.date;

import java.time.LocalDate;

public class DateRangeFactory {
    public static DateRange currentMonth() {
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.withDayOfMonth(1);
        LocalDate lastDay = now.withDayOfMonth(now.lengthOfMonth());
        return new DateRange(firstDay, lastDay);
    }
}
