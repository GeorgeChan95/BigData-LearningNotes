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
