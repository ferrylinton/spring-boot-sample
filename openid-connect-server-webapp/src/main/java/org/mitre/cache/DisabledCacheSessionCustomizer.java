package org.mitre.cache;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.sessions.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisabledCacheSessionCustomizer implements SessionCustomizer {
	
	private static final Logger logger = LoggerFactory.getLogger(DisabledCacheSessionCustomizer.class);

	@Override
	public void customize(Session session) throws Exception {
		logger.info("###############################################");
		logger.info("################## NO CACHE ###################");
		logger.info("###############################################");
	}

}
