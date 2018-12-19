package com.liyou.uc.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.liyou.framework.base.utils.DateUtils.ZONE_ID_ASIA_SHANGHAI;

/**
 * @author: ivywooo
 * @date:2018/5/29
 **/

public class UserCenterDateUtils {

    /**
     * 工具类禁止实例化
     */
    private UserCenterDateUtils() {
        throw new AssertionError();
    }

    public static LocalDate dateToLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(ZONE_ID_ASIA_SHANGHAI)).toLocalDate();
    }

    public static Date localDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.of(ZONE_ID_ASIA_SHANGHAI)).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(ZONE_ID_ASIA_SHANGHAI));
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.of(ZONE_ID_ASIA_SHANGHAI)).toInstant();
        return Date.from(instant);
    }


    public static void main(String[] args) {
        Date now = new Date(0L);
        System.out.println(now);
//        LocalDate localDate = UserCenterDateUtils.dateToLocalDate(now);
//        System.out.println(localDate);
//        System.out.println(UserCenterDateUtils.localDateToDate(localDate));

    }

}
