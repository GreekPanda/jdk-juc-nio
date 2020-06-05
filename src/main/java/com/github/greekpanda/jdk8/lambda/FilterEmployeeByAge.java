package com.github.greekpanda.jdk8.lambda;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 上午 11:27
 */
public class FilterEmployeeByAge implements MyPredicate<Employee> {
    @Override
    public boolean test(Employee e) {
        return e.getAge() <= 35;
    }
}
