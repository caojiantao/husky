package cn.caojiantao.husky;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author admin
 * @date 2018/9/29 16:13
 */
@EnableCaching
@MapperScan("cn.caojiantao.husky.mapper")
@SpringBootApplication
public class HuskyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuskyApplication.class, args);
    }
}
