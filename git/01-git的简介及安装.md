## Git的简介及安装

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5
- git 2.25.1



### 什么是Git

Git是目前世界上最先进的分布式版本控制系统。

![image-20200704103103638](images/image-20200704103103638.png)



### 版本管理系统能干什么

- 版本还原
- 代码备份
- 冲突解决
- 权限管理
- 协同开发
- 历史追查
- 版本记录
- 分支管理
- 代码审查

![image-20200704103312017](images/image-20200704103312017.png)



### 集中管理型版本管理

![image-20200704103341354](images/image-20200704103341354.png)



经典的集中管理型（CVS、VSS、SVN）

**特点：**

- 实现了大部分开发中对版本管理的需求
- 结构简单，上手容易。



### 分布式版本管理

![image-20200704103616960](images/image-20200704103616960.png)

**特点：**

- 容灾能力强
- 本地版本管理
- 异地协作
- 灵活分支





### Git的安装

- 命令行工具：Git for windows

  下载地址：https://git-for-windows.github.io/

- 操作系统中可视化工具：TortoiseGit

  下载地址： https://tortoisegit.org/

![image-20200704103811470](images/image-20200704103811470.png)



![image-20200704103820920](images/image-20200704103820920.png)



![image-20200704103826932](images/image-20200704103826932.png)



![image-20200704103836176](images/image-20200704103836176.png)



![image-20200704103841317](images/image-20200704103841317.png)



![image-20200704103853662](images/image-20200704103853662.png)

选择Git命令的执行环境，这里推荐选择第一个，就是单独用户Git自己的命令行窗口。

不推荐和windows的命令行窗口混用。





![image-20200704103929496](images/image-20200704103929496.png)

在“Configuring the line ending conversions”选项中，

- 在“Configuring the line ending conversions”选项中，
- 第二个选项：如果是跨平台项目，在Unix系统安装，选择；
- 第三个选项：非跨平台项目，选择。





![image-20200704104005990](images/image-20200704104005990.png)

在“terminal emulator”选项中，

- 第一个选项：使用专用的Git窗口（推荐）
- 第二个选项：使用windows的cmd命令行窗口。





![image-20200704104056998](images/image-20200704104056998.png)

在“Configuring extra”选项中，

- 默认开启文件缓存即可（推荐）





![image-20200704104127697](images/image-20200704104127697.png)





![image-20200704104206310](images/image-20200704104206310.png)

安装完成后，在任意的文件目录下，右键都可以开打Git的命令行窗口。



安装完成后，还需要最后一步设置，在命令行输入如下：

![image-20200704104250018](images/image-20200704104250018.png)

Git是分布式版本控制系统，所以需要填写用户名和邮箱作为一个标识。

C:\Users\admin路径下的.gitconfig文件里面可以看到

--global 表示全局属性，所有的git项目都会共用属性