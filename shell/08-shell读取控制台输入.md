## Read读取控制台输入

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- 使用非root用户



### read读取控制台输入

#### 基本语法

read(选项)(参数)

- 选项：

  -p：指定读取值时的提示符；

  -t：指定读取值时等待的时间（秒）

- 参数

  变量：指定读取值的变量名



#### 案例实操

提示7秒内，读取控制台输入的名称

```shell
# 创建脚本
[george@hadoop130 shell]$ vim read.sh


# 内容如下
#!/bin/bash

read -t 7 -p "请在7秒内输入用户名：" Name
echo $Name

# 执行脚本
[george@hadoop130 shell]$ bash ./read.sh 
请在7秒内输入用户名：george
george
```


