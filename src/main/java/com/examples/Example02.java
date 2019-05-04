package com.examples;

import org.riversun.promise.Action;
import org.riversun.promise.Func;
import org.riversun.promise.Promise;

/**
 * "java-promise"(https://github.com/riversun/java-promise)サンプル
 * ラムダ式を使わずに記述する
 */
public class Example02 {

    public static void main(String[] args) {

        Promise.resolve("foo")
                .then(new Promise(new Func() {
                    @Override
                    public void run(Action action, Object data) throws Exception {
                        new Thread(() -> {
                            String newData = data + "bar";
                            action.resolve(newData);
                        }).start();
                    }
                }))
                .then(new Promise(new Func() {
                    @Override
                    public void run(Action action, Object data) throws Exception {
                        new Thread(() -> {
                            System.out.println(data);
                            action.resolve();
                        }).start();
                    }
                }))
                .start();// 非同期処理開始
        System.out.println("Promise in Java");
    }
}