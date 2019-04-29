package com.demo.spring.common.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: 鲁砚琨
 * @Date: 2019/2/28 10:50
 * @Version: v1.0
 */
public class ThreadPool {


    private static ThreadPool threadPool;
    private static final int CPU_COUNT;
    private static boolean init;
    private final int corePoolSize;
    private final int maxPoolSize;
    private ThreadPoolExecutor executor;

    private static final int DEFAULT_CORE_POOL_SIZE;
    private static final int DEFAULT_MAX_POOL_SIZE;
    private static final int DEFAULT_KEEPALIVE_TIME;
    private static final int DEFAULT_QUEUE_SIZE;

    static {
        CPU_COUNT = 15;
        DEFAULT_CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * CPU_COUNT;
        DEFAULT_MAX_POOL_SIZE = 200;
        DEFAULT_KEEPALIVE_TIME = 3000;
        DEFAULT_QUEUE_SIZE = 1000;
    }

    /**
     * 1. CallerRunsPolicy ：这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
     * 2. AbortPolicy ：对拒绝任务抛弃处理，并且抛出异常。
     * 3. DiscardPolicy ：对拒绝任务直接无声抛弃，没有异常信息。
     * 4. DiscardOldestPolicy ：对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
     */
    private ThreadPool() {
        this.corePoolSize = DEFAULT_CORE_POOL_SIZE;
        this.maxPoolSize = DEFAULT_MAX_POOL_SIZE;
        this.executor = new ThreadPoolExecutor(
                corePoolSize, // 线程池维护线程的最少数量，哪怕是空闲的
                maxPoolSize, // 线程池维护线程的最大数量
                DEFAULT_KEEPALIVE_TIME, //  线程池维护线程所允许的空闲时间
                TimeUnit.MILLISECONDS, // 线程池维护线程所允许的空闲时间的单位
                new ArrayBlockingQueue<>(DEFAULT_QUEUE_SIZE), // 线程池所使用的缓冲队列，改缓冲队列的长度决定了能够缓冲的最大数量
                new ThreadPoolExecutor.CallerRunsPolicy() // 这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
        );
    }

    /**
     * 1. CallerRunsPolicy ：这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
     * 2. AbortPolicy ：对拒绝任务抛弃处理，并且抛出异常。
     * 3. DiscardPolicy ：对拒绝任务直接无声抛弃，没有异常信息。
     * 4. DiscardOldestPolicy ：对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
     */
    private ThreadPool(int corePoolSize){
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = DEFAULT_MAX_POOL_SIZE;
        this.executor = new ThreadPoolExecutor(
                corePoolSize, // 线程池维护线程的最少数量，哪怕是空闲的
                maxPoolSize, // 线程池维护线程的最大数量
                DEFAULT_KEEPALIVE_TIME, //  线程池维护线程所允许的空闲时间
                TimeUnit.MILLISECONDS, // 线程池维护线程所允许的空闲时间的单位
                new ArrayBlockingQueue<>(DEFAULT_QUEUE_SIZE), // 线程池所使用的缓冲队列，改缓冲队列的长度决定了能够缓冲的最大数量
                new ThreadPoolExecutor.CallerRunsPolicy() // 这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
        );
    }

    /**
     * 1. CallerRunsPolicy ：这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
     * 2. AbortPolicy ：对拒绝任务抛弃处理，并且抛出异常。
     * 3. DiscardPolicy ：对拒绝任务直接无声抛弃，没有异常信息。
     * 4. DiscardOldestPolicy ：对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
     */
    private ThreadPool(RejectedExecutionHandler handler){
        this.corePoolSize = DEFAULT_CORE_POOL_SIZE;
        this.maxPoolSize = DEFAULT_MAX_POOL_SIZE;
        this.executor = new ThreadPoolExecutor(
                corePoolSize, // 线程池维护线程的最少数量，哪怕是空闲的
                maxPoolSize, // 线程池维护线程的最大数量
                DEFAULT_KEEPALIVE_TIME, //  线程池维护线程所允许的空闲时间
                TimeUnit.MILLISECONDS, // 线程池维护线程所允许的空闲时间的单位
                new ArrayBlockingQueue<>(DEFAULT_QUEUE_SIZE), // 线程池所使用的缓冲队列，改缓冲队列的长度决定了能够缓冲的最大数量
                handler // 这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
        );
    }

    /**
     * 初始化默认线程池
     */
    public synchronized static ThreadPool init(){
        if (!init) {
            init = true;
            threadPool = new ThreadPool();
        }
        return threadPool;
    }

    /**
     * 初始化指定核心线程数的线程池
     */
    public synchronized static ThreadPool init(int corePoolSize){
        if (!init) {
            init = true;
            threadPool = new ThreadPool(corePoolSize);
        }
        return threadPool;
    }

    /**
     * 指定线程处理方式的线程池
     * 1. CallerRunsPolicy ：这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
     * 2. AbortPolicy ：对拒绝任务抛弃处理，并且抛出异常。
     * 3. DiscardPolicy ：对拒绝任务直接无声抛弃，没有异常信息。
     * 4. DiscardOldestPolicy ：对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
     */
    public synchronized static ThreadPool init(RejectedExecutionHandler handler){
        if (!init) {
            init = true;
            threadPool = new ThreadPool(handler);
        }
        return threadPool;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPool.executor;
    }
    /**
     *
     * 获取当前执行线程数量
     */
    public int getExecutorActiveCount() {
        return threadPool.executor.getActiveCount();
    }


    /**
     * 查询当前线程是否已经关闭
     */
    public boolean isShutdown() {
        return threadPool.executor.isShutdown();
    }

    /**
     * 关闭线程池
     */
    public void shutdown(){
        if (!isShutdown())
        threadPool.executor.shutdown();
    }

    public void executor(Runnable runnable) {
        threadPool.executor.execute(runnable);
    }

    public Future<?> submit(Callable<?> callable) {
        return threadPool.executor.submit(callable);
    }
}
