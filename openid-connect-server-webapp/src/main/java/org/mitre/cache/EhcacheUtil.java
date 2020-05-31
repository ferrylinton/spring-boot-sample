package org.mitre.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class EhcacheUtil {
	
	private static final String EHCACHE_MANAGER = "ehCacheManager";
	
	public static final int CACHE_SIZE = 200;
	
	public synchronized static Cache createCache(String cacheName) {
		
		if(BeanUtil.getBean(EHCACHE_MANAGER, CacheManager.class).cacheExists(cacheName)) {
			return BeanUtil.getBean(EHCACHE_MANAGER, CacheManager.class).getCache(cacheName);
		}else {
			boolean overflowToDisk = false;
			boolean eternal = false;
			long timeToLiveSeconds = 60 * 60;
			long timeToIdleSeconds = 15 * 60;
			Cache cache = new Cache(cacheName, CACHE_SIZE, overflowToDisk, eternal, timeToLiveSeconds, timeToIdleSeconds);
			BeanUtil.getBean(EHCACHE_MANAGER, CacheManager.class).addCache(cache);
			return cache;
		}

	}
}
