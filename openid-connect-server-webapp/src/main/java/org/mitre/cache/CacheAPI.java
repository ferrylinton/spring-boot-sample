package org.mitre.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.ehcache.Cache;



@Controller
@RequestMapping("/api/caches")
@PreAuthorize("hasRole('ROLE_USER')")
public class CacheAPI {

	@Autowired
	private EhCacheManagerFactoryBean ehCacheManager;
	
	@RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> getTotalEhCacheSize(Model model, Authentication auth) {
		Map<String, Object> result = new HashMap<>();
		
		for (String name : ehCacheManager.getObject().getCacheNames()) {
			Cache cache = ehCacheManager.getObject().getCache(name);
			
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
	
}
