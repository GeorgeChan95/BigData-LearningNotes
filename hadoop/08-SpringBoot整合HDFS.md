# Spring boot整合HDFS进行文件读写

> 系统环境：Centos 7.6、Windows10
>
> JDK版本：1.8.0_231
>
> Hadoop版本：2.7.2
>
> SpringBoot版本：2.1.6

## 准备工作

- [Windows搭建Hadoop开发环境]([https://gitee.com/GeorgeChan/BigData-LearningNotes/blob/master/hadoop/07-Windows%E6%90%AD%E5%BB%BAHadoop%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83.md](https://gitee.com/GeorgeChan/BigData-LearningNotes/blob/master/hadoop/07-Windows搭建Hadoop开发环境.md))

- 虚拟机启动 Hadoop集群

- 配置 `C:\Windows\System32\drivers\etc\HOSTS`文件

  ```
  192.168.116.201 hadoop01
  192.168.116.202 hadoop02
  192.168.116.203 hadoop03
  ```
  
  

## 代码编写

- **Hadoop Maven依赖**

```xml
<!-- hadoop 依赖 -->
<dependency>
    <groupId>org.apache.hadoop</groupId>
    <artifactId>hadoop-hdfs</artifactId>
    <version>2.7.2</version>
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
        </exclusion>
        <exclusion>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-common -->
<dependency>
    <groupId>org.apache.hadoop</groupId>
    <artifactId>hadoop-common</artifactId>
    <version>2.7.2</version>
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
        </exclusion>
        <exclusion>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.apache.hadoop</groupId>
    <artifactId>hadoop-client</artifactId>
    <version>2.7.2</version>
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
        </exclusion>
        <exclusion>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

- **yml配置文件** (非完整配置)

```yaml
# HDFS 相关配置
hadoop:
  # NameNode的地址
  name-node: hdfs://hadoop01:9000
  # Hadoop操作的用户名
  username: hadoop
  # 文件夹路径
  namespace: /hadoop
```

- **Hadoop 配置类**

```java
package com.george.config;

import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * <p>
 *  Hadoop配置类
 * </p>
 *
 * @author GeorgeChan 2020/1/4 15:25
 * @version 1.0
 * @since jdk1.8
 */
@Configuration
@ConditionalOnProperty(name="hadoop.name-node")
public class HadoopConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(HadoopConfig.class);
    /**
     * NameNode节点url
     */
    @Value("${hadoop.name-node:hdfs://hadoop01:9000}")
    private String namenode;
    /**
     * 文件系统用户名
     */
    @Value("${hadoop.username:root}")
    private String username;

    /**
     * Configuration conf=new Configuration（）；
     * 创建一个Configuration对象时，其构造方法会默认加载hadoop中的两个配置文件，
     * 分别是hdfs-site.xml以及core-site.xml，这两个文件中会有访问hdfs所需的参数值，
     * 主要是fs.default.name，指定了hdfs的地址，有了这个地址客户端就可以通过这个地址访问hdfs了。
     * 即可理解为configuration就是hadoop中的配置信息。
     * @return
     */
    @Bean("fileSystem")
    public FileSystem createFs() {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.set("fs.defaultFS", namenode);
        configuration.set("dfs.replication", "3");
        FileSystem fileSystem = null;

        try {
            URI uri = new URI(namenode.trim());
            fileSystem = FileSystem.get(uri, configuration, username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.info("fs.defaultFs ====> {}", configuration.get("fs.defaultFS"));
        return fileSystem;
    }
}
```

- **HdfsTemplate API工具类**

```java
package com.george.template;

import com.george.util.StringPool;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * HDFS 方法类
 * </p>
 *
 * @author GeorgeChan 2020/1/4 17:13
 * @version 1.0
 * @since jdk1.8
 */
@Component
@ConditionalOnBean(FileSystem.class)
public class HdfsTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(HdfsTemplate.class);
    @Autowired
    private FileSystem fileSystem;

    private static final int bufferSize = 1024 * 1024 * 64;

    /**
     * NameNode节点url
     */
    @Value("${hadoop.name-node:hdfs://hadoop01:9000}")
    private String namenode;
    /**
     * 操作文件夹路径
     */
    @Value("${hadoop.namespace:/}")
    private String namespace;

    @PostConstruct
    public void init() {
        try {
            existDir(namespace, true);
        } catch (IOException e) {
            LOGGER.error("系统异常，初始化 namespace 失败！");
            e.printStackTrace();
        }
    }

    /**
     * 检查文件夹是否存在，不存在则创建文件夹
     *
     * @param filePath 文件夹路径
     * @param create   是否创建文件夹
     * @return 是否操作成功
     */
    public boolean existDir(String filePath, boolean create) throws IOException {
        boolean flag = false;
        if (StringUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("filePath不能为空");
        }
        Path path = new Path(filePath);
        if (create) {
            if (!fileSystem.exists(path)) {
                fileSystem.mkdirs(path);
            }
        }
        if (fileSystem.isDirectory(path)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 检查文件夹是否存在
     *
     * @param filePath 文件夹路径
     * @return 是否操作成功
     */
    public boolean existDir(String filePath) throws IOException {
        boolean flag = false;
        if (StringUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("filePath不能为空");
        }
        Path path = new Path(filePath);
        if (fileSystem.isDirectory(path)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否存在
     */
    public boolean existFile(String filePath) throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            return false;
        }
        Path srcPath = new Path(filePath);
        boolean isExists = fileSystem.exists(srcPath);
        return isExists;
    }

    /**
     * 创建文件夹
     *
     * @param dirPath 文件夹路径
     * @return 是否创建成功
     */
    public boolean mkDir(String dirPath) throws IOException {
        if (StringUtils.isEmpty(dirPath)) {
            return false;
        }
        if (existDir(dirPath)) {
            return true;
        }
        Path path = new Path(dirPath);
        boolean res = fileSystem.mkdirs(path);
        return res;
    }

    /**
     * 读取目录信息
     *
     * @param dirPath 目录路径
     * @return 目录信息集合
     */
    public List<Map<String, Object>> readPathInfo(String dirPath) throws IOException {
        if (StringUtils.isEmpty(dirPath)) {
            return Collections.EMPTY_LIST;
        }
        if (!existDir(dirPath)) {
            return Collections.EMPTY_LIST;
        }
        // 目标路径
        Path path = new Path(dirPath);
        FileStatus[] fileStatuses = fileSystem.listStatus(path);
        List<Map<String, Object>> mapList = Lists.newArrayList();
        if (ArrayUtils.isNotEmpty(fileStatuses)) {
            for (FileStatus status : fileStatuses) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("filePath", status.getPath());
                map.put("blockSize", status.getBlockSize());
                map.put("fileStatus", fileStatuses.toString());
                mapList.add(map);
            }
            return mapList;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * HDFS创建文件
     *
     * @param filePath 文件路径
     * @param file     要上传的文件
     * @return 是否创建成功
     * @throws IOException
     */
    public boolean createFile(String filePath, MultipartFile file) throws IOException {
        if (StringUtils.isEmpty(filePath) || null == file || null == file.getBytes()) {
            return false;
        }
        // 文件名
        String fileName = file.getOriginalFilename();
        // 上传文件默认当前目录，再拼接文件名
        Path path = new Path(filePath + StringPool.SLASH + fileName);
        FSDataOutputStream outputStream = fileSystem.create(path);
        outputStream.write(file.getBytes());
        outputStream.close();
        return true;
    }

    /**
     * 读取文件
     *
     * @param filePath 文件路径
     * @return 输出字符串
     * @throws IOException
     */
    public String readFile(String filePath) throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            return StringPool.EMPTY;
        }

        if (!existFile(filePath)) {
            return StringPool.EMPTY;
        }

        Path path = new Path(filePath);
        // 获取文件输入流
        FSDataInputStream inputStream = fileSystem.open(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String lineText;
        while ((lineText = reader.readLine()) != null) {
            stringBuffer.append(lineText);
        }
        // 关闭输入流
        inputStream.close();
        return stringBuffer.toString();
    }

    /**
     * 获取文件列表
     *
     * @param dirPath 路径
     * @return 文件列表信息
     */
    public List<Map<String, String>> listFile(String dirPath) throws IOException {
        if (StringUtils.isEmpty(dirPath)) {
            return Collections.EMPTY_LIST;
        }
        if (!existDir(dirPath)) {
            return Collections.EMPTY_LIST;
        }

        Path path = new Path(dirPath);
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(path, true);
        List<Map<String, String>> fileList = Lists.newArrayList();
        while (files.hasNext()) {
            LocatedFileStatus next = files.next();
            String fileName = next.getPath().getName();
            Path filePath = next.getPath();
            Map<String, String> map = Maps.newHashMap();
            map.put("fileName", fileName);
            map.put("filePath", filePath.toString());
            fileList.add(map);
        }
        return fileList;
    }

    /**
     * 文件重命名
     *
     * @param oldName 旧文件名
     * @param newName 新文件名
     * @return 是否重命名成功
     * @throws IOException
     */
    public boolean renameFile(String oldName, String newName) throws IOException {
        if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
            return false;
        }

        // 原文件目标路径
        Path oldPath = new Path(oldName);
        // 重命名目标路径
        Path newPath = new Path(newName);
        boolean res = fileSystem.rename(oldPath, newPath);
        return res;
    }

    /**
     * 删除文件或文件夹
     *
     * @param filePath 文件（夹）名称
     * @return 是否删除成功
     * @throws IOException
     */
    public boolean deleteFile(String filePath) throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            return false;
        }
        if (!existFile(filePath)) {
            return false;
        }
        Path path = new Path(filePath);
        boolean res = fileSystem.delete(path, true);
        return res;
    }

    /**
     * 文件上传
     *
     * @param filePath   客户端文件路径
     * @param uploadPath 上传到服务器的路径
     * @return 是否上传成功
     * @throws IOException
     */
    public boolean uploadFile(String filePath, String uploadPath) throws IOException {
        if (StringUtils.isEmpty(filePath) || StringUtils.isEmpty(uploadPath)) {
            return false;
        }
        // 文件路径
        Path clientPath = new Path(filePath);
        // 目标路径
        Path serverPath = new Path(uploadPath);
        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fileSystem.copyFromLocalFile(false, clientPath, serverPath);
        return true;
    }

    /**
     * 文件下载
     *
     * @param clientFilePath 客户端路径
     * @param serverFilePath 服务端路径
     * @return 是否下载成功
     * @throws IOException
     */
    public boolean downloadFile(String clientFilePath, String serverFilePath) throws IOException {
        if (StringUtils.isEmpty(clientFilePath) || StringUtils.isEmpty(serverFilePath)) {
            return false;
        }
        // 客户端路径
        Path clientPath = new Path(clientFilePath);
        // 下载服务器路径
        Path serverPath = new Path(serverFilePath);

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fileSystem.copyToLocalFile(false, serverPath, clientPath);
        return true;
    }

    /**
     * 文件复制
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标路径
     * @return 是否复制成功
     * @throws IOException
     */
    public boolean copyFile(String sourcePath, String targetPath) throws IOException {
        if (StringUtils.isEmpty(sourcePath) || StringUtils.isEmpty(targetPath)) {
            return false;
        }
        // 原始文件路径
        Path oldPath = new Path(sourcePath);
        // 目标路径
        Path newPath = new Path(targetPath);

        FSDataInputStream inputStream = fileSystem.open(oldPath);
        FSDataOutputStream outputStream = fileSystem.create(newPath);

        IOUtils.copyBytes(inputStream, outputStream, bufferSize, false);
        inputStream.close();
        outputStream.close();
        return true;
    }

    /**
     * 获取某个文件在HDFS的集群位置
     *
     * @param filePath 文件路径
     * @return 返回结果
     * @throws IOException
     */
    public BlockLocation[] getFileBlockLocations(String filePath) throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        if (!existFile(filePath)) {
            return null;
        }
        // 目标路径
        Path path = new Path(filePath);
        FileStatus fileStatus = fileSystem.getFileStatus(path);
        BlockLocation[] fileBlockLocations = fileSystem.getFileBlockLocations(path, 0, fileStatus.getLen());
        return fileBlockLocations;
    }
}
```



*完整代码示例*  [hdfs-java-api](https://gitee.com/GeorgeChan/BigData-LearningNotes/tree/master/codes/hadoop/hdfs-java-api)