package org.mitre.cache;

import java.util.Map;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.identitymaps.CacheKey;
import org.eclipse.persistence.internal.identitymaps.IdentityMap;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sessions.interceptors.CacheInterceptor;
import org.eclipse.persistence.sessions.interceptors.CacheKeyInterceptor;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheCacheInterceptor extends CacheInterceptor {

	private static final String EHCACHE_MANAGER = "ehCacheManager";
	
	private Cache cache;

	public EhcacheCacheInterceptor(IdentityMap targetIdentityMap, AbstractSession interceptedSession) {
		super(targetIdentityMap, interceptedSession);
		
		String name = targetIdentityMap.getDescriptorClass().getSimpleName() + "CacheById";
		int maxElementsInMemory = 200;
		boolean overflowToDisk = false;
		boolean eternal = false;
		long timeToLiveSeconds = 60 * 60;
		long timeToIdleSeconds = 15 * 60;
		cache = new Cache(name, maxElementsInMemory, overflowToDisk, eternal, timeToLiveSeconds, timeToIdleSeconds);
		BeanUtil.getBean(EHCACHE_MANAGER, CacheManager.class).addCache(cache);
	}

	@Override
	public Object clone() {
		return null;
	}

	@Override
	protected CacheKeyInterceptor createCacheKeyInterceptor(CacheKey wrappedCacheKey) {
		return new CacheKeyInterceptor(wrappedCacheKey) {

			private static final long serialVersionUID = 1L;

			@Override
			public Object getObject() {
				if(wrappedCacheKey.getKey() != null &&
						cache.isKeyInCache(wrappedCacheKey.getKey()) && 
							cache.get(wrappedCacheKey.getKey()) != null) {
					
					return cache.get(wrappedCacheKey.getKey()).getObjectValue();
				}else {
					return null;
				}
			}

			@Override
			public void setObject(Object object) {
				cache.put(new Element(wrappedCacheKey.getKey(), object));
			}
			
		};
	}
    
	@Override
	public boolean containsKey(Object primaryKey) {
		System.out.println("############# containsKey() : " + primaryKey);
		return cache.isKeyInCache(primaryKey);
	}

	@Override
	public Map<Object, Object> getAllFromIdentityMapWithEntityPK(Object[] pkList, ClassDescriptor descriptor,
			AbstractSession session) {
		System.out.println("############# getAllFromIdentityMapWithEntityPK()");
		return this.targetIdentityMap.getAllFromIdentityMapWithEntityPK(pkList, descriptor, session);
	}

	@Override
	public Map<Object, CacheKey> getAllCacheKeysFromIdentityMapWithEntityPK(Object[] pkList, ClassDescriptor descriptor,
			AbstractSession session) {
		System.out.println("############# getAllCacheKeysFromIdentityMapWithEntityPK()");
		return this.targetIdentityMap.getAllCacheKeysFromIdentityMapWithEntityPK(pkList, descriptor, session);
	}

	@Override
	public void release() {
		System.out.println("############# release()");
		this.targetIdentityMap.release();
	}

}
