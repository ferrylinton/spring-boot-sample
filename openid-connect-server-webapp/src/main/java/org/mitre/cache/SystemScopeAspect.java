package org.mitre.cache;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mitre.oauth2.model.SystemScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 
 * SystemScopeAspect handle cache for SystemScope data
 * 
 * @see org.mitre.oauth2.repository.impl.JpaSystemScopeRepository
 * @author Ferry L. H.
 *
 */
@Component
@Aspect
public class SystemScopeAspect {

	private static final Logger logger = LoggerFactory.getLogger(SystemScopeAspect.class);
	
	@Autowired
	private CacheManager ehCacheManager;
	
	@Autowired
	private CacheProperty cacheProperty;
	
	private Cache cache;
	
	/**
	 * Add SystemScope data to cache if not exist, and return SystemScope data from cache if exist<br>
	 * SystemScope's data will be saved in cache with SystemScope's scope as key
	 * 
	 * @param proceedingJoinPoint
	 * @param scope
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* org.mitre.oauth2.repository.impl.JpaSystemScopeRepository.getByValue(..)) && args(scope)")
	public Object getByValue(ProceedingJoinPoint proceedingJoinPoint, String scope) throws Throwable{
		Object obj = null;
		
		try {
			
			if (cache.isKeyInCache(scope) && 
					cache.get(scope) != null &&
						cache.get(scope).getObjectValue() != null) {
				
				logger.info("cache :: get [scope='{}']", scope);
	            return cache.get(scope).getObjectValue();
	        } else {
	        	obj = proceedingJoinPoint.proceed();
	           
	            if (obj != null) {
	            	logger.info("cache :: add [scope='{}']", scope);
	                cache.put(new Element(scope, obj));
	            }
	        }

		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
		
		return obj;
	}
	
	@PostConstruct
    private void postConstruct() {
		String name = SystemScope.class.getSimpleName() + "CacheByValue";
		cache = new Cache(name, 20, false, true, 0, 0);
		cache.setDisabled(cacheProperty.isDisabled());
		ehCacheManager.addCache(cache);
	}

}
