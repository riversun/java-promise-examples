package com.examples;

import org.riversun.promise.Func;
import org.riversun.promise.Promise;

/**
 * "java-promise"(https://github.com/riversun/java-promise)サンプル
 * Promise.alwaysはrejectもresolveも両方受け取れる
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
                .always(function2)// ステータスが"fulfilled"でも"rejected"でも実行される
                .start();
    }
}