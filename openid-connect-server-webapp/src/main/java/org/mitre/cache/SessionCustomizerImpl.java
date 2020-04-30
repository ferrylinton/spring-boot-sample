package org.mitre.cache;

import java.util.Collection;

import org.eclipse.persistence.config.CacheIsolationType;
import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sessions.Session;

public class SessionCustomizerImpl implements SessionCustomizer {

	@Override
	public void customize(Session session) throws Exception {
		Collection<ClassDescriptor> classDescriptors = session.getDescriptors().values();
		
		for (ClassDescriptor classDescriptor : classDescriptors) {
			session.getDescriptor(classDescriptor.getJavaClass()).setIdentityMapSize(0);
			session.getDescriptor(classDescriptor.getJavaClass()).setCacheIsolation(CacheIsolationType.ISOLATED);
	 	}
	}

}
