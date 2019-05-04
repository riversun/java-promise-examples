package com.examples;

import org.riversun.promise.Promise;

/**
 * "java-promise"(https://github.com/riversun/java-promise)サンプル
 * then(new Promise(Func))でチェインする
 */
public class Example01 {

    public static void main(String[] args) {

        Promise.resolve("foo")
                .then(new Promise((action, data) -> {
                    new Thread(() -> {
                        String newData = data + "bar";
                        action.resolve(newData);
                    }).start();
                }))
                .then(new Promise((action, data) -> {
                    System.out.println(data);
                    action.resolve();
                }))
                .start();// 非同期処理開始
        System.out.println("Promise in Java");
    }
}