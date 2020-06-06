# Hadoop环境搭建之虚拟机准备

> 系统环境：Centos 7.6

- [Vmware14安装Centos7](https://gitee.com/GeorgeChan/BigData-LearningNotes/blob/master/虚拟机/VMware安装Centos7.md)

1. ### 关闭防火墙

   ```shell
   # 关闭防火墙
   systemctl stop firewalld
   # 查看防火墙状态
   firewall-cmd --state
   # 禁止防火墙开机启动
   systemctl disable firewalld
   ```

2. ### 创建hadoop用户

   ```shell
   # 新建用户
   useradd hadoop
   # 添加密码
   passwd hadoop
   ```

3. ### 创建hadoop用户需要的目录

   ```shell
   # 创建目录
   mkdir /opt/software /opt/module
   # 设置目录所属用户和用户组
   chown hadoop:hadoop /opt/software/ /opt/module/
   ```
   
4. ### 把hadoop用户添加到sudoers

   *配置hadoop用户* `sudo` *命令免密*

   ```shell
   vi /etc/sudoers
   # 添加以下内容（）
   hadoop  ALL=(ALL)       ALL
   %hadoop ALL=(ALL)       ALL
   hadoop  ALL=(ALL)       NOPASSWD: ALL
   %hadoop All=(ALL)       NOPASSWD: ALL
   # 强制保存
   :wq!
   ```

   这里注意：空格用 `Tab` 键 `NOPASSWD: ALL`中间也有空格

5. ### 配置静态IP

   *参考：*[Centos7配置静态IP](https://gitee.com/GeorgeChan/BigData-LearningNotes/blob/master/虚拟机/Centos7配置静态IP.md)

   - 编辑 ifcfg-ens33

   ```shell
   vi /etc/sysconfig/network-scripts/ifcfg-ens33
   ```

   *ifcfg-ens33是我的网卡名，根据个人情况不同*

   配置以下几项

   ```shell
   BOOTPROTO=static # 静态
   ONBOOT=yes # 开机启动
   IPADDR=192.168.116.200 # ip地址，在DHCP设置的范围内
   NETMASK=255.255.255.0 # 子网掩码
   GATEWAY=192.168.116.2 # 网关
   DNS1=8.8.8.8 # DNS
   DNS2=8.8.4.4
   ```

   完整配置如下：

   ```shell
   TYPE=Ethernet
   PROXY_METHOD=none
   BROWSER_ONLY=no
   BOOTPROTO=static
   DEFROUTE=yes
   IPV4_FAILURE_FATAL=no
   IPV6INIT=yes
   IPV6_AUTOCONF=yes
   IPV6_DEFROUTE=yes
   IPV6_FAILURE_FATAL=no
   IPV6_ADDR_GEN_MODE=stable-privacy
   NAME=ens33
   UUID=59534708-f47c-4427-8e11-e4eb33cfe06a
   DEVICE=ens33
   ONBOOT=yes
   IPADDR=192.168.116.200
   NETMASK=255.255.255.0
   GATEWAY=192.168.116.2
   DNS1=8.8.8.8
   DNS2=8.8.4.4
   ```

   - DNS文件配置

   ```shell
   vi /etc/resolv.conf 
   ```

   配置如下：

   ```shell
   nameserver 8.8.8.8
   nameserver 8.8.4.4
   ```

   保存退出

   - 重启网络服务

   ```shell
   service network restart
   ```

   - 测试

   ```shell
   # 是否可以连接互联网
   ping www.baidu.com
   # 查看IP
   ifconfig
   ```

6. ### 配置IP映射

   - 配置hosts

   ```shell
   # 编辑hosts文件
   vi /etc/hosts
   # 配置映射IP
   192.168.116.200 namenode01
   ```

   - 配置主机名

   ```shell
   vi /etc/sysconfig/network
   # 添加主机名称
   HOSTNAME=namenode01
   ```

   

