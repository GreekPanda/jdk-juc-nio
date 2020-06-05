package com.github.greekpanda.jdk8.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * * Java8 内置的四大核心函数式接口
 * *
 * * Consumer<T> : 消费型接口
 * * 		void accept(T t);
 * *
 * * Supplier<T> : 供给型接口
 * * 		T get();
 * *
 * * Function<T, R> : 函数型接口
 * * 		R apply(T t);
 * *
 * * Predicate<T> : 断言型接口
 * * 		boolean test(T t);
 *
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 下午 12:11
 */
public class Test3 {

    //Predicate断言接口
    @Test
    public void testPredicate() {
        List<String> list1 = Arrays.asList("hello", "java8", "lambda", "this", "is", "greekpanda");
        List<String> filterStrResult = filterStr(list1, (s) -> s.length() > 5);

        for (String s : filterStrResult) {
            System.out.println(s);
        }
    }

    //将满足条件的数值放入集合中
    private List<String> filterStr(List<String> list, Predicate<String> ps) {
        List<String> lstStringRet = new ArrayList<>();
        for (String s : list) {
            if (ps.test(s)) {
                lstStringRet.add(s);
            }
        }
        return lstStringRet;
    }

    /////////////////////////////////////////////////

    //Function<T, R> 函数型接口：
    @Test
    public void testFunction() {
        String s = strHandler("\t\t\t\t\t\t\tHello java8 lambda", str -> str.trim());
        System.out.println(s);
        //或者简写
        String s1 = strHandler("\t\t\t\t\tHello java8 lambda s2\t\t\t", String::trim);
        System.out.println(s1);

        String s2 = strHandler("测试java8 lambda", str -> str.substring(2, 7));
        System.out.println(s2);


    }

    private String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    ////Supplier<T> 供给型接口 :
    @Test
    public void testSupplier() {
        List<Integer> numList = handlerInteger(100, () -> (int) (Math.random() * 100));
        for (Integer n : numList) {
            System.out.print("\t" + n);
        }
    }

    //指定产生个整数，放入集合中
    private List<Integer> handlerInteger(int num, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; ++i) {
            Integer n = supplier.get();
            list.add(n);
        }
        return list;
    }

    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    ////Consumer<T> 消费型接口 :
    private void accept(String s, Consumer<String> con) {
        con.accept(s);
    }

    @Test
    public void testConsumer() {
        accept("Hello java8 lambda", (s) -> System.out.println(s));
        //或者可以简化
        accept("Hello real world sucks", System.out::println);
    }
}
