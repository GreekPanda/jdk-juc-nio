package com.github.greekpanda.jdk8.defaultinterface;

import com.github.greekpanda.jdk8.date.MyInterface;
import com.github.greekpanda.jdk8.date.SubClass;
import org.junit.Test;

/**
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/5 0005 下午 3:45
 */
public class TestDefaultInterface {
    @Test
    public void testDefaultInterface() {
        SubClass sc = new SubClass();
        //SubClass实现了MyInterface接口，所以会先执行super.getName方法
        System.out.println(sc.getName());
        MyInterface.show();
    }
}
