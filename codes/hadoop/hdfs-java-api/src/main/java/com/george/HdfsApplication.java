package com.george;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *  启动主类
 * </p>
 *
 * @author GeorgeChan 2020/1/4 15:21
 * @version 1.0
 * @since jdk1.8
 */
@SpringBootApplication
public class HdfsApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(HdfsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HdfsApplication.class, args);
        LOGGER.info("SpringBoot 启动成功......");
    }
}
