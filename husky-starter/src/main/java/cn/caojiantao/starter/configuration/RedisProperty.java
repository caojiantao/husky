package cn.caojiantao.starter.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author caojiantao
 * @date 2018-10-31 00:12:27
 * @description
 */
@Data
@Configuration
@ConfigurationProperties("project-env.redis")
public class RedisProperty {

    private String host;
    private Integer port;
    private String password;
}
