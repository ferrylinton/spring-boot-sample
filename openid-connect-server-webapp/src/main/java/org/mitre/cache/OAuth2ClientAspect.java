package org.mitre.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Component
@Aspect
public class OAuth2ClientAspect {

	private static final Logger logger = LoggerFactory.getLogger(OAuth2ClientAspect.class);
	
	@Autowired
	private Cache oauth2ClientCache;
	
	@Around("execution(* org.mitre.oauth2.repository.impl.JpaOAuth2ClientRepository.getClientByClientId(..)) && args(clientId)")
	public Object getClientByClientId(ProceedingJoinPoint proceedingJoinPoint, String clientId) throws Throwable{
		Object obj = null;
		
		try {
			
			if (oauth2ClientCache.isKeyInCache(clientId)) {
				logger.info("cache :: org.mitre.oauth2.repository.impl.JpaOAuth2ClientRepository.getClientByClientId() from cache");
	            return oauth2ClientCache.get(clientId).getObjectValue();
	        } else {
	        	obj = proceedingJoinPoint.proceed();
	           
	            if (obj != null) {
	                oauth2ClientCache.put(new Element(clientId, obj));
	            }
	        }

		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
		
		return obj;
	}

}
