package com.github.greekpanda.jdk8.lambda;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * * 一、Stream API 的操作步骤：
 * *
 * * 1. 创建 Stream
 * *
 * * 2. 中间操作
 * *
 * * 3. 终止操作(终端操作)
 *
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 下午 3:01
 */
public class TestStreamApi {
    //1. 创建流
    @Test
    public void test1() {
        //1. Collection 提供了两个方法  stream() 与 parallelStream()
        List<String> list = Arrays.asList("a", "b", "c", "d", "e", "abc");
        Stream<String> stream = list.stream(); //获取一个顺序流
        stream.forEach(System.out::println);
        Stream<String> parallelStream = list.parallelStream(); //获取一个并行流
        parallelStream.forEach(System.out::println);

        System.out.println("--------------------------------");
        //2. 通过 Arrays 中的 stream() 获取一个数组流
        Integer[] nums = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Stream<Integer> stream1 = Arrays.stream(nums);
        stream1.forEach(System.out::println);
        System.out.println("--------------------------------");
        //3. 通过 Stream 类中静态方法 of()
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5, 6);
        stream2.forEach(System.out::println);
        System.out.println("-------------------------------------");

        //4. 创建无限流
        //迭代
        Stream<Integer> stream3 = Stream.iterate(0, x -> x + 2).limit(10);
        stream3.forEach(System.out::println);

        System.out.println("====================================");

        //5. 生成流
        Stream<Double> stream4 = Stream.generate(Math::random).limit(10);
        stream4.forEach(System.out::println);
    }

    //2. 中间操作
    private List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, BigDecimal.valueOf(6666.66)),
            new Employee(101, "张三", 18, BigDecimal.valueOf(9999.99)),
            new Employee(103, "王五", 28, BigDecimal.valueOf(3333.33)),
            new Employee(104, "赵六", 8, BigDecimal.valueOf(7777.77)),
            new Employee(104, "赵六", 8, BigDecimal.valueOf(7777.77)),
            new Employee(104, "赵六", 8, BigDecimal.valueOf(7777.77)),
            new Employee(105, "田七", 38, BigDecimal.valueOf(5555.55))
    );

    /*
	  筛选与切片
		filter——接收 Lambda ， 从流中排除某些元素。
		limit——截断流，使其元素不超过给定数量。
		skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
	 */

    @Test
    public void test2() {
        Stream<Employee> stream = emps.stream()
                .filter(e -> {
                    System.out.println("中间过程");
                    return e.getAge() <= 35;
                });
        stream.forEach(System.out::println);
    }

    //外部迭代
    @Test
    public void test3() {
        Iterator<Employee> iterator = emps.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("----------------------");
        //或者使用foreach
        for (Employee e : emps) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void test4() {
        emps.stream().filter(e -> {
            System.out.println("短路");
            return e.getSalary().compareTo(BigDecimal.valueOf(5000.0)) >= 0;
        }).limit(3).forEach(System.out::println);
    }

    @Test
    public void test5() {
        emps.stream().filter(e -> e.getSalary().compareTo(BigDecimal.valueOf(5000.0)) >= 0)
                .skip(2)
                .forEach(System.out::println);
    }

    @Test
    public void test6() {
        emps.stream().filter(e -> e.getAge() <= 35)
                .distinct()
                .forEach(System.out::println);
    }
}
