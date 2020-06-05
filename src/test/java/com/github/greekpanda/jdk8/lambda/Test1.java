package com.github.greekpanda.jdk8.lambda;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 上午 10:40
 */
public class Test1 {
    //使用匿名类进行比较，代码冗余
    @Test
    public void testAnonymous() {
        Comparator<String> cmp = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                System.out.println("use annonymous to test");
                return Integer.compare(o1.length(), o2.length());
            }
        };

        TreeSet<String> ts1 = new TreeSet<>(cmp);
        TreeSet<String> ts2 = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });
    }

    //使用了lambda的方式，代码简洁
    @Test
    public void useLambda() {
        System.out.println("test use lambda");
        Comparator<String> cmp = (x, y) -> Integer.compare(x.length(), y.length());
        TreeSet<String> ts = new TreeSet<>(cmp);
        System.out.println(String.valueOf(ts));
    }

    /////////////////////////////////////////////////////////////////
    //结合更具体例子

    private List<Employee> employeeList = Arrays.asList(
            new Employee(101, "张三", 18, BigDecimal.valueOf(9999.99)),
            new Employee(102, "李四", 59, BigDecimal.valueOf(6666.66)),
            new Employee(103, "王五", 28, BigDecimal.valueOf(3333.33)),
            new Employee(104, "赵六", 8, BigDecimal.valueOf(7777.77)),
            new Employee(105, "田七", 38, BigDecimal.valueOf(5555.55))
    );

    //正常使用方法调用来获取
    @Test
    public void getInfoByAge() {
        List<Employee> lstFilterByAge = filterEmployeeByAge(employeeList);
        for (Employee e : lstFilterByAge) {
            System.out.println(e.toString());
        }
        System.out.println("---------------------");
        List<Employee> lstFilterBySalary = filterEmployeeBySalary(employeeList);
        for (Employee e : lstFilterBySalary) {
            System.out.println(e.toString());
        }
    }


    private List<Employee> filterEmployeeByAge(List<Employee> lstEmployee) {
        List<Employee> lstFilterEmployeeByAge = new ArrayList<>();
        for (Employee e : lstEmployee) {
            if (e.getAge() <= 35) {
                lstFilterEmployeeByAge.add(e);
            }
        }
        return lstFilterEmployeeByAge;
    }

    private List<Employee> filterEmployeeBySalary(List<Employee> lstEmployee) {
        List<Employee> lstFilterEmployeeBySalary = new ArrayList<>();
        for (Employee e : lstEmployee) {
            if (e.getSalary().compareTo(BigDecimal.valueOf(5000)) >= 0) {
                lstFilterEmployeeBySalary.add(e);
            }
        }
        return lstFilterEmployeeBySalary;
    }

    /////////////////////////////////////////////////////////////
    ///使用策略设计模式
    private List<Employee> filterEmployee(List<Employee> lstEmployee, MyPredicate<Employee> mpEmployee) {
        List<Employee> retList = new ArrayList<>();
        for (Employee e : lstEmployee) {
            if (mpEmployee.test(e)) {
                retList.add(e);
            }
        }
        return retList;
    }

    @Test
    public void testByFilterWithStrategyPattern() {
        List<Employee> listEmployee = filterEmployee(employeeList, new FilterEmployeeByAge());
        for (Employee e : listEmployee) {
            System.out.println(e.toString());
        }
        System.out.println("===============================");
        List<Employee> list1 = filterEmployee(employeeList, new FilterEmployeeBySalary());
        for (Employee e : list1) {
            System.out.println(e.toString());
        }
    }

    //1.使用匿名内部类进行优化
    @Test
    public void testByFilterWithAnonymous() {
        List<Employee> list1 = filterEmployee(employeeList, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getAge() < 35;
            }
        });
        for (Employee e : list1) {
            System.out.println(e.toString());
        }

        System.out.println("=========================");
        List<Employee> list2 = filterEmployee(employeeList, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary().compareTo(BigDecimal.valueOf(5000)) >= 0;
            }
        });
        for (Employee e : list2) {
            System.out.println(e.toString());
        }
    }

    //直接使用lambda
    @Test
    public void testByFilterLambda() {
        List<Employee> list1 = filterEmployee(employeeList, (e) -> e.getAge() <= 35);
        list1.forEach(System.out::println);
        System.out.println("=============================");
        List<Employee> list2 = filterEmployee(employeeList, (e) -> e.getSalary().compareTo(BigDecimal.valueOf(5000)) >= 0);
        list2.forEach(System.out::println);
    }
}
