package com.github.greekpanda.jdk8.date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 下午 3:50
 */
public class TestLocalDateTime {
    //1. LocalDate、LocalTime、LocalDateTime
    @Test
    public void test1() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        System.out.println("---------------------");

        LocalDateTime ldt1 = LocalDateTime.of(2020, 6, 5, 15, 52, 52);
        System.out.println(ldt1);

        System.out.println("---------------------");

        LocalDateTime ldt2 = ldt1.plusYears(5).plusDays(10).plusMonths(20);
        System.out.println(ldt2);

        System.out.println("---------------------");

        LocalDateTime ldt3 = ldt1.minusMonths(3);
        System.out.println(ldt3);

        System.out.println("---------------------");

        System.out.println("Year: " + ldt.getYear()
                + " month: " + ldt.getMonth()
                + " day: " + ldt.getDayOfMonth()
                + " hour: " + ldt.getHour()
                + " minute: " + ldt.getMinute()
                + " second: " + ldt.getSecond());
    }

    //2. Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
    @Test
    public void test2() {
        Instant instant = Instant.now();
        System.out.println(instant);

        System.out.println("-----------------------");

        OffsetDateTime odt = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);

        System.out.println("-----------------------");

        System.out.println(instant.getNano());
        System.out.println(instant.getEpochSecond());

        System.out.println("-----------------------");

        Instant instant1 = Instant.ofEpochSecond(5L);
        System.out.println(instant1);
    }

    //3.
    //Duration : 用于计算两个“时间”间隔
    //Period : 用于计算两个“日期”间隔
    @Test
    public void test3() {
        Instant instant = Instant.now();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant instant1 = Instant.now();
        System.out.println("耗费时间：" + Duration.between(instant, instant1));

        System.out.println("-------------------------");

        LocalDate ldt1 = LocalDate.now();
        LocalDate ldt2 = LocalDate.of(2015, 5, 2);

        Period p = Period.between(ldt1, ldt2);
        System.out.println("year: " + p.getYears() + " month: " + p.getMonths() + " days: " + p.getDays());
    }

    //4. TemporalAdjuster : 时间校正器
    @Test
    public void test4() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        LocalDateTime ldt1 = ldt.withDayOfMonth(10);
        System.out.println(ldt1);

        System.out.println("-------------------------");

        LocalDateTime ldt2 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt2);

        System.out.println("-------------------------");

        //自定义下一个工作日
        LocalDateTime ldt3 = ldt.with(ll -> {
            LocalDateTime ldt4 = (LocalDateTime) ll;
            DayOfWeek dow = ldt4.getDayOfWeek();
            if (dow.equals(DayOfWeek.FRIDAY)) {
                return ldt4.plusDays(3);
            } else if (dow.equals(DayOfWeek.SATURDAY)) {
                return ldt4.plusDays(2);
            } else
                return ldt4.plusDays(1);
        });
        System.out.println(ldt3);
    }

    //5. DateTimeFormatter : 解析和格式化日期或时间
    @Test
    public void test5() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss E");
        LocalDateTime ldt = LocalDateTime.now();
        String strDateTime = ldt.format(dtf);
        System.out.println(strDateTime);

        System.out.println("----------------------");

        LocalDateTime ldt1 = LocalDateTime.parse(strDateTime, dtf);
        System.out.println(ldt1);

    }

    //6.ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
    @Test
    public void test6() {
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
    }

    @Test
    public void test7() {
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(ldt);

        System.out.println("-------------------------");

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println(zdt);
    }
}
