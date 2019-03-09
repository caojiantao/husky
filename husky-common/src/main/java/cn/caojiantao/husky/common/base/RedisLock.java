package cn.caojiantao.husky.common.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author caojiantao
 */
@Component
public class RedisLock {

    /**
     * Redis原生连接，采用自动注入初始化
     */
    private final LettuceConnectionFactory connectionFactory;

    @Autowired
    public RedisLock(LettuceConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * 尝试加锁
     *
     * @param lockKey    加锁的KEY
     * @param requestId  加锁客户端唯一ID标识
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     * @return 是否加锁成功
     */
    public Boolean tryLock(String lockKey, String requestId, long expireTime, TimeUnit timeUnit) {
        RedisConnection connection = connectionFactory.getConnection();
        Boolean result = connection.set(lockKey.getBytes(StandardCharsets.UTF_8), requestId.getBytes(StandardCharsets.UTF_8), Expiration.from(expireTime, timeUnit), RedisStringCommands.SetOption.SET_IF_ABSENT);
        connection.close();
        return result;
    }

    /**
     * 释放锁
     *
     * @param lockKey   加锁的KEY
     * @param requestId 加锁客户端唯一ID标识
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey, String requestId) {
        // Lua代码，一次性执行保证原子性，避免并发问题
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisConnection connection = connectionFactory.getConnection();
        byte[][] keysAndArgs = new byte[2][];
        keysAndArgs[0] = lockKey.getBytes(StandardCharsets.UTF_8);
        keysAndArgs[1] = requestId.getBytes(StandardCharsets.UTF_8);
        Long result = connection.scriptingCommands().eval(script.getBytes(StandardCharsets.UTF_8), ReturnType.INTEGER, 1, keysAndArgs);
        connection.close();
        return result != null && result > 0;
    }
}
