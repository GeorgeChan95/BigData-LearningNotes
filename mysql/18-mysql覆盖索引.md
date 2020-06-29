## Mysql的覆盖索引

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- mysql 5.5
- navicat 15.9



### 什么是覆盖索引？

简单说就是，select 到 from 之间查询的列 <=使用的索引列+主键

![image-20200629233539972](images/image-20200629233539972.png)



```sql
explain select * from emp where name like '%abc';
```

![image-20200629233623893](images/image-20200629233623893.png)

![image-20200629233648678](images/image-20200629233648678.png)



使用覆盖索引后

![image-20200629233659802](images/image-20200629233659802.png)

