## Zset类型相关命令

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- redis 3.2.5



### zset类型特性

Redis有序集合zset与普通集合set非常相似，是一个没有重复元素的字符串集合。不同之处是有序集合的所有成员都关联了一个评分（score） ，这个评分（score）被用来按照从最低分到最高分的方式排序集合中的成员。集合的成员是唯一的，但是评分可以是重复了 。
       因为元素是有序的, 所以你也可以很快的根据评分（score）或者次序（position）来获取一个范围的元素。访问有序集合的中间元素也是非常快的,因此你能够使用有序集合作为一个没有重复成员的智能列表。



### zset 命令



#### zadd  <key> <score1> <value1>  <score2> <value2>...

将一个或多个 member 元素及其 score 值加入到有序集 key 当中。

```shell
127.0.0.1:6379> zadd z1 60 v1 70 v2 80 v3 90 v4
(integer) 4
```



#### zrange <key>  <start> <stop>  [WITHSCORES]   

返回有序集 key 中，下标在<start> <stop>之间的元素
带WITHSCORES，可以让分数一起和值返回到结果集。

```shell
127.0.0.1:6379> zrange z1 0 3
1) "v1"
2) "v2"
3) "v3"
4) "v4"
127.0.0.1:6379> zrange z1 0 3 withscores
1) "v1"
2) "60"
3) "v2"
4) "70"
5) "v3"
6) "80"
7) "v4"
8) "90"
```



#### zrangebyscore key min max [withscores] [limit offset count]

返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。 

```shell
127.0.0.1:6379> zrangebyscore z1 70 90
1) "v2"
2) "v3"
3) "v4"
127.0.0.1:6379> zrangebyscore z1 70 90 WITHSCORES
1) "v2"
2) "70"
3) "v3"
4) "80"
5) "v4"
6) "90"
```



#### zrevrangebyscore key max min [withscores] [limit offset count]

同上，改为从大到小排列。 

```shell
127.0.0.1:6379> zrevrangebyscore z1 90 60 withscores
1) "v4"
2) "90"
3) "v3"
4) "80"
5) "v2"
6) "70"
7) "v1"
8) "60"
```



#### zincrby <key> <increment> <value>

为元素的score加上增量

```shell
127.0.0.1:6379> zincrby z1 10 v1
"70"
127.0.0.1:6379> zrange z1 0 1000 withscores
1) "v1"
2) "70"
3) "v2"
4) "70"
5) "v3"
6) "80"
7) "v4"
8) "90"
```



#### zrem  <key>  <value>  

删除该集合下，指定值的元素 

```shell
127.0.0.1:6379> zrem z1 v1
(integer) 1
```



#### zcount <key>  <min>  <max> 

统计该集合，分数区间内的元素个数 

```shell
127.0.0.1:6379> zcount z1 60 90
(integer) 3
```



#### zrank <key>  <value> 

返回该值在集合中的排名，从0开始。

```shell
127.0.0.1:6379> zrank z1 v4
(integer) 2
```


