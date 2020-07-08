## Lambda函数编程

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- jdk 1.8



### Lambda了解



#### 结构组成

- 小括号
- 右箭头
- 大括号

```java
() -> {}
```



#### @FunctionalInterface

该注解用于标识该接口为 `函数式接口`

**函数式接口特点：**

- 接口有且仅有一个抽象方法
- 允许定义静态方法（static）
- 允许定义默认方法（default）
- 允许java.lang.Object中的public方法
- 该注解不是必须的，如果一个接口符合"函数式接口"定义，那么加不加该注解都没有影响。加上该注解能够更好地让编译器进行检查。如果编写的不是函数式接口，但是加上了@FunctionInterface，那么编译器会报错



### 案例代码

`codes` --> `jvm-juc` --> `juc-case\LambdaExpressDemo2.java`

```java
package com.george.juc;

/**
 * <p>
 *     Lambda函数式编程
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/5 13:09
 * @since JDK 1.8
 */
public class LambdaExpressDemo2 {
    public static void main(String[] args) {
        // 使用匿名内部类实现
        /*Foo foo = new Foo() {
            @Override
            public void sayHello() {
                System.out.println("hello Lambda");
            }
        };
        foo.sayHello();*/

        // 使用 Lambda表达式实现
        Foo foo = (int x, int y) -> {
            System.out.println("this is add method");
            return x + y;
        };
        System.out.println("add method 结果：" + foo.add(5, 6));
        System.out.println("mul method 结果：" + foo.mul(4,5));
        System.out.println("div method 结果：" + Foo.div(6, 3));
    }
}

@FunctionalInterface
interface Foo {
//    public void sayHello();

    // 接口有且仅有一个抽象方法
    public int add(int x, int y);

    // 允许定义默认方法（default）
    default int mul(int x,int y) {
        return x * y;
    }

    // 允许定义静态方法（static）
    public static int div(int x, int y) {
        return x/y;
    }
}
```


