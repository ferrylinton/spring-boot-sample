package org.mitre.cache;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.persistence.config.CacheIsolationType;
import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sessions.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheSessionCustomizer implements SessionCustomizer {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheSessionCustomizer.class);

	private static final List<String> TABLES = Arrays.asList("client_details", "approved_site", "saved_registered_client", "resource_set", "blacklisted_site", "pairwise_identifier", "permission_ticket", "refresh_token", "policy", "authorization_code", "system_scope", "whitelisted_site", "address", "authentication_holder", "access_token", "saved_user_auth", "claim", "permission");
	
	@Override
	public void customize(Session session) throws Exception {
		logger.info("######################################################");
		logger.info("################## CACHE IS ENABLE ###################");
		logger.info("######################################################");
		Collection<ClassDescriptor> classDescriptors = session.getDescriptors().values();
		
		for (ClassDescriptor classDescriptor : classDescriptors) {
			if(TABLES.contains(classDescriptor.getTableName())) {
				classDescriptor.useSoftCacheWeakIdentityMap();
				classDescriptor.setIdentityMapSize(50);
				classDescriptor.setCacheIsolation(CacheIsolationType.SHARED);
			}else {
				classDescriptor.setIdentityMapSize(0);
				classDescriptor.setCacheIsolation(CacheIsolationType.ISOLATED);
			}
	 	}
		
		
	}

}
