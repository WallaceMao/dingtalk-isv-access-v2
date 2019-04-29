package com.rishiqing.common.http;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2019-02-19 20:27
 */
public class RequestLimiter {
    public void checkRequestRate(String url){
        // Bandwidth limit = Bandwidth.simple(2, Duration.ofSeconds(1));
        // Bucket bucket = Bucket4j.builder().addLimit(limit).build();
        //
        // Date now = new Date();
        // System.out.println("----begin: " + now);
        // try {
        //     while (true) {
        //         if (new Date().getTime() - now.getTime() > 5000L) {
        //             break;
        //         }
        //         bucket.asScheduler().consume(1);
        //         printTime(now.getTime());
        //     }
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // printTime(now.getTime());
    }

    private void printTime(Long base) {
        Date now = new Date();
        System.out.println("----Date: " + now + ", elapse: " + (now.getTime() - base) + "ms");
    }

    public static void main(String[] args) throws Exception {
        // new RequestLimiter().checkRequestRate("https://www.rishiqing.com");

        // MutableConfiguration<String, Integer> jcacheConfig = new MutableConfiguration<>();
        // Config redisConfig = new Config();
        // redisConfig.useSingleServer().setAddress("redis://127.0.0.1:6379");
        // RedissonClient redisson = Redisson.create(redisConfig);
        // Configuration<String, Integer> config = RedissonConfiguration.fromInstance(redisson, jcacheConfig);
        // CacheManager manager = Caching.getCachingProvider().getCacheManager();
        // // Cache<String, String> cache = manager.createCache("namedCache", config);
        // Cache<String, Integer> cache = manager.createCache("testCache", config);

        // // URI redissonConfigUri = getClass().getResource("redisson-jcache.json").toURI();
        // CacheManager manager = Caching.getCachingProvider().getCacheManager();
        // Cache<String, String> cache = manager.createCache("namedCache", config);
    }
}
