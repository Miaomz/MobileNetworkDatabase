package org.casual.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class DateTimeUtil {

    private DateTimeUtil() {}

    /**
     * check if two local dates have the same year and month values
     * @param date1 local date
     * @param date2 local date
     * @return is the same month
     */
    public static boolean isSameMonth(LocalDate date1, LocalDate date2){
        return date1.getYear() == date2.getYear() && date1.getMonthValue() == date2.getMonthValue();
    }

    public static LocalDateTime getStartOfCurrentMonth(){
        return LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
    }
}
