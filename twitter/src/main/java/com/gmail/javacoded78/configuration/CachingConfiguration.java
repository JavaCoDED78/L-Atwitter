package com.gmail.javacoded78.configuration;

//@Configuration
//@EnableCaching
//public class CachingConfiguration extends CachingConfigurerSupport {
//
//    @Bean
//    public net.sf.ehcache.CacheManager ehCacheManager() {
//        CacheConfiguration tweetsCache = new CacheConfiguration();
//        tweetsCache.setName("tweets-cache");
//        tweetsCache.setMemoryStoreEvictionPolicy("LRU");
//        tweetsCache.setMaxEntriesLocalHeap(1000);
//        tweetsCache.setTimeToLiveSeconds(3600);
//        net.sf.ehcache.config.Configuration configuration = new net.sf.ehcache.config.Configuration();
//        configuration.addCache(tweetsCache);
//        return net.sf.ehcache.CacheManager.newInstance(configuration);
//    }
//
//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        return new EhCacheCacheManager(ehCacheManager());
//    }
//}
