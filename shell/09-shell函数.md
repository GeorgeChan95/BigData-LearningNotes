## Shell函数

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5



### 系统函数

#### basename基本语法

```shell
basename [string / pathname] [suffix]
```

**功能描述：**basename命令会删掉所有的前缀包括最后一个（‘/’）字符，然后将字符串显示出来。

**选项：**suffix为后缀，如果suffix被指定了，basename会将pathname或string中的suffix去掉。



#### 案例实操

- 截取该 `/usr/local/src/george.txt` 路径的文件名称

  ```shell
  [root@localhost src]# basename /usr/local/src/george.txt 
  george.txt
  [root@localhost src]# basename /usr/local/src/george.txt .txt
  george
  ```



#### dirname基本语法

```shell
dirname 文件绝对路径	
```

**功能描述：**从给定的包含绝对路径的文件名中去除文件名（非目录的部分），然后返回剩下的路径（目录的部分）



#### 案例实操

- 获取`george.txt`文件的路径

  ```shell
  [root@localhost src]# dirname /usr/local/src/george.txt 
  /usr/local/src
  ```





### 自定义函数

#### 基本语法

[ function ] funname[()]

{

​	Action;

​	[return int;]

}

funname



#### 经验技巧

- 必须在调用函数地方之前，先声明函数，shell脚本是逐行运行。不会像其它语言一样先编译。
- 函数返回值，只能通过`$?`系统变量获得，可以显示加：return返回，如果不加，将以最后一条命令运行结果，作为返回值。return后跟数值n(0-255)



#### 案例实操

- 计算两个输入参数的和

```shell
#!/bin/bash
function sum()
{
    s=0
    s=$[ $1 + $2 ]
    echo "$s"
}

read -p "Please input the number1: " n1;
read -p "Please input the number2: " n2;
sum $n1 $n2;

# 提权
[root@localhost src]# chmod +x function.sh

# 执行
[root@localhost src]# bash ./function.sh 
Please input the number1: 3
Please input the number2: 5
8
```

