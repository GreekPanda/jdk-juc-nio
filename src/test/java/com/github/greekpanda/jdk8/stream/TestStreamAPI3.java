package com.github.greekpanda.jdk8.stream;

import com.github.greekpanda.jdk8.date.Employee;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 下午 5:59
 */
public class TestStreamAPI3 {
    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 79, 6666.66, Employee.Status.BUSY),
            new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
            new Employee(103, "王五", 28, 3333.33, Employee.Status.VOCATION),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.BUSY),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
            new Employee(105, "田七", 38, 5555.55, Employee.Status.BUSY)
    );

    //3. 终止操作
	/*
		归约
		reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
	 */
    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = list.stream().reduce(0, Integer::sum);
        System.out.println(sum);

        System.out.println("---------------------");

        Optional<Double> op = emps.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(op);
    }

    //搜索某一个出现的次数
    @Test
    public void test2() {
        Optional<Integer> sum = emps.stream()
                .map(Employee::getName)
                .flatMap(TestStreamAPI1::filterChar)
                .map(c -> {
                    if (c.equals('六'))
                        return 1;
                    else
                        return 0;
                }).reduce(Integer::sum);
        System.out.println(sum.get());
    }

    //collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
    @Test
    public void test3() {
        List<String> list = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        System.out.println(list);

        System.out.println("-------------------");

        Set<String> set = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        System.out.println(set);

        System.out.println("-------------------");

        HashSet<String> hs = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        System.out.println(hs);
    }

    @Test
    public void test4() {
        Optional<Double> op = emps.stream()
                .map(Employee::getSalary).max(Double::compare);
        System.out.println(op.get());

        System.out.println("----------------------------");

        Optional<Employee> op1 = emps.stream().min(Comparator.comparingDouble(Employee::getSalary));
        System.out.println(op1.get());

        System.out.println("--------------------------------");

        Double sum = emps.stream().mapToDouble(Employee::getSalary).sum();
        System.out.println(sum);

        System.out.println("--------------------------------");

        OptionalDouble avg = emps.stream().mapToDouble(Employee::getSalary).average();
        System.out.println(avg.getAsDouble());

        System.out.println("--------------------------------");

        DoubleSummaryStatistics dss = emps.stream().mapToDouble(Employee::getSalary).summaryStatistics();
        System.out.println(dss);
    }

    //分组
    @Test
    public void test5() {
        Map<Employee.Status, List<Employee>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);
    }

    //多机分组
    @Test
    public void test6() {
        Map<Employee.Status, Map<String, List<Employee>>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
                    if (e.getAge() >= 60)
                        return "old";
                    else if (e.getAge() < 60 && e.getAge() >= 35)
                        return "middle age";
                    else
                        return "young";
                })));
        System.out.println(map);
    }

    //分区
    @Test
    public void test7() {
        Map<Boolean, List<Employee>> map = emps.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() >= 5000));
        System.out.println(map);
    }

    @Test
    public void test8() {
        String s = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",", "==", "----"));
        System.out.println(s);
    }

    @Test
    public void test9() {
        Optional<Double> sum = emps.stream()
                .map(Employee::getSalary).reduce(Double::sum);
        System.out.println(sum);
    }
}
