package com.github.greekpanda.jdk8.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 下午 1:58
 */
public class TestMethodRef {
    //数组引用
    @Test
    public void testArrayRef() {
        Function<Integer, String[]> fun = args -> new String[args];
        String[] str = fun.apply(10);
        System.out.println(str.length);
        for (String s : str) {
            System.out.print("\t" + s);
        }

        System.out.println("========================");
        Function<Integer, Employee[]> fun1 = Employee[]::new;
        Employee[] emps = fun1.apply(10);
        System.out.println(emps.length);
        for (Employee e : emps) {
            System.out.print("\t" + e.toString());
        }
    }

    //构造器引用
    //注意构造器的传入的类型和function的第一个参数的类型必须要一致
    @Test
    public void testConstructorRef() {
        Function<Integer, Employee> fun1 = Employee::new;
        BiFunction<String, Integer, Employee> fun2 = Employee::new;
    }

    @Test
    public void testConstructorRef1() {
        Supplier<Employee> supplier = Employee::new;
        System.out.println(supplier.get());
    }

    //类名：：实例方法名
    @Test
    public void testClassAndInstance() {
        BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);
        System.out.println(biPredicate.test("abc", "abc"));
        System.out.println(biPredicate.test("abcd", "abc"));

        System.out.println("----------------------------------");

        BiPredicate<String, String> biPredicate1 = String::equals;
        System.out.println(biPredicate1.test("abc", "abc"));
        System.out.println(biPredicate1.test("abcd", "abc"));

        System.out.println("----------------------------------");
        Function<Employee, String> function = Employee::show;
        System.out.println(function.apply(new Employee()));

    }

    //类名::静态方法名
    @Test
    public void testClassStaticMethod() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        System.out.println("------------------------");
        Comparator<Integer> com1 = Integer::compare;

    }


}
