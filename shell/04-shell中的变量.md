## Shell中的变量

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- 非root用户



### 系统变量



#### 常用系统变量

- $HOME

  获取当前用户的家目录

- $PWD

  获取当前所在路径和描述

- $SHELL

  获取系统默认的shell解释器

- $USER

  获取当前shell用户



#### 案例实操

##### 查看系统变量的值

```shell
[george@hadoop130 ~]$ echo $HOME
/home/george
```



##### 显示当前Shell中所有变量：set

```shell
[george@hadoop130 shell]$ set
BASH=/bin/bash
BASHOPTS=checkwinsize:cmdhist:expand_aliases:extquote:force_fignore:histappend:hostcomplete:interactive_comments:login_shell:progcomp:promptvars:sourcepath
BASH_ALIASES=()
BASH_ARGC=()
BASH_ARGV=()
BASH_CMDS=()
BASH_LINENO=()
BASH_SOURCE=()
BASH_VERSINFO=([0]="4" [1]="2" [2]="46" [3]="2" [4]="release" [5]="x86_64-redhat-linux-gnu")
BASH_VERSION='4.2.46(2)-release'
CLASSPATH=.:/usr/local/src/jdk1.8.0_251/lib:/usr/local/src/jdk1.8.0_251/jre/lib
COLUMNS=172
DIRSTACK=()
EUID=1000
....
```





### 自定义变量



#### 基本语法

- 定义变量：变量=值 
- 撤销变量：unset 变量
- 声明静态变量：readonly变量，注意：不能unset



#### 变量定义规则

- 变量名称可以由字母、数字和下划线组成，但是不能以数字开头，**环境变量名建议大写。**
- **等号两侧不能有空格**
- 在bash中，变量默认类型都是字符串类型，无法直接进行数值运算。
- 变量的值如果有空格，需要使用双引号或单引号括起来。



#### 案例实操

- 定义变量A

  ```shell
  [george@hadoop130 shell]$ A=5
  [george@hadoop130 shell]$ echo $A
  5
  ```

- 给变量A重新赋值

  ```shell
  [george@hadoop130 shell]$ A=8
  [george@hadoop130 shell]$ echo $A
  8
  ```

- 撤销变量A

  ```shell
  [george@hadoop130 shell]$ unset A
  [george@hadoop130 shell]$ echo $A
  
  ```

- 声明静态的变量B=2，不能unset

  ```shell
  [george@hadoop130 shell]$ readonly B=2
  [george@hadoop130 shell]$ echo $B
  2
  [george@hadoop130 shell]$ B=4
  -bash: B: readonly variable
  [george@hadoop130 shell]$ unset B
  -bash: unset: B: cannot unset: readonly variable
  ```

- 在bash中，变量默认类型都是字符串类型，无法直接进行数值运算

  ```shell
  [george@hadoop130 shell]$ C=1+2
  [george@hadoop130 shell]$ echo $C
  1+2
  ```

- 变量的值如果有空格，需要使用双引号或单引号括起来

  ```shell
  [george@hadoop130 shell]$ D=i uuu mmm
  -bash: uuu: command not found
  [george@hadoop130 shell]$ D="i uuu mmm"
  [george@hadoop130 shell]$ echo $D
  i uuu mmm
  ```

- 可把变量提升为全局环境变量，可供其他Shell程序使用

  **export 变量名**

  ```shell
  # 查看当前shell变量的值
  [george@hadoop130 shell]$ echo $B
  2
  # 编辑shell文件
  [george@hadoop130 shell]$ vim shell-export.sh
  
  
  # 内容如下
  #!/bin/bash
  echo "test export"
  echo $B
  
  # 执行shell，发现并没有打印 $B 的值
  [george@hadoop130 shell]$ bash ./shell-export.sh 
  test export
  
  
  #执行 export B，将$B变为全局变量
  [george@hadoop130 shell]$ export B
  [george@hadoop130 shell]$ bash ./shell-export.sh 
  test export
  2
  ```





### 特殊变量：$n



#### 基本语法

$n	（功能描述：n为数字，$0代表该脚本名称，$1-$9代表第一到第九个参数，十以上的参数，十以上的参数需要用大括号包含，如${10}）



#### 案例实操

输出该脚本文件名称、输入参数1和输入参数2 的值

```shell
# 创建shell脚本文件
[george@hadoop130 shell]$ vim test-n.sh

# 内容如下
#!/bin/bash
echo "$0  $1 $2"
echo $0 $1 $2

# 执行脚本
[george@hadoop130 shell]$ bash ./test-n.sh 111 222
./test-n.sh  111 222
./test-n.sh 111 222
```





### 特殊变量：$#



#### 基本语法

$#	（功能描述：获取所有输入参数个数，常用于循环）。



#### 案例实操

获取输入参数的个数

```shell
# 创建shell脚本
[george@hadoop130 shell]$ vim test-#.sh

# 内容如下
#!/bin/bash
echo "$0 $1 $2"
echo $#

# 执行脚本
[george@hadoop130 shell]$ bash ./test-#.sh 333 444
./test-#.sh 333 444
2
```





### 特殊变量：$*、$@



#### 基本语法

$*	（功能描述：这个变量代表命令行中所有的参数，$*把所有的参数看成一个整体）

$@	（功能描述：这个变量也代表命令行中所有的参数，不过$@把每个参数区分对待）



#### 案例实操

打印输入的所有参数

```shell
# 创建shell脚本
[george@hadoop130 shell]$ vim test-@.sh

# 内容如下
echo "$0 $1 $2"
echo $#
echo $*
echo $@

# 执行脚本
[george@hadoop130 shell]$ bash ./test-@.sh 555 666
./test-@.sh 555 666
2
555 666
555 666
```





### 特殊变量：$？



#### 基本语法

$？	（功能描述：最后一次执行的命令的返回状态。如果这个变量的值为0，证明上一个命令正确执行；如果这个变量的值为非0（具体是哪个数，由命令自己来决定），则证明上一个命令执行不正确了。）



#### 案例实操

判断helloworld.sh脚本是否正确执行

```shell
[george@hadoop130 shell]$ ./helloworld.sh 
hello world
[george@hadoop130 shell]$ echo $?
0
```


