package org.mitre.cache;

import java.util.Map;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.indirection.ValueHolderInterface;
import org.eclipse.persistence.internal.identitymaps.CacheKey;
import org.eclipse.persistence.internal.identitymaps.IdentityMap;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.mappings.ForeignReferenceMapping;
import org.eclipse.persistence.sessions.interceptors.CacheInterceptor;
import org.eclipse.persistence.sessions.interceptors.CacheKeyInterceptor;
import org.mitre.oauth2.model.AuthenticationHolderEntity;
import org.mitre.oauth2.model.ClientDetailsEntity;
import org.mitre.oauth2.model.OAuth2RefreshTokenEntity;
import org.mitre.oauth2.model.SavedUserAuthentication;
import org.mitre.oauth2.model.SystemScope;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheCacheInterceptor extends CacheInterceptor {

	private static final String EHCACHE_MANAGER = "ehCacheManager";
	
	private Cache cache;

	public EhcacheCacheInterceptor(IdentityMap targetIdentityMap, AbstractSession interceptedSession) {
		super(targetIdentityMap, interceptedSession);
		cache = new Cache(targetIdentityMap.getDescriptorClass().getSimpleName() + "CacheById", 500, false, false, 3600, 900);
		BeanUtil.getBean(EHCACHE_MANAGER, CacheManager.class).addCache(cache);
		System.out.println("###################################################### create cache : " + cache.getName());
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
				if(wrappedCacheKey.getKey() != null) {
					return cache.get(wrappedCacheKey.getKey());
				}else {
					return null;
				}
			}

			@Override
			public void setObject(Object object) {
				boolean isNull = true;
				
				if(object != null) {
					if(object instanceof ClientDetailsEntity) {
						isNull = ((ClientDetailsEntity) object).getId() == null && wrappedCacheKey.getKey() != null;
					}else if(object instanceof SystemScope) {
						isNull = ((SystemScope) object).getId() == null && wrappedCacheKey.getKey() != null;
					}else if(object instanceof SavedUserAuthentication) {
						isNull = ((SavedUserAuthentication) object).getId() == null && wrappedCacheKey.getKey() != null;
					}else if(object instanceof AuthenticationHolderEntity) {
						isNull = ((AuthenticationHolderEntity) object).getId() == null && wrappedCacheKey.getKey() != null;
					}else if(object instanceof OAuth2RefreshTokenEntity) {
						isNull = ((OAuth2RefreshTokenEntity) object).getId() == null && wrappedCacheKey.getKey() != null;
					}
				}
				
				if(!isNull) {
					cache.put(new Element(wrappedCacheKey.getKey(), object));
				}
			}
			
		};
	}
	
	/**
     * Store the object in the cache at its primary key.
     * This is used by InsertObjectQuery, typically into the UnitOfWork identity map.
     * Merge and reads do not use put, but acquireLock.
     * Also an advanced (very) user API.
     * @param primaryKey is the primary key for the object.
     * @param object is the domain object to cache.
     * @param writeLockValue is the current write lock value of object, if null the version is ignored.
     */
	@Override
    public CacheKey put(Object primaryKey, Object object, Object writeLockValue, long readTime) {
        return this.targetIdentityMap.put(primaryKey, object, writeLockValue, readTime);
    }
	
    /**
     * Notify the cache that a lazy relationship has been triggered in the object
     * and the cache may need to be updated
     */
    public void lazyRelationshipLoaded(Object rootEntity, ValueHolderInterface valueHolder, ForeignReferenceMapping mapping){
        this.targetIdentityMap.lazyRelationshipLoaded(rootEntity, valueHolder, mapping);
    }

	 /**
     * Get the cache key (with object) for the primary key.
     */
	@Override
    public CacheKey getCacheKey(Object primaryKey, boolean forMerge) {
        return this.targetIdentityMap.getCacheKey(primaryKey, forMerge);
    }

    /**
     * Get the cache key (with object) for the primary key.
     */
	@Override
    public CacheKey getCacheKeyForLock(Object primaryKey) {
        return this.targetIdentityMap.getCacheKeyForLock(primaryKey);
    }
    
	@Override
	public boolean containsKey(Object primaryKey) {
		return cache.isKeyInCache(primaryKey);
	}

	@Override
	public Map<Object, Object> getAllFromIdentityMapWithEntityPK(Object[] pkList, ClassDescriptor descriptor,
			AbstractSession session) {
		return null;
	}

	@Override
	public Map<Object, CacheKey> getAllCacheKeysFromIdentityMapWithEntityPK(Object[] pkList, ClassDescriptor descriptor,
			AbstractSession session) {
		return null;
	}

	@Override
	public void release() {
	}

}
