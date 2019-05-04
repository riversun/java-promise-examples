package com.examples;

import java.util.List;
import org.riversun.promise.Func;
import org.riversun.promise.Promise;

/**
 * "java-promise"(https://github.com/riversun/java-promise)サンプル
 * Promise.allで並列実行する
 */
public class Example40 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        // 非同期処理１
        Func function1 = (action, data) -> {
            new Thread(() -> {
                Promise.sleep(1000);System.out.println("func1 running");action.resolve("func1-result");
            }).start();
        };

        // 非同期処理２
        Func function2 = (action, data) -> {
            new Thread(() -> {
                Promise.sleep(500);System.out.println("func2 running");action.resolve("func2-result");
            }).start();
        };

        // 非同期処理３
        Func function3 = (action, data) -> {
            new Thread(() -> {
                Promise.sleep(100);System.out.println("func3 running");action.resolve("func3-result");
            }).start();
        };
        // 最後に結果を受け取る処理
        Func function4 = (action, data) -> {
            System.out.println("結果を受け取りました");
            List<Object> resultList = (List<Object>) data;
            for (int i = 0; i < resultList.size(); i++) {
                Object result = resultList.get(i);
                System.out.println("非同期処理" + (i + 1) + "の結果は " + result);
            }

            action.resolve();
        };

        Promise.all(function1, function2, function3)
                .always(function4)
                .start();
    }
}
