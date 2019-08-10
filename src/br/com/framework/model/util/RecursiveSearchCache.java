package br.com.framework.model.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.framework.model.AbstractEntidade;
import br.com.framework.util.ValidatorUtil;

public class RecursiveSearchCache {

	Map<Class<? extends AbstractEntidade>, Set<AbstractEntidade>> cache;

	public RecursiveSearchCache() {
		cache = new HashMap<Class<? extends AbstractEntidade>, Set<AbstractEntidade>>();
	}

	/**
	 * Insere o objeto na Cache.
	 * @param clazz
	 * @param obj
	 */
	public void set(AbstractEntidade obj) {
		Class<? extends AbstractEntidade> clazz = obj.getClass();
		if (!cache.containsKey(clazz)) {
			cache.put(clazz, new HashSet<AbstractEntidade>());
		}
		Set<AbstractEntidade> cacheByType = cache.get(clazz);
		cacheByType.add(obj);
	}

	/**
	 * Verifica se objeto está na cache.
	 * @param obj
	 * @return
	 */
	public boolean contains(AbstractEntidade obj) {
		Class<? extends AbstractEntidade> clazz = obj.getClass();
		Set<AbstractEntidade> cacheByType = cache.get(clazz);
		if (ValidatorUtil.isNotEmpty(cacheByType)) {
			return cacheByType.contains(obj);
		}
		return false;
	}
	/**
	 * Verifica se objeto com <i>id</i> está na cache.
	 * @param clazz
	 * @param id
	 * @return
	 */
	public boolean contains(Class<? extends AbstractEntidade> clazz, Integer id) {
		Set<AbstractEntidade> cacheByType = cache.get(clazz);
		if (ValidatorUtil.isNotEmpty(cacheByType)) {
			for (AbstractEntidade obj : cacheByType) {
				if (id.equals(obj.getId())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Obtém objeto da cache.
	 * @param clazz
	 * @param id
	 * @return
	 */
	public AbstractEntidade get(Class<? extends AbstractEntidade> clazz, Integer id) {
		Set<AbstractEntidade> cacheByType = cache.get(clazz);
		if (ValidatorUtil.isNotEmpty(cacheByType)) {
			for (AbstractEntidade obj : cacheByType) {
				if (id.equals(obj.getId())) {
					return obj;
				}
			}
		}
		return null;
	}
}
