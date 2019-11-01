package cn.caojiantao.husky.system.configuration;

import lombok.Data;

/**
 * @author caojiantao
 */
@Data
public class TokenConfig {

    private String key;
    private String secret;
    private Long expired;
}
