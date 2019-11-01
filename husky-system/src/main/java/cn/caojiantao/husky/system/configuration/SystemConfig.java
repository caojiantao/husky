package cn.caojiantao.husky.system.configuration;

import cn.caojiantao.husky.common.spring.MultiPropertyFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author caojiantao
 */
@Configuration
@PropertySource(value = "classpath:system.yml", factory = MultiPropertyFactory.class)
public class SystemConfig {

    @Bean
    @ConfigurationProperties(prefix = "system.token")
    public TokenConfig tokenConfig() {
        return new TokenConfig();
    }
}
