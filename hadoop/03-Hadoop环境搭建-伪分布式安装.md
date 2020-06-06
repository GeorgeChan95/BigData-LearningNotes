# Hadoop环境搭建之伪分布式安装

> 系统：Centos 7.6
>
> JDK版本：1.8.0_231
>
> Hadoop版本：2.7.2

## 前期准备

#### 先决条件

hadoop的运行依赖JDK，需预先安装JDK，安装步骤见

- [Hadoop环境搭建之JDK安装](https://gitee.com/GeorgeChan/BigData-LearningNotes/blob/master/hadoop/02-Hadoop环境搭建-安装JDK.md)

#### 配置IP映射

- 配置hosts

```shell
# 编辑hosts文件
sudo vi /etc/hosts
# 配置映射IP
192.168.116.200 namenode01
```

- 配置主机名

```shell
sudo vi /etc/sysconfig/network
# 添加主机名称
HOSTNAME=namenode01
```

- 测试

```shell
[hadoop@namenode01 ~]$ hostname
namenode01
```

#### 配置免密登录

- 生成密钥

```shell
ssh-keygen -t rsa
```

然后需要连续回车3次

生成成功

```
[hadoop@hadoop01 .ssh]$ ssh-keygen -t rsa
Generating public/private rsa key pair.
Enter file in which to save the key (/home/hadoop/.ssh/id_rsa): 
Enter passphrase (empty for no passphrase): 
Enter same passphrase again: 
Your identification has been saved in /home/hadoop/.ssh/id_rsa.
Your public key has been saved in /home/hadoop/.ssh/id_rsa.pub.
The key fingerprint is:
SHA256:D3r09bg5Z3cDuN+eQFlY79sjMOkX3rlKgyGyNQJ2Rio hadoop@hadoop01
The key's randomart image is:
+---[RSA 2048]----+
|       .       . |
|      o       o .|
|   E + o     . ..|
|    o +     . o. |
|       oS+ =o+  .|
|       o=++oB+o +|
|      ... oooBo=.|
|       .   .+oB.*|
|            +*o*o|
+----[SHA256]-----+
```

- 将公钥写入到授权文件

```shell
cat /home/hadoop/.ssh/id_rsa.pub >> /home/hadoop/.ssh/authorized_keys
```

- 密钥文件修改权限

```
sudo chmod 700 /home/hadoop/.ssh/
sudo chmod 600 /home/hadoop/.ssh/authorized_keys 
```

​	*这是linux的安全要求，如果权限不对，自动登录将不会生效*

- 验证免密登录

```
ssh namenode01
```

​	*首次验证免密需要输入密码*

## 安装Hadoop

#### 下载解压

从[官网](https://archive.apache.org/dist/hadoop/common/)下载响应版本的Hadoop安装包，我这里是[2.7.2](https://archive.apache.org/dist/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz)，建议下载[CDH](http://archive.cloudera.com/cdh5/cdh/5/)版本的安装包，稳定且兼容性好。

```shell
# 下载安装包
wget https://archive.apache.org/dist/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz

# 解压到指定文件夹
tar -zxvf hadoop-2.7.2.tar.gz -C /opt/module/
```

#### 配置环境变量

```shell
sudo vi /etc/profile
# 添加Hadoop环境变量
export HADOOP_HOME=/opt/module/hadoop-2.7.2
export PATH=${HADOOP_HOME}/bin:$PATH:${HADOOP_HOME}/sbin:$PATH
```

​	*保存*

- 执行 `source` 命令，使得配置的环境变量立即生效：

```shell
source /etc/profile
```

- 验证是否配置成功

```shell
[hadoop@namenode01 software]$ hadoop version
Hadoop 2.7.2
Subversion https://git-wip-us.apache.org/repos/asf/hadoop.git -r b165c4fe8a74265c792ce23f546c64604acf0e41
Compiled by jenkins on 2016-01-26T00:08Z
Compiled with protoc 2.5.0
From source with checksum d0fda26633fa762bff87ec759ebe689c
This command was run using /opt/module/hadoop-2.7.2/share/hadoop/common/hadoop-common-2.7.2.jar
```

## 配置伪分布式

进入 `${HADOOP_HOME}/etc/hadoop/ `目录下，修改以下配置：

- 配置hadoop-env.sh

```shell
export JAVA_HOME=/opt/module/jdk1.8.0_231
```

- 配置mapred-env.sh

```shell
export JAVA_HOME=/opt/module/jdk1.8.0_231
```

- 配置yarn-env.sh

```shell
export JAVA_HOME=/opt/module/jdk1.8.0_231
```

- 配置core-site.xml

```xml
<configuration>
    <property>
        <!--指定 namenode 的 hdfs 协议文件系统的通信地址-->
        <name>fs.defaultFS</name>
        <value>hdfs://namenode01:9000</value>
    </property>
    <property>
        <!--指定 hadoop 存储临时文件的目录-->
        <name>hadoop.tmp.dir</name>
        <value>/opt/module/hadoop-2.7.2/data/tmp</value>
    </property> 
</configuration>
```

- 配置hdfs-site.xml

  指定副本系数和secondary namenode

```xml
<configuration>
    <!--由于我们这里搭建是单机版本，所以指定 dfs 的副本系数为 1-->
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
    <!--secondary namenode 服务器地址和端口-->
    <property>
        <name>dfs.namenode.secondary.http-address</name>
        <value>namenode01:50090</value>
    </property>
</configuration>
```

- 配置yarn-site.xml

```xml
<configuration>
    <!-- Reducer获取数据的方式 -->
    <property>
         <name>yarn.nodemanager.aux-services</name>
         <value>mapreduce_shuffle</value>
    </property>
    <!-- 指定YARN的ResourceManager的地址 -->
    <property>
         <name>yarn.resourcemanager.hostname</name>
         <value>namenode01</value>
    </property>
</configuration>
```

- 配置： (对mapred-site.xml.template重新命名为) mapred-site.xml

```xml
<configuration>
    <!-- 指定MR运行在YARN上 -->
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
</configuration>
```

- 配置`slaves`

```shell
namenode01
```

- 格式化

  *由于已经配置了环境变量，可在任意目录下执行格式化命令*

```shell
hdfs namenode -format
```

## 启动Hadoop

- 单个启动

```shell
# 启动 NameNode
hadoop-daemon.sh start namenode
# 启动 DataNode
hadoop-daemon.sh start datanode
# 启动 ResourceManager
yarn-daemon.sh start resourcemanager
# 启动 NodeManager
yarn-daemon.sh start nodemanager

# 查看启动程序
[hadoop@namenode01 ~]$ jps
8146 DataNode
8642 Jps
8052 NameNode
8501 NodeManager
8249 ResourceManager
```

- 网页访问

```
# 查看文件系统
http://192.168.116.200:50070/
# 查看节点集群
http://192.168.116.200:8088/cluster
```

- 批量启动

```sh
start-all.sh
```

- 关闭Hadoop

```sh
stop-all.sh
```

## 验证伪分布式

**执行hadoop自带的单词计数示例程序**

- 新建 `wc.input`

```shell
# 文件路径
/opt/module/hadoop-2.7.2/input
# 内容如下
hadoop yarn
hadoop mapreduce
george
george
```

​	保存

- 上传到分布式文件系统

```shell
# 在hdfs上新建文件夹
hdfs dfs -mkdir /input
# 文件上传，当前命令所在路径   /opt/module/hadoop-2.7.2
hdfs dfs -put input/wc.input /input/wc.input
# 第一个路径是服务器文件路径（相对路径）
# 第二个路径是hdfs文件所在路径（绝对路径）
```

- 执行单词统计程序

```shell
hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar wordcount /input /output
```

- 查看运行结果

```shell
[hadoop@namenode01 hadoop-2.7.2]$ hdfs dfs -cat /output/*
george	2
hadoop	2
mapreduce	1
yarn	1
```

