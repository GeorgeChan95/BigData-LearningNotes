## Shell流程控制

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- 使用非root用户



### if 判断



#### 基本语法

if [ 条件判断式 ];then 

 程序 

fi 

或者 

if [ 条件判断式 ] 

 then 

  程序 

elif [ 条件判断式 ]

​	then

​		程序

else

​	程序

fi



**注意事项：**

- [ 条件判断式 ]，中括号和条件判断式之间必须有空格
- if后要有空格



#### 案例实操

输入一个数字，如果是1，则输出 this is 1，如果是2，则输出this is 2，如果是其它，什么也不输出。

```shell
# 创建脚本
[george@hadoop130 shell]$ vim test-if.sh


# 内容如下
#!/bin/bash
if [ $1 -eq 1 ]
then
        echo "this is 1"
elif [ $2 -eq 2 ]
then
        echo "this is 2"
fi


# 执行脚本
[george@hadoop130 shell]$ bash ./test-if.sh 1
this is 1
```







### case 语句



#### 基本语法

case $变量名 in 

 "值1"） 

  如果变量的值等于值1，则执行程序1 

  ;; 

 "值2"） 

  如果变量的值等于值2，则执行程序2 

  ;; 

 …省略其他分支… 

 *） 

  如果变量的值都不是以上的值，则执行此程序 

  ;; 

esac



**注意事项：**

- case行尾必须为单词“in”，每一个模式匹配必须以右括号“）”结束。
- 双分号“;;”表示命令序列结束，相当于java中的break
- 最后的“*）”表示默认模式，相当于java中的default
- 



#### 案例实操

输入一个数字，如果是1，则输出 this is 1，如果是2，则输出 this is 2，如果是其它，输出 other。

```shell
# 创建脚本
[george@hadoop130 shell]$ vim test-case.sh

# 内容如下
#!/bin/bash
case $1 in
1)
        echo this is 1
;;
2)
        echo this is 2
;;
*)
        echo other
;;
esac


# 执行脚本
[george@hadoop130 shell]$ bash ./test-case.sh 2
this is 2
[george@hadoop130 shell]$ bash ./test-case.sh 3
other
```







### for 循环



#### 基本语法

for (( 初始值;循环控制条件;变量变化 )) 

 do 

  程序 

 done



#### 案例实操

从1加到100

```shell
# 创建脚本
[george@hadoop130 shell]$ vim test-for-1.sh


# 内容如下
#!/bin/bash
s=0
for (( i=0; i<=100; i++ ))
do
        s=$[ $s + $i ]
done
echo $s


# 执行脚本
[george@hadoop130 shell]$ bash ./test-for-1.sh
5050
```



#### 基本语法2

for 变量 in 值1 值2 值3… 

 do 

  程序 

 done



#### 案例实操

- 打印所有输入参数

```shell
# 创建脚本
[george@hadoop130 shell]$ vim test-for-2.sh


# 内容如下
#!/bin/bash
for i in $*
do
        echo this is $i
done


# 执行脚本
[george@hadoop130 shell]$ bash ./test-for-2.sh 123 456 789
this is 123
this is 456
this is 789
```



- 比较$*和$@区别

  - $*和$@都表示传递给函数或脚本的所有参数，不被双引号“”包含时，都以$1 $2 …$n的形式输出所有参数。

  ```shell
  # 创建脚本
  [george@hadoop130 shell]$ vim test-for-2.sh
  
  
  # 内容如下
  #!/bin/bash
  for i in $*
  do
          echo this is $i
  done
  
  
  for j in $@
  do
          echo this is $j
  done
  
  
  # 执行脚本
  [george@hadoop130 shell]$ bash ./test-for-3.sh 123 456 789
  this is 123
  this is 456
  this is 789
  this is 123
  this is 456
  this is 789
  ```

  - 当它们被双引号“”包含时，“$*”会将所有的参数作为一个整体，以“$1 $2 …$n”的形式输出所有参数；“$@”会将各个参数分开，以“$1” “$2”…”$n”的形式输出所有参数。

  ```shell
  # 创建脚本
  [george@hadoop130 shell]$ vim test-for-4.sh
  
  
  # 内容如下
  #!/bin/bash
  for i in "$*"
  do
          echo this is $i
  done
  
  
  for j in "$@"
  do
          echo this is $j
  done
  
  
  # 执行脚本
  [george@hadoop130 shell]$ bash ./test-for-4.sh 123 456 789
  this is 123 456 789
  this is 123
  this is 456
  this is 789
  ```





### while 循环



#### 基本语法

while [ 条件判断式 ] 

 do 

  程序

 done





#### 案例实操

从1加到100

```shell
# 创建脚本
[george@hadoop130 shell]$ vim test-while.sh


# 内容如下
#!/bin/bash
s=0
i=1
while [ $i -le 100 ]
do
        s=$[$s+$i]
        i=$[$i+1]
done

echo $s



#执行脚本
[george@hadoop130 shell]$ bash ./test-while.sh 
5050
```

