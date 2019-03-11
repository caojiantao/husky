package cn.caojiantao.husky.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author caojiantao
 */
@SpringBootApplication(scanBasePackages = "cn.caojiantao.husky")
@MapperScan("cn.caojiantao.husky.**.mapper")
public class StarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
    }
}
