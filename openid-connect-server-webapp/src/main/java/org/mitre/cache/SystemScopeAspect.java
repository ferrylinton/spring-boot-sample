package org.mitre.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;

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
	private Cache systemScopeCache;
	
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
			
			if (systemScopeCache.isKeyInCache(id) && 
					systemScopeCache.get(id) != null && 
						systemScopeCache.get(id).getObjectValue() != null) {
				
				logger.info("cache :: get [id='{}']", id);
	            return systemScopeCache.get(id).getObjectValue();
	        } else {
	        	obj = proceedingJoinPoint.proceed();
	           
	            if (obj != null) {
	            	logger.info("cache :: add [id='{}']", id);
	                systemScopeCache.put(new Element(id, obj));
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
			
			if (systemScopeCache.isKeyInCache(scope) && 
					systemScopeCache.get(scope) != null &&
						systemScopeCache.get(scope).getObjectValue() != null) {
				
				logger.info("cache :: get [scope='{}']", scope);
	            return systemScopeCache.get(scope).getObjectValue();
	        } else {
	        	obj = proceedingJoinPoint.proceed();
	           
	            if (obj != null) {
	            	logger.info("cache :: add [scope='{}']", scope);
	                systemScopeCache.put(new Element(scope, obj));
	            }
	        }

		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
		
		return obj;
	}

}
