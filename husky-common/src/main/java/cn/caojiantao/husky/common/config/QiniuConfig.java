package cn.caojiantao.husky.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author caojiantao
 */
@Data
@Component
@ConfigurationProperties("qiniu")
public class QiniuConfig {

    private String domain;
    private String accessKey;
    private String secretKey;
    private String bucket;
}
