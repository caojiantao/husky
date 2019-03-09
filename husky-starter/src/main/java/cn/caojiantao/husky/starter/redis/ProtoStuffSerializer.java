package cn.caojiantao.husky.starter.redis;

import io.protostuff.*;
import io.protostuff.runtime.RuntimeSchema;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author caojiantao
 * @since 2018-10-05 00:39:00
 */
public class ProtoStuffSerializer<T> implements RedisSerializer<T> {

    private Class<T> clazz;

    public ProtoStuffSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        return ProtostuffIOUtil.toByteArray(t, schema, LinkedBuffer.allocate());
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        T t = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, t, schema);
        return t;
    }
}
