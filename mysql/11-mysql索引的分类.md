## Mysql索引的分类

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- mysql 5.5
- navicat 15.9



### 单值索引

即一个索引只包含单个列，一个表可以有多个单列索引



语法：

```shell
# 随表一起建索引：
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id),
  KEY (customer_name)
);
  
# 单独建单值索引：
CREATE  INDEX idx_customer_name ON customer(customer_name); 
 
# 删除索引：
DROP INDEX idx_customer_name  on customer;
```



### 唯一索引

索引列的值必须唯一，但允许有空值。

语法：

```shell
# 随表一起建索引：
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id),
  KEY (customer_name),
  UNIQUE (customer_no)
);
  
# 单独建唯一索引：
CREATE UNIQUE INDEX idx_customer_no ON customer(customer_no); 
 
# 删除索引：
DROP INDEX idx_customer_no on customer;
```



### 主键索引

设定为主键后数据库会自动建立索引，innodb为聚簇索引

```shell
# 随表一起建索引：
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id) 
);
   
CREATE TABLE customer2 (id INT(10) UNSIGNED   ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id) 
);
 
# 单独建主键索引：
ALTER TABLE customer 
 add PRIMARY KEY customer(customer_no);  
 
# 删除建主键索引：
ALTER TABLE customer 
 drop PRIMARY KEY ;  
 
# 修改建主键索引：
必须先删除掉(drop)原索引，再新建(add)索引
```





### 复合索引

即一个索引包含多个列。

语法：

```shell
# 随表一起建索引：
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id),
  KEY (customer_name),
  UNIQUE (customer_name),
  KEY (customer_no,customer_name)
);
 
# 单独建索引：
CREATE  INDEX idx_no_name ON customer(customer_no,customer_name); 
 
# 删除索引：
DROP INDEX idx_no_name  on customer;
```



### 基本语法



#### 创建

CREATE  [UNIQUE ]  INDEX [indexName] ON table_name(column)) 



#### 删除

DROP INDEX [indexName] ON mytable; 



#### 查看

SHOW INDEX FROM table_name\G;



#### 使用ALTER命令

有四种方式来添加数据表的索引：
ALTER TABLE tbl_name ADD PRIMARY KEY (column_list): 该语句添加一个主键，这意味着索引值必须是唯一的，且不能为NULL。

ALTER TABLE tbl_name ADD UNIQUE index_name (column_list): 这条语句创建索引的值必须是唯一的（除了NULL外，NULL可能会出现多次）。

ALTER TABLE tbl_name ADD INDEX index_name (column_list): 添加普通索引，索引值可出现多次。

ALTER TABLE tbl_name ADD FULLTEXT index_name (column_list):该语句指定了索引为 FULLTEXT ，用于全文索引。





### 哪些情况需要创建索引

- 主键自动建立唯一索引
- 频繁作为查询条件的字段应该创建索引
- 查询中与其它表关联的字段，外键关系建立索引
- 单键/组合索引的选择问题， 组合索引性价比更高
- 查询中排序的字段，排序字段若通过索引去访问将大大提高排序速度
- 查询中统计或者分组字段





### 哪些情况不要创建索引

- 表记录太少

- 经常增删改的表或者字段

  Why:提高了查询速度，同时却会降低更新表的速度，如对表进行INSERT、UPDATE和DELETE。
  因为更新表时，MySQL不仅要保存数据，还要保存一下索引文件

- Where条件里用不到的字段不创建索引

- 过滤性不好的不适合建索引