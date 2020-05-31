package org.mitre.cache;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

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
	private CacheManager ehCacheManager;
	
	@Autowired
	private CacheProperty cacheProperty;
	
	private Cache cache;
	
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
			
			if (cache.isKeyInCache(accessTokenValue) && 
					cache.get(accessTokenValue) != null &&
						cache.get(accessTokenValue).getObjectValue() != null) {
				
				logger.info("cache :: get [accessTokenValue='{}']", accessTokenValue);
	            return cache.get(accessTokenValue).getObjectValue();
	        } else {
	        	obj = proceedingJoinPoint.proceed();
	           
	            if (obj != null) {
	            	logger.info("cache :: add [accessTokenValue='{}']", accessTokenValue);
	                cache.put(new Element(accessTokenValue, obj));
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
                cache.put(new Element(token.getValue(), obj));
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
			
			if (cache.isKeyInCache(accessToken.getValue())) {
				logger.info ("cache :: remove [accessTokenValue='{}']", accessToken.getValue());
				
				cache.remove(accessToken.getValue());
	        }
			
		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}
	
	@PostConstruct
    private void postConstruct() {
		String name = OAuth2AccessTokenEntity.class.getSimpleName() + "CacheByValue";
		long timeToLiveSeconds = 1 * 60 * 60;
		long timeToIdleSeconds = 15 * 60;
		cache = new Cache(name, 200, false, false, timeToLiveSeconds, timeToIdleSeconds);
		cache.setDisabled(cacheProperty.isDisabled());
		ehCacheManager.addCache(cache);
	}
	
}
