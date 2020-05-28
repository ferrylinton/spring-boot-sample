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
	
	private Cache cache;
	
	/**
	 * Add SystemScope data to cache if not exist, and return SystemScope data from cache if exist <br>
	 * SystemScope's data will be saved in cache with SystemScope's id as key
	 * 
	 * @param proceedingJoinPoint
	 * @param id SystemScope's id
	 * @return SystemScope's object
	 * @throws Throwable
	 */
	@Around("execution(* org.mitre.oauth2.repository.impl.JpaSystemScopeRepository.getById(..)) && args(id)")
	public Object getById(ProceedingJoinPoint proceedingJoinPoint, Long id) throws Throwable{
		Object obj = null;
		
		try {
			
			if (cache.isKeyInCache(id) && 
					cache.get(id) != null && 
						cache.get(id).getObjectValue() != null) {
				
				logger.info("cache :: get [id='{}']", id);
	            return cache.get(id).getObjectValue();
	        } else {
	        	obj = proceedingJoinPoint.proceed();
	           
	            if (obj != null) {
	            	logger.info("cache :: add [id='{}']", id);
	                cache.put(new Element(id, obj));
	            }
	        }

		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
		
		return obj;
	}
	
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
		int maxElementsInMemory = 20;
		boolean overflowToDisk = false;
		boolean eternal = true;
		long timeToLiveSeconds = 60 * 60;
		long timeToIdleSeconds = 15 * 60;
		cache = new Cache(name, maxElementsInMemory, overflowToDisk, eternal, timeToLiveSeconds, timeToIdleSeconds);
		ehCacheManager.addCache(cache);
	}

}
