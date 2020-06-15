## Set类型命令

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- redis 3.2.5



### Set特性

Redis set对外提供的功能与list类似是一个列表的功能，特殊之处在于set是可以自动排重的，当你需要存储一个列表数据，又不希望出现重复数据时，set是一个很好的选择，并且set提供了判断某个成员是否在一个set集合内的重要接口，这个也是list所不能提供的。



Redis的Set是string类型的无序集合。它底层其实是一个value为null的hash表,所以添加，删除，查找的复杂度都是O(1)。

 

### Set类型命令



#### sadd <key>  <value1>  <value2> .....   

将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。

```shell
127.0.0.1:6379> sadd s1 v1 v2 v3 v4 v5
(integer) 5
```



#### smembers <key>

取出该集合的所有值。

```shell
127.0.0.1:6379> smembers s1
1) "v4"
2) "v3"
3) "v1"
4) "v2"
5) "v5"
```



#### sismember <key>  <value>

判断集合<key>是否为含有该<value>值，有返回1，没有返回0

```shell
127.0.0.1:6379> sismember s1 v1
(integer) 1
127.0.0.1:6379> sismember s1 v9
(integer) 0
```



#### scard   <key>

返回该集合的元素个数。

```shell
127.0.0.1:6379> scard s1
(integer) 5
```



#### srem <key> <value1> <value2> ....

删除集合中的某个元素。

```shell
127.0.0.1:6379> srem s1 v4 v5
(integer) 2
```



#### spop <key>  

随机从该集合中吐出一个值。

```shell
127.0.0.1:6379> spop s1
"v2"
```



#### srandmember <key> <n>

随机从该集合中取出n个值。
不会从集合中删除

```shell
127.0.0.1:6379> srandmember s1 2
1) "v3"
2) "v1"
```



#### sinter <key1> <key2>  

返回两个集合的交集元素。

```shell
127.0.0.1:6379> sadd s2 v1 v2 v3 v4 v5
(integer) 5
127.0.0.1:6379> sinter s1 s2
1) "v3"
2) "v1"
```



#### sunion <key1> <key2>  

返回两个集合的并集元素。

```shell
127.0.0.1:6379> sunion s1 s2
1) "v1"
2) "v2"
3) "v3"
4) "v4"
5) "v5"
```



#### sdiff <key1> <key2>  

返回两个集合的差集元素。

```shell
127.0.0.1:6379> sdiff s1 s2
(empty list or set)
127.0.0.1:6379> sadd s1 v8
(integer) 1
127.0.0.1:6379> sdiff s1 s2
1) "v8"
```

