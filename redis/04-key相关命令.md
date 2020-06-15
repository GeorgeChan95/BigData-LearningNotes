## NoSql概述

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- redis 3.2.5



### keys  *

查询当前库的所有键

```shell
127.0.0.1:6379> keys *
1) "test"
```



### exists  key

判断某个键是否存在

```shell
127.0.0.1:6379> exists test
(integer) 1
```

​    

### type  key

查看键的类型

```shell
127.0.0.1:6379> type test
string
```



### del  key

删除某个键

```shell
127.0.0.1:6379> del test
(integer) 1
```



### expire   key   seconds

为键值设置过期时间，单位秒。

```shell
127.0.0.1:6379> expire k1 10
(integer) 1
```



### ttl   key

查看还有多少秒过期，-1表示永不过期，-2表示已过期

```shell
127.0.0.1:6379> ttl k1
(integer) 7
```



### dbsize

查看当前数据库的key的数量

```shell
127.0.0.1:6379> dbsize
(integer) 1
```



### flushdb

清空当前库

```shell
127.0.0.1:6379> flushall
OK
```



### flushall

通杀全部库 

```shell
127.0.0.1:6379> flushall
OK
```



