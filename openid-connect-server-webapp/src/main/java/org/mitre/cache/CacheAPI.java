package org.mitre.cache;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.tools.profiler.PerformanceMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Transactional(readOnly = true)
@Controller
@RequestMapping("/api/caches")
@PreAuthorize("hasRole('ROLE_USER')")
public class CacheAPI {

	@Autowired
	private EhCacheManagerFactoryBean ehCacheManager;
	
	@PersistenceContext(unitName="defaultPersistenceUnit")
	private EntityManager manager;
	
	@RequestMapping(value = "/ehcache/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> getEhcacheInfo() {
		Map<String, Object> result = new HashMap<>();
		
		for (String name : ehCacheManager.getObject().getCacheNames()) {
			net.sf.ehcache.Cache cache = ehCacheManager.getObject().getCache(name);
			
			Map<String, Object> statistics = new HashMap<>();
			statistics.put("size", cache.getStatistics().getSize());
			statistics.put("cacheHitCount", cache.getStatistics().cacheHitCount());
			statistics.put("cachePutCount", cache.getStatistics().cachePutCount());
			statistics.put("cacheEvictedCount", cache.getStatistics().cacheEvictedCount());
			statistics.put("cacheExpiredCount", cache.getStatistics().cacheExpiredCount());
			
			result.put(name, statistics);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/eclipselink/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> getEclipselinkCacheInfo() {
		PerformanceMonitor performanceMonitor = (PerformanceMonitor) manager.unwrap(Session.class).getProfiler();
		return performanceMonitor.getOperationTimings();
	}
	
}
