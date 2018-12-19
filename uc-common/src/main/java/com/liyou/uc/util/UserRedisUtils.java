package com.liyou.uc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.liyou.framework.base.utils.StringUtils;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.framework.redis.util.RedisUtils;
import com.liyou.uc.constant.TimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author: ivywooo
 * @date:2018/3/9
 **/

public class UserRedisUtils {
    private static final Logger logger = LoggerFactory.getLogger(UserRedisUtils.class);

    /**
     * 工具类禁止实例化
     */
    private UserRedisUtils() {
        throw new AssertionError();

    }

    public static void cachePut(final String key, final Object value, Long expire, TimeUnit timeUnit) {
        try {
            BoundHashOperations boundHashOperations = RedisUtils.boundHashOps(key);
            boundHashOperations.put(key, value);
            if (expire > 0) {
                boundHashOperations.expire(expire, timeUnit);
            }
            logger.info("cachePut_try_key ="+key);
        } catch (Exception ex) {
            logger.error("放入缓存失败", ex);
        }

        logger.info("cachePut_key ="+key);
    }

    public static void cachePut(final String key, final Object value, Long expire) {
        cachePut(key, value, expire, TimeUnit.MILLISECONDS);
    }


    public static void cachePut(final String key, final Object value, Boolean expireable) {

        //如果可过期，给一个默认过期时间1分钟
        if (expireable){
            cachePut(key, value, TimeConstants.MILLISECOND_PER_MINUTE);
        }else {
            //不过期
            cachePut(key, value, 0L);
        }


    }

    public static Object cacheGet(final String key) {
        BoundHashOperations boundHashOperations = RedisUtils.boundHashOps(key);
        return boundHashOperations.get(key);
    }


    /**
     * 删除key左匹配的所有value.
     *
     * @param key
     */
    public static void cacheRemoveLeft(final String key) {
        String pattern = key + "*";
        Set<String> keys = RedisUtils.keys(pattern);
        RedisUtils.delete(keys);
    }

    public static void delete(Object key){
        RedisUtils.delete(key);
    }

    public static void delete(Collection key){
        RedisUtils.delete(key);
    }

    public static String keyBuild(String prefix, String... param) {
        String key = prefix + "|";
        List<String> paramList = Lists.newArrayList(param);
        return key + StringUtils.join(paramList, "-");
    }

    public static String keyBuild(String prefix, String param) {
        String key = prefix + "|";
        return key + param;
    }


    public static Set<String>  keys(String pattern ){
        Set<String> keys = RedisUtils.keys(pattern);
        return keys;
    }
}
