## Shell运算符

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- 使用非root用户



### shell运算符



#### 基本语法

- “$((运算式))”或“$[运算式]”
- expr  + , - , \*,  /,  %   加，减，乘，除，取余

**注意：expr运算符间要有空格**



#### 案例实操

- 计算3+2的值

  ```shell
  [george@hadoop130 shell]$ echo $[2+3]
  5
  [george@hadoop130 shell]$ echo $[ 2 + 3 ]
  5
  [george@hadoop130 shell]$ expr 2 + 3
  5
  [george@hadoop130 shell]$ expr 2+3
  2+3
  ```

  

- 计算3-2的值

  ```shell
  [george@hadoop130 shell]$ echo $[3-2]
  1
  [george@hadoop130 shell]$ expr 3 - 2
  1
  ```

  

- 计算（2+3）X4的值

  ```shell
  [george@hadoop130 shell]$ echo $[(2+3)*4]
  20
  [george@hadoop130 shell]$ echo $[$[2+3]*4]
  20
  [george@hadoop130 shell]$ echo $[ $[ 2 + 3 ] * 4 ]
  20
  [george@hadoop130 shell]$ expr `expr 3 + 2` \* 4
  20
  [george@hadoop130 shell]$ expr `expr 3 + 2 \* 4`
  11
  ```