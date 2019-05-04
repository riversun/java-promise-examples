package com.examples;

import org.riversun.promise.Func;
import org.riversun.promise.Promise;

/**
 * Examples for "java-promise"(https://github.com/riversun/java-promise)
 * Move on to the next operation with "resolved" only
 */
public class Example20 {

    public static void main(String[] args) {

        // Operation 1 (on another thread)
        Func function1 = (action, data) -> {
            new Thread(() -> {
                System.out.println("Process-1");
                Promise.sleep(1000);// Same as Thread.sleep
                action.resolve("Result-1");// Make the status "fulfilled" and pass the result("Result-1") to the next operation
            }).start();// Start asynchronous operation on another thread for this operation.
        };

        // Operation 2
        Func function2 = (action, data) -> {
            System.out.println("Process-2 result=" + data);
            action.resolve();
        };

        Promise.resolve()
                .then(function1)// Do operation 1
                .then(function2)// Do operation 2
                .start();// Start asynchronous operations

        System.out.println("Hello,Promise");
    }
}