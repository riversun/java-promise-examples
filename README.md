# examples for "java-promise"

**java-promise** is a Promise library for Java.
https://github.com/riversun/java-promise

It is licensed under [MIT](https://opensource.org/licenses/MIT).

# Java Promise examples

## Dependency

**Maven**

```xml
<dependency>
  <groupId>org.riversun</groupId>
  <artifactId>java-promise</artifactId>
  <version>1.1.0</version>
</dependency>
```

## Chain with then(new Promise(Func))

```Java
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
```

Result:

```
Promise in Java
foobar
```

## Write code without lambda expression

```Java
/**
 * Examples for "java-promise"(https://github.com/riversun/java-promise)
 * Write code without lambda expression
 */
public class Example02 {

    public static void main(String[] args) {

        Promise.resolve("foo")
                .then(new Promise(new Func() {
                    @Override
                    public void run(Action action, Object data) throws Exception {
                        new Thread(() -> {
                            String newData = data + "bar";
                            action.resolve(newData);
                        }).start();
                    }
                }))
                .then(new Promise(new Func() {
                    @Override
                    public void run(Action action, Object data) throws Exception {
                        new Thread(() -> {
                            System.out.println(data);
                            action.resolve();
                        }).start();
                    }
                }))
                .start();// start asynchronous operations
        System.out.println("Promise in Java");
    }
}
```

Result:

```
Promise in Java
foobar
```

## Chain with then(Func)

```Java
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
```

Result:

```
Promise in Java
foobar
```


## Move on to the next operation with "resolved" only

```Java
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
```

Result:

```
Hello,Promise
Process-1
```

## Handling "reject"


```Java
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
```

Result:

```
Hello,Promise
Process-1
Rejected Process-2

```

## Promise.always can receive both "reject" and "resolve"

```Java
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
```

Result:

```
Received:I send REJECT
```

##  Concurrent execution with Promise.all

```Java
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
```

Result:

```
func3 running
func2 running
func1 running
Received the final result.
The result of async operation1 is func1-result
The result of async operation2 is func2-result
The result of async operation3 is func3-result
```

## Specify your ExecutorService when doing concurrent operation with Promise.all

```Java
/**
 * Examples for "java-promise"(https://github.com/riversun/java-promise)
 * Specify your ExecutorService when doing concurrent operation with Promise.all
 */
public class Example41 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        final ExecutorService myExecutor = Executors.newFixedThreadPool(2);

        // Asynchronous operation 1
        Func function1 = (action, data) -> {
            System.out.println("No.1 " + Thread.currentThread());
            new Thread(() -> {
                Promise.sleep(1000);
                System.out.println("func1 running");
                action.resolve("func1-result");
            }).start();
        };

        // Asynchronous operation 2
        Func function2 = (action, data) -> {
            System.out.println("No.2 " + Thread.currentThread());
            new Thread(() -> {
                Promise.sleep(500);
                System.out.println("func2 running");
                action.resolve("func2-result");
            }).start();
        };

        // Asynchronous operation 3
        Func function3 = (action, data) -> {
            System.out.println("No.3 " + Thread.currentThread());
            new Thread(() -> {
                Promise.sleep(100);
                System.out.println("func3 running");
                action.resolve("func3-result");
            }).start();
        };
        // Operation to receive final result
        Func function4 = (action, data) -> {
            System.out.println("No.4 " + Thread.currentThread());
            System.out.println("Received the final result.");
            List<Object> resultList = (List<Object>) data;
            for (int i = 0; i < resultList.size(); i++) {
                Object result = resultList.get(i);
                System.out.println("The result of async operation" + (i + 1) + " is " + result);
            }

            myExecutor.shutdown();
            action.resolve();
        };

        Promise.all(myExecutor, function1, function2, function3)
                .always(function4)
                .start();// start asynchronous operations

    }
}
```

Result:

```No.1 Thread[pool-1-thread-2,5,main]
No.2 Thread[pool-1-thread-2,5,main]
No.3 Thread[pool-1-thread-2,5,main]
func3 running
func2 running
func1 running
No.4 Thread[pool-1-thread-1,5,main]
Received the final result.
The result of async operation1 is func1-result
The result of async operation2 is func2-result
The result of async operation3 is func3-result

```
