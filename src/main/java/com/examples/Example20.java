package com.examples;

import org.riversun.promise.Func;
import org.riversun.promise.Promise;

/**
 * "java-promise"(https://github.com/riversun/java-promise)サンプル
 * resolvedのみでチェインする
 */
public class Example20 {

    public static void main(String[] args) {

        // 処理１（別スレッド実行）
        Func function1 = (action, data) -> {
            new Thread(() -> {
                System.out.println("Process-1");
                Promise.sleep(1000);// Thread.sleepと同じ
                action.resolve("Result-1");// ステータスを"fulfilled"にして、次の処理に結果("Result-1")を伝える
            }).start();// 別スレッドでの非同期処理開始
        };

        // 処理２
        Func function2 = (action, data) -> {
            System.out.println("Process-2 result=" + data);
            action.resolve();
        };

        Promise.resolve()// 処理を開始
                .then(function1)// 処理１実行
                .then(function2)// 処理２実行
                .start();// 開始

        System.out.println("Hello,Promise");
    }
}