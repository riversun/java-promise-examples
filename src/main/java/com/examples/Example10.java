package com.examples;

import org.riversun.promise.Promise;

/**
 * Examples for "java-promise"(https://github.com/riversun/java-promise)
 * Chain with then(Func)
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
                .start();// start asynchronous operations
        System.out.println("Promise in Java");
    }
}