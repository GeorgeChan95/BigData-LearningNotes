package com.george;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *     启动主类
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/5 10:36
 * @since JDK 1.8
 */
@SpringBootApplication
public class JvmJucApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(JvmJucApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JvmJucApplication.class, args);
        LOGGER.info("springboot 项目启动成功!");
    }
}
