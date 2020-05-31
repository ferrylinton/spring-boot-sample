package org.mitre.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class EhcacheUtil {
	
	private static final String EHCACHE_MANAGER = "ehCacheManager";

	public static final int CACHE_SIZE = 200;
	
	public synchronized static Cache createCache(String cacheName) {
		if(getCacheManager().cacheExists(cacheName)) {
			return getCacheManager().getCache(cacheName);
		}else {
			long timeToLiveSeconds = 60 * 60;
			long timeToIdleSeconds = 15 * 60;
			Cache cache = new Cache(cacheName, CACHE_SIZE, false, false, timeToLiveSeconds, timeToIdleSeconds);
			cache.setDisabled(isDisabled());
			addCache(cache);
			return cache;
		}
	}

	public static void addCache(Cache cache) {
		getCacheManager().addCache(cache);;
	}
	
	public static boolean isDisabled() {
		return BeanUtil.getBean(CacheProperty.class).isDisabled();
	}
	
	public static CacheManager getCacheManager() {
		return BeanUtil.getBean(EHCACHE_MANAGER, CacheManager.class);
	}
	
}
