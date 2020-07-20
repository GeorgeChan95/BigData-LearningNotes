## Shell条件判断

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- 使用非root用户



### 条件判断



#### 基本语法

[ condition ]（注意condition前后要有空格）

**注意：条件非空即为true，[ test ]返回true，[] 返回false。**



#### 常用条件判断

- 两个整数之间比较
  - = 字符串比较
  - -lt 小于（less than）
  - -le 小于等于（less equal）
  - -eq 等于（equal）
  - -gt 大于（greater than）
  - -ge 大于等于（greater equal）
  - -ne 不等于（Not equal）
- 按照文件权限进行判断
  - -r 有读的权限（read）
  - -w 有写的权限（write）
  - -x 有执行的权限（execute）
- 按照文件类型进行判断
  - -f 文件存在并且是一个常规的文件（file）
  - -e 文件存在（existence）
  - -d 文件存在并是一个目录（directory）



#### 案例实操

- 23是否大于等于22

  ```shell
  [george@hadoop130 shell]$ [ 23 -ge 22 ]
  [george@hadoop130 shell]$ echo $?
  0
  ```

- helloworld.sh是否具有写权限

  ```shell
  [george@hadoop130 shell]$ [ -w helloworld.sh ]
  [george@hadoop130 shell]$ echo $?
  0
  ```

- /home/george/cls.txt目录中的文件是否存在

  ```shell
  [george@hadoop130 shell]$ [ -e /home/george/cls.txt ]
  [george@hadoop130 shell]$ echo $?
  1
  ```

- 多条件判断（&& 表示前一条命令执行成功时，才执行后一条命令，|| 表示上一条命令执行失败后，才执行下一条命令）

  ```shell
  [george@hadoop130 shell]$ [ 3 -gt 2 ] && echo yes || echo no
  yes
  ```