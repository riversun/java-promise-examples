package com.examples;

import org.riversun.promise.Func;
import org.riversun.promise.Promise;

/**
 * Examples for "java-promise"(https://github.com/riversun/java-promise)
 * Handling "reject"
 */
public class Example21 {

    public static void main(String[] args) {

        Func function1 = (action, data) -> {
            System.out.println("Process-1");
            action.reject();// Make the status "rejected" and finish execution
        };

        Func function2_1 = (action, data) -> {
            System.out.println("Resolved Process-2");
            action.resolve();
        };

        Func function2_2 = (action, data) -> {
            System.out.println("Rejected Process-2");
            action.resolve();
        };

        Promise.resolve()
                .then(function1)
                .then(
                        function2_1, // Execute when the status is "fulfilled"
                        function2_2 // Execute when the status is "rejected"
                )
                .start();// start asynchronous operations

        System.out.println("Hello,Promise");

    }
}
