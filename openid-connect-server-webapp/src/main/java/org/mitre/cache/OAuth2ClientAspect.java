package org.mitre.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;

@Aspect
public class OAuth2ClientAspect {

	private Cache cache;
	
	@Around("execution(public * getClientByClientId(..)) && args(clientId)")
	public Object getClientByClientId(ProceedingJoinPoint proceedingJoinPoint, String clientId){
		System.out.println(">>>>>>>>>>>>>>>>>> Before invoking getClientByClientId(). clientId = " + clientId);
		Object value = null;
		
		try {
			
			if (cache.isKeyInCache(clientId)) {
	            System.out.println(">>>>> Returning from cache....");
	            return cache.get(clientId).getObjectValue();
	        } else {
	        	value = proceedingJoinPoint.proceed();
	           
	            if (value != null) {
	                System.out.println(">>>>> Storing into cache...");
	                cache.put(new Element(clientId, value));
	            }
	        }

		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println(">>>>>>>>>>>>>>>>>> After invoking getClientByClientId(). Return value = "+value);
		return value;
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

}
