package com.examples;

import org.riversun.promise.Promise;

/**
 * "java-promise"(https://github.com/riversun/java-promise)サンプル
 * then(Func)でチェインする
 */
public class Example10 {

    public static void main(String[] args) {

        Promise.resolve("foo")
                .then((action, data) -> {
                    new Thread(() -> {
                        String newData = data + "bar";
                        action.resolve(newData);
                    }).start();
                })
                .then((action, data) -> {
                    System.out.println(data);
                    action.resolve();
                })
                .start();// 非同期処理開始
        System.out.println("Promise in Java");
    }
}