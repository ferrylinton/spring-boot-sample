package org.mitre.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;

/**
 * 
 * OAuth2TokenAspect handle cache for OAuth2AccessTokenEntity data
 * 
 * @see org.mitre.oauth2.repository.impl.JpaOAuth2TokenRepository
 * @author Ferry L. H.
 *
 */
@Component
@Aspect
public class OAuth2TokenAspect {

	private static final Logger logger = LoggerFactory.getLogger(OAuth2TokenAspect.class);
	
	@Autowired
	private Cache oauth2TokenCache;
	
	/**
	 * Add OAuth2AccessTokenEntity data to cache if not exist, and return ClientDetailsEntity data from cache if exist
	 * 
	 * @param proceedingJoinPoint AOP's ProceedingJoinPoint
	 * @param accessTokenValue access token from header
	 * @return OAuth2AccessTokenEntity token data
	 * @throws Throwable
	 */
	@Around("execution(* org.mitre.oauth2.repository.impl.JpaOAuth2TokenRepository.getAccessTokenByValue(..)) && args(accessTokenValue)")
	public Object getAccessTokenByValue(ProceedingJoinPoint proceedingJoinPoint, String accessTokenValue) throws Throwable{
		Object obj = null;
		
		try {
			
			if (oauth2TokenCache.isKeyInCache(accessTokenValue) && 
					oauth2TokenCache.get(accessTokenValue) != null &&
						oauth2TokenCache.get(accessTokenValue).getObjectValue() != null) {
				
				logger.info("cache :: get [accessTokenValue='{}']", accessTokenValue);
	            return oauth2TokenCache.get(accessTokenValue).getObjectValue();
	        } else {
	        	obj = proceedingJoinPoint.proceed();
	           
	            if (obj != null) {
	            	logger.info("cache :: add [accessTokenValue='{}']", accessTokenValue);
	                oauth2TokenCache.put(new Element(accessTokenValue, obj));
	            }
	        }

		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
		
		return obj;
	}
	
	@Around("execution(* org.mitre.oauth2.repository.impl.JpaOAuth2TokenRepository.saveAccessToken(..)) && args(token)")
	public Object saveAccessToken(ProceedingJoinPoint proceedingJoinPoint, OAuth2AccessTokenEntity token) throws Throwable{
		Object obj = null;
		
		try {
			
			obj = proceedingJoinPoint.proceed();
	           
            if (obj != null) {
            	logger.info("cache :: add [accessTokenValue='{}']", token.getValue());
                oauth2TokenCache.put(new Element(token.getValue(), obj));
            }

		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
		
		return obj;
	}
	
	@Before("execution(* org.mitre.oauth2.repository.impl.JpaOAuth2TokenRepository.removeAccessToken(..)) && args(accessToken)")
	public void removeAccessToken(JoinPoint joinPoint, OAuth2AccessTokenEntity accessToken) throws Throwable{
		try {
			
			if (oauth2TokenCache.isKeyInCache(accessToken.getValue())) {
				logger.info ("cache :: remove [accessTokenValue='{}']", accessToken.getValue());
				
				oauth2TokenCache.remove(accessToken.getValue());
	        }
			
		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}
	
}
