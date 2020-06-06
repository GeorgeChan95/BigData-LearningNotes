# Hadoop环境搭建之集群时间同步

时间同步的方式：找一个机器，作为时间服务器，所有的机器与这台集群时间进行定时的同步，比如，每隔十分钟，同步一次时间。

### 时间服务器配置（必须root用户）

- 检查ntp是否安装

  ```shell
  [root@hadoop01 hadoop-2.7.2]# rpm -qa|grep ntp
  ntp-4.2.6p5-29.el7.centos.x86_64
  ntpdate-4.2.6p5-29.el7.centos.x86_64
  ```

- 修改ntp配置文件

  ```shell
  # 授权192.168.1.0-192.168.1.255网段上的所有机器可以从这台机器上查询和同步时间 [下面这行注释释放]
  restrict 192.168.1.0 mask 255.255.255.0 nomodify notrap
  
  # 集群在局域网中，不使用其他互联网上的时间
  # 时间服务器配置注释
  # server 0.centos.pool.ntp.org iburst
  # server 1.centos.pool.ntp.org iburst
  # server 2.centos.pool.ntp.org iburst
  # server 3.centos.pool.ntp.org iburst
  
  # 当该节点丢失网络连接，依然可以采用本地时间作为时间服务器为集群中的其他节点提供时间同步
  # 添加以下内容
  server 127.127.1.0
  fudge 127.127.1.0 stratum 10
  ```

- 修改`/etc/sysconfig/ntpd` 文件

  ```shell
  # 增加下面这行
  # 让硬件时间与系统时间一起同步
  SYNC_HWCLOCK=yes
  ```

- 重新启动ntpd服务

  ```shell
  # 查看服务状态
  service ntpd status
  # 启动
  service ntpd start
  # 停止服务
  service ntpd stop
  # 设置开机启动
  chkconfig ntpd on
  ```

### 其他机器配置（必须root用户）

- 在其他机器配置10分钟与时间服务器同步一次

  ```shell
  [root@hadoop02 hadoop]# crontab -e
  ```

  编写定时任务如下：

  ```shell
  */10 * * * * /usr/sbin/ntpdate hadoop01
  ```

### 测试

- 修改任意机器时间

  ```shell
  date -s "2018-9-11 23:23:11"
  ```

- 十分钟后查看机器是否与时间服务器同步

  ```shell
  date
  ```

注意点：每个节点都得安装 `ntp`服务，安装命令

```shell
yum install ntp ntpdate -y
```

*结束*