package com.examples;

import org.riversun.promise.Func;
import org.riversun.promise.Promise;

/**
 * Examples for "java-promise"(https://github.com/riversun/java-promise)
 * Promise.always can receive both "reject" and "resolve"
 */
public class Example30 {

    public static void main(String[] args) {
        Func function1 = (action, data) -> {
            action.reject("I send REJECT");
        };
        Func function2 = (action, data) -> {
            System.out.println("Received:" + data);
            action.resolve();
        };
        Promise.resolve()
                .then(function1)
                .always(function2)// Execute either "fulfilled" or "rejected"
                .start();// start asynchronous operations
    }
}