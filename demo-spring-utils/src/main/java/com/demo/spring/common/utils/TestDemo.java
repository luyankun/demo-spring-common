package com.demo.spring.common.utils;

/**
 * @Description:
 * @Author: 鲁砚琨
 * @Date: 2019/2/28 14:03
 * @Version: v1.0
 */
public class TestDemo {

    public static void main(String[] args) {
        ThreadPool pool = ThreadPool.init();
        TestDemo testDemo = new TestDemo();
        System.out.println(pool.getExecutorActiveCount());
        pool.executor(()-> testDemo.test());
        pool.executor(()-> testDemo.demo());
        System.out.println(pool.getExecutorActiveCount());
    }


    public void test() {
        System.out.println("test");
    }

    public void demo() {
        System.out.println("demo");
    }
}
