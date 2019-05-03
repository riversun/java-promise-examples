package com.examples;

import org.riversun.promise.Func;
import org.riversun.promise.Promise;

/**
 * "java-promise"(https://github.com/riversun/java-promise)サンプル
 * rejectを処理する
 */
public class Example21 {

    public static void main(String[] args) {

        Func function1 = (action, data) -> {
            System.out.println("Process-1");
            action.reject();// ステータスを "rejected" にセットして実行完了
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
                        function2_1, // ステータスが fulfilled のときに実行される
                        function2_2 // ステータスが rejected のときに実行される
                )
                .start();

        System.out.println("Hello,Promise");

    }
}
