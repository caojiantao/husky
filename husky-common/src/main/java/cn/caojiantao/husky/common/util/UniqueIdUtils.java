package cn.caojiantao.husky.common.util;

import java.util.UUID;

/**
 * 全局唯一ID工具类
 *
 * @author caojiantao
 */
public class UniqueIdUtils {

    /**
     * 获取UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
