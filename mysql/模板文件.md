## Mysql的视图（view）

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- mysql 5.5
- navicat 15.9



### 什么是视图

将一段查询sql封装为一个虚拟的表。 

这个虚拟表只保存了sql逻辑，不会保存任何查询结果。



### 视图的作用

- 封装复杂sql语句，提高复用性
- 逻辑放在数据库上面，更新不需要发布程序，面对频繁的需求变更更灵活



### 适用场景

- 很多地方可以共用的一组查询结果
- 报表



### 创建视图

```sql
CREATE VIEW view_name 
AS
SELECT column_name(s)
FROM table_name
WHERE condition;
```



### 使用视图

**查询**

```sql
select * from view_name;
```



### 更新视图

```shell
CREATE OR REPLACE VIEW view_name 
AS
SELECT column_name(s)
FROM table_name
WHERE condition
```



### 注意事项

mysql的视图中不允许有from 后面的子查询，但oracle可以
