package com.examples;

import java.util.List;
import org.riversun.promise.Func;
import org.riversun.promise.Promise;

/**
 * Examples for "java-promise"(https://github.com/riversun/java-promise)
 * Concurrent execution with Promise.all
 */
public class Example40 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        // Asynchronous operation 1
        Func function1 = (action, data) -> {
            new Thread(() -> {
                Promise.sleep(1000);
                System.out.println("func1 running");
                action.resolve("func1-result");
            }).start();
        };

        // Asynchronous operation 2
        Func function2 = (action, data) -> {
            new Thread(() -> {
                Promise.sleep(500);
                System.out.println("func2 running");
                action.resolve("func2-result");
            }).start();
        };

        // Asynchronous operation 3
        Func function3 = (action, data) -> {
            new Thread(() -> {
                Promise.sleep(100);
                System.out.println("func3 running");
                action.resolve("func3-result");
            }).start();
        };
        // Operation to receive final result
        Func function4 = (action, data) -> {
            System.out.println("Received the final result.");
            List<Object> resultList = (List<Object>) data;
            for (int i = 0; i < resultList.size(); i++) {
                Object result = resultList.get(i);
                System.out.println("The result of async operation" + (i + 1) + " is " + result);
            }

            action.resolve();
        };

        Promise.all(function1, function2, function3)
                .always(function4)
                .start();// start asynchronous operations
    }
}
