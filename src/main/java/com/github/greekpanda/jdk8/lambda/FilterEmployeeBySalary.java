package com.github.greekpanda.jdk8.lambda;

import java.math.BigDecimal;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 上午 11:29
 */
public class FilterEmployeeBySalary implements MyPredicate<Employee> {
    @Override
    public boolean test(Employee e) {
        return e.getSalary().compareTo(BigDecimal.valueOf(5000.0)) >= 0;
    }
}
