package com.github.greekpanda.jdk8.date;

public interface MyInterface {

    default String getName() {
        return "MyInterface getName";
    }

    static void show() {
        System.out.println("接口中的静态方法");
    }

}
