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

@Component
@Aspect
public class SystemScopeAspect {

	private static final Logger logger = LoggerFactory.getLogger(SystemScopeAspect.class);
	
	@Autowired
	private Cache systemScopeCache;
	
	@Around("execution(* org.mitre.oauth2.repository.impl.JpaSystemScopeRepository.getById(..)) && args(id)")
	public Object getById(ProceedingJoinPoint proceedingJoinPoint, Long id) throws Throwable{
		Object obj = null;
		
		try {
			
			if (systemScopeCache.isKeyInCache(id)) {
				logger.info("cache :: org.mitre.oauth2.repository.impl.JpaSystemScopeRepository.getById() from cache");
	            return systemScopeCache.get(id).getObjectValue();
	        } else {
	        	obj = proceedingJoinPoint.proceed();
	           
	            if (obj != null) {
	                systemScopeCache.put(new Element(id, obj));
	            }
	        }

		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
		
		return obj;
	}
	
	@Around("execution(* org.mitre.oauth2.repository.impl.JpaSystemScopeRepository.getByValue(..)) && args(scope)")
	public Object getByValue(ProceedingJoinPoint proceedingJoinPoint, String scope) throws Throwable{
		Object obj = null;
		
		try {
			
			if (systemScopeCache.isKeyInCache(scope)) {
				logger.info("cache :: org.mitre.oauth2.repository.impl.JpaSystemScopeRepository.getByValue() from cache");
	            return systemScopeCache.get(scope).getObjectValue();
	        } else {
	        	obj = proceedingJoinPoint.proceed();
	           
	            if (obj != null) {
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
