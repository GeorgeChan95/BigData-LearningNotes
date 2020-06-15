## String类型命令

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- redis 3.2.5



### 概念理解

- String是Redis最基本的类型，你可以理解成与Memcached一模一样的类型，一个key对应一个value。
- String类型是二进制安全的。意味着Redis的string可以包含任何数据。比如jpg图片或者序列化的对象 。
- String类型是Redis最基本的数据类型，一个Redis中字符串value最多可以是512M



### 什么是原子性

所谓原子操作是指不会被线程调度机制打断的操作；这种操作一旦开始，就一直运行到结束，中间不会有任何 context switch （切换到另一个线程）。



（1） 在单线程中， 能够在单条指令中完成的操作都可以认为是" 原子操作"，因为中断只能发生于指令之间。
（2）在多线程中，不能被其它进程（线程）打断的操作就叫原子操作。



Redis单命令的原子性主要得益于Redis的单线程



### 相关命令



#### get   <key>

查询对应键值

```shell
127.0.0.1:6379> get k1
"v1"
```



#### set   <key>  <value>

添加键值对

```shell
127.0.0.1:6379> set k1 v1
OK
```



#### append  <key>  <value>

将给定的<value> 追加到原值的末尾

```shell
127.0.0.1:6379> append k1 test
(integer) 6
127.0.0.1:6379> get k1
"v1test"
```



#### strlen  <key>

获得值的长度

```shell
127.0.0.1:6379> strlen k1
(integer) 6
```



#### setnx  <key>  <value>

只有在 key 不存在时设置 key 的值

```shell
127.0.0.1:6379> setnx k1 v1
(integer) 0
127.0.0.1:6379> setnx k2 v2
(integer) 1
```



#### incr  <key>

将 key 中储存的数字值增1

只能对数字值操作，如果为空，新增值为1

```shell
127.0.0.1:6379> incr k3
(integer) 1
```



#### decr  <key>

将 key 中储存的数字值减1

只能对数字值操作，如果为空，新增值为-1

```shell
127.0.0.1:6379> decr k3
(integer) 0
127.0.0.1:6379> decr k4
(integer) -1
```



#### incrby / decrby  <key>  <步长>

将 key 中储存的数字值增减。自定义步长。

```shell
127.0.0.1:6379> incrby k3 9
(integer) 9
127.0.0.1:6379> get k3
"9"
```



#### mset  <key1>  <value1>  <key2>  <value2>   ..... 

同时设置一个或多个 key-value对  

```shell
127.0.0.1:6379> mset k5 v5 k6 v6
OK
127.0.0.1:6379> get k5
"v5"
127.0.0.1:6379> get k6
"v6"-
```



#### mget  <key1>   <key2>   <key3> ..... 

同时获取一个或多个 value  

```shell
127.0.0.1:6379> mget k1 k2 k3
1) "v1test"
2) "v2"
3) "9"
```



#### msetnx <key1>  <value1>  <key2>  <value2>  ..... 

同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。

```shell
127.0.0.1:6379> msetnx k7 v7 k8 v8
(integer) 1
```



#### getrange  <key>  <起始位置>  <结束位置>

获得值的范围，类似java中的substring

```shell
127.0.0.1:6379> getrange k1 0 1
"v1"
127.0.0.1:6379> get k1
"v1test"
```



#### setrange  <key>   <起始位置>   <value>

用 <value>  覆写<key> 所储存的字符串值，从<起始位置>开始。

```shell
127.0.0.1:6379> get k1
"v1test"
127.0.0.1:6379> setrange k1 3 uuu
(integer) 6
127.0.0.1:6379> get k1
"v1tuuu"
```



#### setex  <key>  <过期时间>   <value>

设置键值的同时，设置过期时间，单位秒。

```shell
127.0.0.1:6379> setex k9 10 v9
OK
127.0.0.1:6379> ttl k9
(integer) 5
```



#### getset <key>  <value>

以新换旧，设置了新值同时获得旧值。

```shell
127.0.0.1:6379> getset k1 newV1
"v1tuuu"
```

