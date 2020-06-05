package com.github.greekpanda.jdk8.optional;

import com.github.greekpanda.jdk8.lambda.Employee;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * * 一、Optional 容器类：用于尽量避免空指针异常
 * * 	Optional.of(T t) : 创建一个 Optional 实例
 * * 	Optional.empty() : 创建一个空的 Optional 实例
 * * 	Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
 * * 	isPresent() : 判断是否包含值
 * * 	orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
 * * 	orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
 * * 	map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
 * * 	flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
 *
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 下午 4:41
 */
public class TestOptional {

    @Test
    public void test1() {
        Optional<Employee> optionalEmployee = Optional.of(new Employee());
        Employee employee = optionalEmployee.get();
        System.out.println(employee);
    }

    @Test
    public void test2() {
        //这个会抛出异常，无法继续执行
        Optional<Employee> optionalEmployee = Optional.ofNullable(null);
        System.out.println(optionalEmployee.get());

        Optional<Employee> op = Optional.empty();
        System.out.println(op.get());
    }

    @Test
    public void test3() {
        Optional<Employee> optionalEmployee = Optional.ofNullable(new Employee());
        if (optionalEmployee.isPresent()) {
            System.out.println(optionalEmployee.get());
        }
        //下面是lambda表达式版本
        optionalEmployee.ifPresent(System.out::println);

        System.out.println("----------------------------");
        Employee e = new Employee("Tom");
        System.out.println(e.toString());
        Employee e1 = optionalEmployee.orElseGet(Employee::new);
        System.out.println(e1);
    }

    @Test
    public void test4() {
        Optional<Employee> op = Optional.of(new Employee(101, "张三", 18, BigDecimal.valueOf(9999.99)));
        Optional<String> op1 = op.map(Employee::getName);
        op1.ifPresent(System.out::println);
        //System.out.println(op1.get());

        System.out.println("--------------------------");
        Optional<String> op2 = op.flatMap( e -> Optional.of(e.getName()));
        op2.ifPresent(System.out::println);
        //System.out.println(op2.get());

    }
}
