package com.examples;

import org.riversun.promise.Promise;

/**
 * Examples for "java-promise"(https://github.com/riversun/java-promise)
 * chain with then(new Promise(Func))
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
                .start();// start asynchronous operations
        System.out.println("Promise in Java");
    }
}