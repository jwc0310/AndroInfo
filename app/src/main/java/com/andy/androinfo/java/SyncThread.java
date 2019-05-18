package com.andy.androinfo.java;

public class SyncThread implements Runnable {
    private Integer key = 0;
    @Override
    public void run() {
        // key是Integer对象（注意不是int，因为int不是对象）
        // 线程进入下面同步代码之前，需要先获取key的锁。
        // 需要结果是key实现自增长，如果没有同步块，则可能会出现重复key值的现象
        synchronized (this) {
            key++;

            System.out.println(Thread.currentThread().getName() + ":" + key);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String args[]) {
        SyncThread thread = new SyncThread();
        for (int i = 0; i < 10; i++) {
            new Thread(thread, "Thread"+i).start();
        }
    }

}
