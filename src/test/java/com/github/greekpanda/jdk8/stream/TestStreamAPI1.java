package com.github.greekpanda.jdk8.stream;

import com.github.greekpanda.jdk8.lambda.Employee;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * * 一、 Stream 的操作步骤
 * *
 * * 1. 创建 Stream
 * *
 * * 2. 中间操作
 * *
 * * 3. 终止操作
 *
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 下午 5:04
 */
public class TestStreamAPI1 {
    private List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, BigDecimal.valueOf(6666.66)),
            new Employee(101, "张三", 18, BigDecimal.valueOf(9999.99)),
            new Employee(103, "王五", 28, BigDecimal.valueOf(3333.33)),
            new Employee(104, "赵六", 8, BigDecimal.valueOf(7777.77)),
            new Employee(104, "赵六", 8, BigDecimal.valueOf(7777.77)),
            new Employee(104, "赵六", 8, BigDecimal.valueOf(7777.77)),
            new Employee(105, "田七", 38, BigDecimal.valueOf(5555.55))
    );

    //2. 中间操作
	/*
		映射
		map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，
		并将其映射成一个新的元素。
		flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
    @Test
    public void test1() {
        emps.stream().map(Employee::getName).forEach(System.out::println);

        System.out.println("------------------------------");

        List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        strList.stream().map(String::toUpperCase).forEach(System.out::println);

        System.out.println("------------------------------");

        Stream<Stream<Character>> stream1 = strList.stream()
                .map(TestStreamAPI1::filterChar);

        stream1.forEach(s -> s.forEach(System.out::println));

        System.out.println("------------------------------");

        Stream<Character> stream2 = strList.stream()
                .flatMap(TestStreamAPI1::filterChar);

        stream2.forEach(System.out::println);

    }

    public static Stream<Character> filterChar(String str) {
        List<Character> list = new ArrayList<>();

        for (Character c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

    /*
		sorted()——自然排序
		sorted(Comparator com)——定制排序
	 */
    @Test
    public void test2() {
        emps.stream().map(Employee::getName).forEach(System.out::println);
        System.out.println("-----------------------------");

        emps.stream().sorted((x, y) -> {
            if (x.getAge().equals(y.getAge())) {
                return x.getName().compareTo(y.getName());
            } else {
                return Integer.compareUnsigned(x.getAge(), y.getAge());
            }
        }).forEach(System.out::println);
    }

}
