package org.mitre.cache;

import java.util.Collection;

import org.eclipse.persistence.config.CacheIsolationType;
import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sessions.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheSessionCustomizer implements SessionCustomizer {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheSessionCustomizer.class);

	@Override
	public void customize(Session session) throws Exception {
		if (EhcacheUtil.isDisabled()) {
			noCache();
		}else {
			enableCache(session);
		}
		
	}
	
	private synchronized void noCache() {
		logger.info("###############################################");
		logger.info("################## NO CACHE ###################");
		logger.info("###############################################");
	}
	
	private synchronized void enableCache(Session session) {
		logger.info("######################################################");
		logger.info("################## CACHE IS ENABLE ###################");
		logger.info("######################################################");
		
		Collection<ClassDescriptor> classDescriptors = session.getDescriptors().values();
		
		for (ClassDescriptor classDescriptor : classDescriptors) {
			classDescriptor.useSoftCacheWeakIdentityMap();
			classDescriptor.setIdentityMapSize(EhcacheUtil.CACHE_SIZE);
			classDescriptor.setCacheIsolation(CacheIsolationType.SHARED);
			classDescriptor.setCacheInterceptorClass(EhcacheCacheInterceptor.class);
	 	}
	}

}
