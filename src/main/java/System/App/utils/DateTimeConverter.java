package System.App.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateTimeConverter {
    public static LocalDateTime convertDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    public static Timestamp convertTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
