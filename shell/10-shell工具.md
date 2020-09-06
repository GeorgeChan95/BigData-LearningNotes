## Shell工具（重点）

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5



### cut

#### 用途

cut的工作就是“剪”，具体的说就是在文件中负责剪切数据用的。cut 命令从文件的每一行剪切字节、字符和字段并将这些字节、字符和字段输出。



#### 基本用法

cut [选项参数]  filename

**说明：默认分隔符是制表符**



#### 选项参数说明

| 选项参数 | 功能                         |
| -------- | ---------------------------- |
| -f       | 列号，提取第几列             |
| -d       | 分隔符，按照指定分隔符分割列 |
| -c       | 指定具体的字符               |



#### 案例实操

- 数据准备

```shell
[root@localhost src]# vim cut.txt
dong shen
guan zhen
wo  wo
lai  lai
le  le
```

- 切割cut.txt第一列(每一行，按空格进行分割，取第一列)

```shell
[root@localhost src]# cut -d " " -f 1 cut.txt
dong
guan
wo
lai
le
```

- 切割cut.txt第二、三列(每一行，按空格进行分割，取第2,第3列，空格也算)

```shell
[root@localhost src]# cut -d " " -f 2,3 cut.txt
shen
zhen
 wo
 lai
 le
```

- 在cut.txt文件中切割出guan

```shell
[root@localhost src]# cat cut.txt | grep "guan" | cut -d " " -f 1
guan
```

- 选取系统PATH变量值，第2个“：”开始后的所有路径：

```shell
[root@localhost src]# echo $PATH
/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin
[root@localhost src]# echo $PATH | cut -d : -f 2-
/usr/local/bin:/usr/sbin:/usr/bin:/root/bin
```

- 切割ifconfig 后打印的IP地址

```shell
[root@localhost src]# ifconfig ens33 | grep "netmask" | cut -d " " -f 10
192.168.137.130
```



### sed

#### 用途

sed是一种流编辑器，它一次处理一行内容。处理时，把当前处理的行存储在临时缓冲区中，称为“模式空间”，接着用sed命令处理缓冲区中的内容，处理完成后，把缓冲区的内容送往屏幕。接着处理下一行，这样不断重复，直到文件末尾。文件内容并没有改变，除非你使用重定向存储输出。

#### 基本用法

sed [选项参数]  ‘command’  filename



#### 选项参数说明

| 选项参数 | 功能                                  |
| -------- | ------------------------------------- |
| -e       | 直接在指令列模式上进行sed的动作编辑。 |
| -i       | 直接编辑文件                          |



#### 命令功能描述

| 命令 | 功能描述                              |
| ---- | ------------------------------------- |
| a    | 新增，a的后面可以接字串，在下一行出现 |
| d    | 删除                                  |
| s    | 查找并替换                            |



#### 案例实操

- 数据准备

```shell
[root@localhost src]# vim sed.txt
dong shen
guan zhen
wo  wo
lai  lai

le  le
```

- 将“mei nv”这个单词插入到sed.txt第二行下，打印。

```shell
[root@localhost src]# sed '2a mei nv' sed.txt 
dong shen
guan zhen
mei nv
wo  wo
lai  lai

le  le

# 查看源文件
[root@localhost src]# cat sed.txt 
dong shen
guan zhen
wo  wo
lai  lai

le  le
# 源文件未发生改变
```

- 删除sed.txt文件所有包含wo的行

```shell
[root@localhost src]# sed '/wo/d' sed.txt 
dong shen
guan zhen
lai  lai

le  le
```

- 将sed.txt文件中wo替换为ni

```shell
[root@localhost src]# sed 's/wo/ni/g' sed.txt 
dong shen
guan zhen
ni  ni
lai  lai

le  le
# ‘g’表示global，全部替换
```

- 将sed.txt文件中的第二行删除并将wo替换为ni

```shell
[root@localhost src]# sed -e '2d' -e 's/wo/ni/g' sed.txt 
dong shen
ni  ni
lai  lai

le  le
[root@localhost src]# cat sed.txt 
dong shen
guan zhen
wo  wo
lai  lai

le  le
# 源文件依然没有变化
```



### awk

#### 用途

一个强大的文本分析工具，把文件逐行的读入，以空格为默认分隔符将每行切片，切开的部分再进行分析处理。

#### 基本用法

awk [选项参数] ‘pattern1{action1} pattern2{action2}...’ filename

pattern：表示AWK在数据中查找的内容，就是匹配模式

action：在找到匹配内容时所执行的一系列命令



#### 选项参数说明

| 选项参数 | 功能                 |
| -------- | -------------------- |
| -F       | 指定输入文件分隔符   |
| -v       | 赋值一个用户定义变量 |



#### 案例实操

- 数据准备

```shell
[root@localhost src]# sudo cp /etc/passwd ./
# 内容如下
[root@localhost src]# cat passwd
root:x:0:0:root:/root:/bin/bash
bin:x:1:1:bin:/bin:/sbin/nologin
daemon:x:2:2:daemon:/sbin:/sbin/nologin
adm:x:3:4:adm:/var/adm:/sbin/nologin
lp:x:4:7:lp:/var/spool/lpd:/sbin/nologin
sync:x:5:0:sync:/sbin:/bin/sync
shutdown:x:6:0:shutdown:/sbin:/sbin/shutdown
halt:x:7:0:halt:/sbin:/sbin/halt
mail:x:8:12:mail:/var/spool/mail:/sbin/nologin
operator:x:11:0:operator:/root:/sbin/nologin
games:x:12:100:games:/usr/games:/sbin/nologin
ftp:x:14:50:FTP User:/var/ftp:/sbin/nologin
nobody:x:99:99:Nobody:/:/sbin/nologin
systemd-network:x:192:192:systemd Network Management:/:/sbin/nologin
dbus:x:81:81:System message bus:/:/sbin/nologin
polkitd:x:999:998:User for polkitd:/:/sbin/nologin
sshd:x:74:74:Privilege-separated SSH:/var/empty/sshd:/sbin/nologin
postfix:x:89:89::/var/spool/postfix:/sbin/nologin
```

- 搜索passwd文件以root关键字开头的所有行，并输出该行的第7列。

```shell
[root@localhost src]# awk -F: '/^root/{print $7}' passwd
```

- 搜索passwd文件以root关键字开头的所有行，并输出该行的第1列和第7列，中间以“，”号分割。

```shell
[root@localhost src]# awk -F: '/^root/{print $1","$7}' passwd 
root,/bin/bash
```

**注意：只有匹配了pattern的行才会执行action**

- 只显示/etc/passwd的第一列和第七列，以逗号分割，且在所有行前面添加列名user，shell在最后一行添加"dahaige，/bin/zuishuai"。

```shell
 awk -F : 'BEGIN{print "user, shell"} {print $1","$7} END{print "dahaige,/bin/zuishuai"}' passwd
```

**注意：BEGIN 在所有数据读取行之前执行；END 在所有数据执行之后执行。**

- 将passwd文件中的用户id增加数值1并输出

```shell
[root@localhost src]# awk -v i=1 -F: '{print $3+i}' passwd
1
2
3
4
......
```



#### awk的内置变量

| 变量     | 说明                                   |
| -------- | -------------------------------------- |
| FILENAME | 文件名                                 |
| NR       | 已读的记录数                           |
| NF       | 浏览记录的域的个数（切割后，列的个数） |

##### 案例实操

- 统计passwd文件名，每行的行号，每行的列数

```
[root@localhost src]# awk -F : '{print "filename:" FILENAME ", linenumber:" NR ",colums:"NF }' passwd
filename:passwd, linenumber:1,colums:7
filename:passwd, linenumber:2,colums:7
filename:passwd, linenumber:3,colums:7
filename:passwd, linenumber:4,colums:7
......
```

- 切割IP

```shell
[root@localhost src]# ifconfig ens33 | grep netmask | awk -F " " '{print $2}'
192.168.137.130
```

- 查询sed.txt中空行所在的行号

```
[root@localhost src]# awk '/^$/{print NR}' sed.txt 
3
```



### sort

#### 用途

sort命令是在Linux里非常有用，它将文件进行排序，并将排序结果标准输出。

#### 基本用法

sort(选项)(参数)

#### 选项参数说明

| 选项参数 | 功能                     |
| -------- | ------------------------ |
| -n       | 依照数值的大小排序       |
| -r       | 以相反的顺序来排序       |
| -t       | 设置排序时所用的分隔字符 |
| -k       | 指定需要排序的列         |

参数：指定待排序的文件列表



#### 案例实操

- 数据准备

```shell
[root@localhost src]# vim sort.sh
bb:40:5.4
bd:20:4.2
xz:50:2.3
cls:10:3.5
ss:30:1.6
```

- 按照“：”分割后的第三列倒序排序。

```shell
[root@localhost src]# sort -t : -nrk 3 sort.sh 
bb:40:5.4
bd:20:4.2
cls:10:3.5
xz:50:2.3
ss:30:1.6
```