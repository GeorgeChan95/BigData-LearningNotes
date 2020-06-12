## RPM命令

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5



### RPM概述

RPM（RedHat Package Manager），RedHat软件包管理工具，类似windows里面的setup.exe

 是Linux这系列操作系统里面的打包安装工具，它虽然是RedHat的标志，但理念是通用的。

**RPM包的名称格式**

Apache-1.3.23-11.i386.rpm

\-     “apache” 软件名称

\-     “1.3.23-11”软件的版本号，主版本和此版本

\-     “i386”是软件所运行的硬件平台，Intel 32位微处理器的统称

\-     “rpm”文件扩展名，代表RPM包





### RPM查询命令（rpm -qa）



#### 基本语法

rpm -qa            （功能描述：查询所安装的所有rpm软件包）



#### 经验技巧

由于软件包比较多，一般都会采取过滤。**rpm -qa | grep rpm**软件包



#### 案例实操

```shell
# 查询 vim 软件安装情况
[root@hadoop130 ~]# rpm -qa | grep vim
vim-common-7.4.629-6.el7.x86_64
vim-minimal-7.4.629-6.el7.x86_64
vim-filesystem-7.4.629-6.el7.x86_64
vim-enhanced-7.4.629-6.el7.x
```





### RPM卸载命令（rpm -e）



#### 基本语法

rpm -e RPM软件包 

rpm -e --nodeps 软件包 



#### 选项说明

| 选项     | 功能                                                         |
| -------- | ------------------------------------------------------------ |
| -e       | 卸载软件包                                                   |
| --nodeps | 卸载软件时，不检查依赖。这样的话，那些使用该软件包的软件在此之后可能就不能正常工作了。 |



#### 案例实操

```shell
# 卸载firefox软件
[root@hadoop130 ~]# rpm -e firefox
```





### RPM安装命令（rpm -ivh）



#### 基本语法

rpm -ivh RPM包全名



#### 选项说明

| 选项     | 功能                     |
| -------- | ------------------------ |
| -i       | -i=install，安装         |
| -v       | -v=verbose，显示详细信息 |
| -h       | -h=hash，进度条          |
| --nodeps | --nodeps，不检测依赖进度 |



#### 案例实操

```shell
# 安装firefox软件
rpm -ivh firefox-45.0.1-1.el6.centos.x86_64.rpm
```


