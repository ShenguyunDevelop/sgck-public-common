package com.sgck.cache.redis.serializer;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

public class StringRedisKeySerializer implements RedisSerializer<Object> {

	private final Charset charset;

	public StringRedisKeySerializer() {
		this(Charset.forName("UTF8"));
	}

	public StringRedisKeySerializer(Charset charset) {
		Assert.notNull(charset);
		this.charset = charset;
	}

	public Object deserialize(byte[] bytes) {
		return (bytes == null ? null : new String(bytes, charset));
	}

	public byte[] serialize(Object string) {
		return (string == null ? null : String.valueOf(string).getBytes(charset));
	}
}
