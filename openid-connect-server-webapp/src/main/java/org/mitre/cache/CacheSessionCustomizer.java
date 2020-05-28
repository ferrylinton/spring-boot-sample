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
		logger.info("######################################################");
		logger.info("################## CACHE IS ENABLE ###################");
		logger.info("######################################################");
		Collection<ClassDescriptor> classDescriptors = session.getDescriptors().values();
		
		for (ClassDescriptor classDescriptor : classDescriptors) {
			classDescriptor.useSoftCacheWeakIdentityMap();
			classDescriptor.setIdentityMapSize(200);
			classDescriptor.setCacheIsolation(CacheIsolationType.SHARED);
			classDescriptor.setCacheInterceptorClass(EhcacheCacheInterceptor.class);
	 	}

	}

}
