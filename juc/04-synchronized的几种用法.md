## Synchronized的几种用法

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- jdk 1.8



### 概念理解

synchronized是Java中的关键字，是一种同步锁。它修饰的对象有以下几种：

- 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象；
- 修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象；
- 修改一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象；
- 修改一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象；



### 案例1-多线程访问两个同步方法

多线程访问两个同步方法，请问先打印邮件还是短信

```java
package com.george.juc;

import java.util.concurrent.TimeUnit;

public class SynchronizedDemo4 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                phone.sendEmail();
            }).start();

            new Thread(() -> {
                phone.sendSMS();
            }).start();

            TimeUnit.MICROSECONDS.sleep(100);
            System.out.println("==========================");
        }

    }
}

class Phone {
    public synchronized void sendEmail() {
        System.out.println("******send Email");
    }

    public synchronized void sendSMS() {
        System.out.println("******send SMS");
    }
}
```



#### 结果：

多次执行可见，邮件与短信方法执行的先后顺序是随机的。

#### 解析：

一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法





### 案例2-暂停4秒钟在邮件方法

暂停4秒钟在邮件方法，请问先打印邮件还是短信

```java
package com.george.juc;

import java.util.concurrent.TimeUnit;

public class SynchronizedDemo4 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendEmail();
        }).start();

        new Thread(() -> {
            phone.sendSMS();
        }).start();
    }
}

class Phone {
    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4);
            System.out.println("******send Email");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendSMS() {
        System.out.println("******send SMS");
    }
}
```



#### 结果：

若邮件方法先获得执行权，则先等待4秒至邮件方法执行完再执行短信方法。

若短信方法先获得执行去，则先执行短信方法，再等待4秒后再执行邮件方法。

#### 解析：

一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法





### 案例3-新增普通sayHello方法，请问先打印邮件还是hello



```java
package com.george.juc;

import java.util.concurrent.TimeUnit;

public class SynchronizedDemo4 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendEmail();
        }).start();

        new Thread(() -> {
            phone.sayHello();
        }).start();
    }
}

class Phone {
    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4);
            System.out.println("******send Email");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendSMS() {
        System.out.println("******send SMS");
    }

    public void sayHello() {
        System.out.println("*****sayHello");
    }
}
```



#### 结果：

先执行 `sayHello`方法，再执行 邮件方法。

#### 解析：

虽然 `sendEmail` 为 synchronized 修饰，但它锁定得只是当前得this对象。被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法。加个普通方法后发现和同步锁无关





### 案例4-两部手机，请问先打印邮件还是短信

```java
package com.george.juc;

import java.util.concurrent.TimeUnit;

public class SynchronizedDemo4 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> {
            phone.sendEmail();
        }).start();

        new Thread(() -> {
            phone2.sendSMS();
        }).start();
    }
}

class Phone {
    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4);
            System.out.println("******send Email");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendSMS() {
        System.out.println("******send SMS");
    }

    public void sayHello() {
        System.out.println("*****sayHello");
    }
}
```



#### 结果：

先执行 `sendSMS`方法，再执行 `sendEmail` 方法。

#### 解析：

换成两个对象后，不是同一把锁了，所以 `sendEmail`方法所在线程锁定的对象不能限制另一个对象。



### 案例5-两个静态同步方法，同一部手机，请问先打印邮件还是短信

```java
package com.george.juc;

import java.util.concurrent.TimeUnit;

public class SynchronizedDemo4 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendEmail();
        }).start();

        new Thread(() -> {
            phone.sendSMS();
        }).start();
    }
}

class Phone {
    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("******send Email");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void sendSMS() {
        System.out.println("******send SMS");
    }

    public void sayHello() {
        System.out.println("*****sayHello");
    }
}
```



#### 结果：

先执行 `sendEmail` 方法，再执行 `sendSMS` 方法。

#### 解析：

对于静态同步方法，锁是当前类的Class对象。`static synchronized` 为全局锁





### 案例6-两个静态同步方法，2部手机，请问先打印邮件还是短信

```java
package com.george.juc;

import java.util.concurrent.TimeUnit;

public class SynchronizedDemo4 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            phone.sendEmail();
        }).start();

        new Thread(() -> {
            phone2.sendSMS();
        }).start();
    }
}

class Phone {
    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4);
            System.out.println("******send Email");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void sendSMS() {
        System.out.println("******send SMS");
    }

    public void sayHello() {
        System.out.println("*****sayHello");
    }
}
```





#### 结果：

先执行 `sendEmail` 方法，再执行 `sendSMS` 方法。

#### 解析：

同案例5，对于静态同步方法，锁是当前类的Class对象。`static synchronized` 为全局锁





### 案例7-1个静态同步方法，1个普通同步方法,同一部手机，请问先打印邮件还是短信



```java
package com.george.juc;

import java.util.concurrent.TimeUnit;

public class SynchronizedDemo4 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendEmail();
        }).start();

        new Thread(() -> {
            phone.sendSMS();
        }).start();
    }
}

class Phone {
    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4);
            System.out.println("******send Email");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendSMS() {
        System.out.println("******send SMS");
    }

    public void sayHello() {
        System.out.println("*****sayHello");
    }
}
```



#### 结果：

先执行 `sendSMS` 方法，再执行 `sendEmail` 方法。

#### 解析：

`sendEmail`锁的是Class， 而 `sendSMS` 锁的是this，

考察 全局锁 与 对象锁的区别



### 案例8-1个静态同步方法，1个普通同步方法,2部手机，请问先打印邮件还是短信

```java
package com.george.juc;

import java.util.concurrent.TimeUnit;

public class SynchronizedDemo4 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            phone.sendEmail();
        }).start();

        new Thread(() -> {
            phone2.sendSMS();
        }).start();
    }
}

class Phone {
    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(4);
            System.out.println("******send Email");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendSMS() {
        System.out.println("******send SMS");
    }

    public void sayHello() {
        System.out.println("*****sayHello");
    }
}
```

#### 结果：

先执行 `sendSMS` 方法，再执行 `sendEmail` 方法。

#### 解析：

`sendEmail`锁的是Class， 而 `sendSMS` 锁的是this，

考察 全局锁 与 对象锁的区别





#### 总结

一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法，锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法。

加个普通方法后发现和同步锁无关，

换成两个对象后，不是同一把锁了，情况立刻变化。

都换成静态同步方法后，情况又变化。

所有的非静态同步方法用的都是同一把锁——实例对象本身，



synchronized实现同步的基础：Java中的每一个对象都可以作为锁。

具体表现为以下3种形式。

- 对于普通同步方法，锁是当前实例对象。
- 对于同步方法块，锁是Synchonized括号里配置的对象。
- 对于静态同步方法，锁是当前类的Class对象。



当一个线程试图访问同步代码块时，它首先必须得到锁，退出或抛出异常时必须释放锁。也就是说如果一个实例对象的非静态同步方法获取锁后，该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁后才能获取锁，可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁，所以毋须等待该实例对象已获取锁的非静态同步方法释放锁就可以获取他们自己的锁。



所有的静态同步方法用的也是同一把锁——类对象Class本身，这两把锁是两个不同的对象，所以静态同步方法与非静态同步方法之间是不会有竞态条件的。但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁，而不管是同一个实例对象的静态同步方法之间，还是不同的实例对象的静态同步方法之间，只要它们同一个类的实例对象！