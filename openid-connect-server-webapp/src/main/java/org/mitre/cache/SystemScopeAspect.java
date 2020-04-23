package org.mitre.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;

@Aspect
public class SystemScopeAspect {

	private Cache cache;
	
	@Around("execution(* org.mitre.oauth2.repository.impl.JpaSystemScopeRepository.getById(..)) && args(id)")
	public Object getById(ProceedingJoinPoint proceedingJoinPoint, Long id){
		System.out.println(">>>>>>>>>>>>>>>>>> Before invoking getById(). id = " + id);
		Object value = null;
		
		try {
			
			if (cache.isKeyInCache(id)) {
	            System.out.println(">>>>> Returning from cache....");
	            return cache.get(id).getObjectValue();
	        } else {
	        	value = proceedingJoinPoint.proceed();
	           
	            if (value != null) {
	                System.out.println(">>>>> Storing into cache...");
	                cache.put(new Element(id, value));
	            }
	        }

		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println(">>>>>>>>>>>>>>>>>> After invoking getById(). Return value = "+value);
		return value;
	}
	
	@Around("execution(* org.mitre.oauth2.repository.impl.JpaSystemScopeRepository.getByValue(..)) && args(scope)")
	public Object getByValue(ProceedingJoinPoint proceedingJoinPoint, String scope){
		System.out.println(">>>>>>>>>>>>>>>>>> Before invoking getByValue(). scope = " + scope);
		Object value = null;
		
		try {
			
			if (cache.isKeyInCache(scope)) {
	            System.out.println(">>>>> Returning from cache....");
	            return cache.get(scope).getObjectValue();
	        } else {
	        	value = proceedingJoinPoint.proceed();
	           
	            if (value != null) {
	                System.out.println(">>>>> Storing into cache...");
	                cache.put(new Element(scope, value));
	            }
	        }

		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println(">>>>>>>>>>>>>>>>>> After invoking getByValue(). Return value = "+value);
		return value;
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

}
