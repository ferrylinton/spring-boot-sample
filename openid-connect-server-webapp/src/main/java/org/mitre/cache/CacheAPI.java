package org.mitre.cache;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;


@Transactional(readOnly = true)
@Controller
@RequestMapping("/api/caches")
@PreAuthorize("hasRole('ROLE_USER')")
public class CacheAPI {

	@Autowired
	private CacheManager ehCacheManager;
	
	@PersistenceContext(unitName="defaultPersistenceUnit")
	private EntityManager manager;
	
	@RequestMapping(value = "/ehcache/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> getEhcacheInfo() {
		Map<String, Object> result = new HashMap<>();
		
		for (String name : ehCacheManager.getCacheNames()) {
			Cache cache = ehCacheManager.getCache(name);
			
			Map<String, Object> statistics = new HashMap<>();
			statistics.put("size", cache.getSize());
			statistics.put("cacheHitCount", cache.getSize());
			statistics.put("cachePutCount", cache.getStatistics().cachePutCount());
			statistics.put("cacheEvictedCount", cache.getStatistics().cacheEvictedCount());
			statistics.put("cacheExpiredCount", cache.getStatistics().cacheExpiredCount());
			
			result.put(name, statistics);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/ehcache/clear", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> clearEhcache() {
		Map<String, Object> result = new HashMap<>();
		
		for (String name : ehCacheManager.getCacheNames()) {
			net.sf.ehcache.Cache cache = ehCacheManager.getCache(name);
			cache.removeAll();
			result.put(name, cache.getSize());
		}
		
		manager.getEntityManagerFactory().getCache().evictAll();
		return result;
	}
	
}
