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
public class OAuth2TokenAspect {

	private static final Logger logger = LoggerFactory.getLogger(OAuth2TokenAspect.class);
	
	@Autowired
	private Cache oauth2TokenCache;
	
	@Around("execution(* org.mitre.oauth2.repository.impl.JpaOAuth2TokenRepository.getAccessTokenByValue(..)) && args(accessTokenValue)")
	public Object getById(ProceedingJoinPoint proceedingJoinPoint, String accessTokenValue) throws Throwable{
		Object obj = null;
		
		try {
			
			if (oauth2TokenCache.isKeyInCache(accessTokenValue) && oauth2TokenCache.get(accessTokenValue).getObjectValue() != null) {
				logger.info("cache :: org.mitre.oauth2.repository.impl.JpaOAuth2TokenRepository.getAccessTokenByValue()");
	            return oauth2TokenCache.get(accessTokenValue).getObjectValue();
	        } else {
	        	obj = proceedingJoinPoint.proceed();
	           
	            if (obj != null) {
	                oauth2TokenCache.put(new Element(accessTokenValue, obj));
	            }
	        }

		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
		
		return obj;
	}
	
}
