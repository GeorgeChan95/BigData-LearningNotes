## service和chkconfig命令

### 环境

- Centos 7.6
- xshell 6
- vmvare 15.5



### service 后台服务管理



#### **基本语法**

service 服务名 start		（功能描述：开启服务）

service 服务名 stop         （功能描述：关闭服务）

service 服务名 restart        （功能描述：重新启动服务）

service 服务名 status        （功能描述：查看服务状态）



#### 经验技巧

查看服务的方法：

```shell
/etc/init.d/服务名
```

![image-20200606150402074](images/image-20200606150402074.png)



#### 案例实操

##### 查看网络服务的状态

```shell
service network status
```

##### 停止网络服务

```shell
service network stop
```

##### 启动网络服务

```shell
service network start
```

##### 重启网络服务

```shell
service network restart
```

##### 查看系统中所有的后台服务

```shell
service --status-all
```



### chkconfig 设置后台服务的自启配置



#### 基本语法

chkconfig           				（功能描述：查看所有服务器自启配置）

chkconfig 服务名 off  	  （功能描述：关掉指定服务的自动启动）

chkconfig 服务名 on  	  （功能描述：开启指定服务的自动启动）

chkconfig 服务名 --list     （功能描述：查看服务开机启动状态）



#### 案例实操

关闭network服务的自动启动

```shell
chkconfig network off
```

开启network服务的自动启动

```shell
chkconfig network on
```



### 进程运行级别



#### 查看默认级别

```shell
# Centos7
systemctl get-default

# Centos6
vi /etc/inittab
```

Linux系统有7种运行级别(runlevel)：**常用的是级别 **3和5



#### 级别明细

- **运行级别0：**系统停机状态，系统默认运行级别不能设为0，否则不能正常启动
- **运行级别1：**单用户工作状态，root权限，用于系统维护，禁止远程登陆
- **运行级别2：**多用户状态(没有NFS)，不支持网络
- **运行级别3：**完全的多用户状态(有NFS)，登陆后进入控制台命令行模式
- **运行级别4：**系统未使用，保留
- **运行级别5：**X11控制台，登陆后进入图形GUI模式
- **运行级别6：**系统正常关闭并重启，默认运行级别不能设为6，否则不能正常启动