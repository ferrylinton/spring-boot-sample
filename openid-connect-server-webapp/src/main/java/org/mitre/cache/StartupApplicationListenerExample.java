package org.mitre.cache;

import org.mitre.oauth2.repository.SystemScopeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListenerExample implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(StartupApplicationListenerExample.class);
	
	@Autowired
	private SystemScopeRepository systemScopeRepository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("Application is starting ...");
		
		try {
			systemScopeRepository.getByValue("openid");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}
}
