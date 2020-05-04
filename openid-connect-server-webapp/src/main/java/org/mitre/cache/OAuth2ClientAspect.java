package org.mitre.cache;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.mitre.oauth2.model.ClientDetailsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * 
 * OAuth2ClientAspect handle cache for OAuth2Client data
 * 
 * @see org.mitre.oauth2.repository.impl.JpaOAuth2ClientRepository
 * @author Ferry L. H.
 *
 */
@Component
@Aspect
public class OAuth2ClientAspect {

	private static final Logger logger = LoggerFactory.getLogger(OAuth2ClientAspect.class);
	
	@Autowired
	private Cache oauth2ClientCache;

	/**
	 * Add ClientDetailsEntity data to cache if not exist, and return ClientDetailsEntity data from cache if exist
	 * 
	 * @param proceedingJoinPoint AOP's ProceedingJoinPoint
	 * @param clientId OpenID's client_id
	 * @return ClientDetailsEntity's data
	 * @throws Throwable
	 */
	@Around("execution(* org.mitre.oauth2.repository.impl.JpaOAuth2ClientRepository.getClientByClientId(..)) && args(clientId)")
	public Object getClientByClientId(ProceedingJoinPoint proceedingJoinPoint, String clientId) throws Throwable{
		Object obj = null;
		
		try {
			
			if (oauth2ClientCache.isKeyInCache(clientId) && 
					oauth2ClientCache.get(clientId) != null &&
						oauth2ClientCache.get(clientId).getObjectValue() != null) {
				
				logger.info("cache :: get [clientId='{}']", clientId);
	            return oauth2ClientCache.get(clientId).getObjectValue();
	        } else {
	        	obj = proceedingJoinPoint.proceed();
	           
	            if (obj != null) {
	            	logger.info("cache :: add [clientId='{}']", clientId);
	                oauth2ClientCache.put(new Element(clientId, obj));
	            }
	        }

		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}
		
		return obj;
	}
	
	/**
	 * Remove ClientDetailsEntity data from cache, before update ClientDetailsEntity data
	 * 
	 * @param joinPoint AOP's JoinPoint
	 * @param id ClientDetailsEntity's id
	 * @param client ClientDetailsEntity's object
	 */
	@Before("execution(* org.mitre.oauth2.repository.impl.JpaOAuth2ClientRepository.updateClient(..)) && args(id, client)")
	public void updateClient(JoinPoint joinPoint, Long id, ClientDetailsEntity client){
		try {
			
			if (oauth2ClientCache.isKeyInCache(client.getClientId())) {
				logger.info ("cache :: remove [clientId='{}']", client.getClientId());
	            oauth2ClientCache.remove(client.getClientId());
	        }

		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}
	
	/**
	 * Remove ClientDetailsEntity data from cache, before delete ClientDetailsEntity data
	 * 
	 * @param joinPoint AOP's JoinPoint
	 * @param client ClientDetailsEntity's object
	 */
	@Before("execution(* org.mitre.oauth2.repository.impl.JpaOAuth2ClientRepository.deleteClient(..)) && args(client)")
	public void deleteClient(JoinPoint joinPoint, ClientDetailsEntity client){
		try {

			if (oauth2ClientCache.isKeyInCache(client.getClientId())) {
				logger.info ("cache :: remove [clientId='{}']", client.getClientId());
	            oauth2ClientCache.remove(client.getClientId());
	        }
			
		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}

}
