package org.mitre.cache;

import java.util.Map;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.identitymaps.CacheKey;
import org.eclipse.persistence.internal.identitymaps.IdentityMap;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sessions.interceptors.CacheInterceptor;
import org.eclipse.persistence.sessions.interceptors.CacheKeyInterceptor;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class EhcacheCacheInterceptor extends CacheInterceptor {

	private Cache cache;

	public EhcacheCacheInterceptor(IdentityMap targetIdentityMap, AbstractSession interceptedSession) {
		super(targetIdentityMap, interceptedSession);
		cache = EhcacheUtil.createCache(targetIdentityMap.getDescriptorClass().getSimpleName() + "CacheById");
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
