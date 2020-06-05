package com.github.greekpanda.jdk8.lambda;

/**
 * 使用了@FunctionalInterface，表明此接口是一个函数式接口，可以直接使用函数式调用
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 上午 11:12
 */
@FunctionalInterface
public interface MyPredicate<T> {
    boolean test(T t);
}
