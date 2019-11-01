package cn.caojiantao.husky.starter;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author caojiantao
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "cn.caojiantao.husky")
@MapperScan("cn.caojiantao.husky.**.mapper")
public class StarterApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StarterApplication.class, args);
        log.info(context.getApplicationName() + " 启动成功，─=≡Σ(((つ•̀ω•́)つ");
    }
}
