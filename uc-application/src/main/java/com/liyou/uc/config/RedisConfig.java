package com.liyou.uc.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyou.framework.base.store.LocalStore;
import com.liyou.framework.base.store.Store;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.framework.redis.RedisStore;
import com.liyou.framework.redis.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * cache集成redis,最好framework能支持，目前先放在本地服务中
 * @author: ivywooo
 * @date:2018/2/25
 **/
@Configuration
@EnableAspectJAutoProxy
public class RedisConfig extends CachingConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    public static final String LOCAL_CACHE = "LOCAL_CACHE";
    public static final String REDIS_CACHE = "REDIS_CACHE";

    @Bean(name = LOCAL_CACHE)
    public Store localCache(){
        return new LocalStore();
    }

    @Primary
    @Bean(name = REDIS_CACHE)
    public Store redisCache(){
        return new RedisStore();
    }

    //todo 解决jpa的Repository使用cacheable报错的问题

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer( Object.class);

        ObjectMapper mapper = JSONUtils.objectMapperCopy();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();

        RedisUtils.setRedisTemplate(template);
        return template;
    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(0);
        return cacheManager;
    }

    /**
     * 用于幂等
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager idempotentCacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //10s setFallbackToNoOpCache
        cacheManager.setDefaultExpiration(10);
        return cacheManager;
    }

    /**
     * 解除对redis的强依赖,经测试可行
     * 实现errorHandler()方法，我们可以覆盖Spring默认的DefaultErrorHandler。
     * 为了保证数据一致性，缓存清除方法出错时，我们按原样抛出异常，
     * 在查询和插入缓存时如果出现了Redis连接的异常，不做异常抛出处理，这样会继续从数据库中读取/写入数据，不影响正常业务服务，等缓存连接恢复后，又可以正常的提供服务了。
     * @return
     */

    @Override
    public CacheErrorHandler errorHandler() {
        logger.info("errorHandler");
        return new AppCacheErrorHandler();
    }

    public static class AppCacheErrorHandler implements CacheErrorHandler{

        @Override
        public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {

            if(e instanceof JedisConnectionException || e instanceof RedisConnectionFailureException){
                logger.warn("redis has lose connection:",e);
                return;
            }
            throw e;
        }

        @Override
        public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {

            if(e instanceof JedisConnectionException || e instanceof RedisConnectionFailureException){
                logger.warn("redis has lose connection:",e);
                return;
            }
            throw e;
        }

        @Override
        public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
            throw e;
        }

        @Override
        public void handleCacheClearError(RuntimeException e, Cache cache) {
            throw e;
        }
    }


}
