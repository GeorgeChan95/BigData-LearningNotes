# Centos7安装JDK1.8

> 环境：Vmware14
>
> 系统：Centos 7.6
>
> JDK版本：1.8.0_231



- **下载并解压**

  在[官网](https://www.oracle.com/technetwork/java/javase/downloads/index.html) 下载所需版本的 JDK，这里我下载的版本为[JDK 1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) ,下载后进行解压：

```shell
# 解压到指定路径
tar -zxvf jdk-8u231-linux-x64.tar.gz -C /usr/java
```

- **设置环境变量**

```shell
vi /etc/profile
```

​	在文件末尾追加

```shell
export JAVA_HOME=/usr/java/jdk1.8.0_231
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
```

​	执行 `source` 命令，使得配置立即生效：

```shell
source /etc/profile
```

- **检查是否安装成功**

```shell
[root@localhost src]# java -version
java version "1.8.0_231"
Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)
```

**结束**