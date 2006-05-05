package org.seasar.xwork.component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.seasar.framework.container.S2Container;

public class S2ComponentMap implements Map {
	private S2Container container;

	public S2ComponentMap(S2Container container) {
		this.container = container;
	}

	public int size() {
		return 0;
	}

	public void clear() {
	}

	public boolean isEmpty() {
		return false;
	}

	public boolean containsKey(Object key) {
		return container.hasComponentDef(key);
	}

	public boolean containsValue(Object value) {
		return false;
	}

	public Collection values() {
		return null;
	}

	public void putAll(Map arg0) {
	}

	public Set entrySet() {
		return null;
	}

	public Set keySet() {
		return null;
	}

	public Object get(Object key) {
		return container.getComponent(key);
	}

	public Object remove(Object arg0) {
		return null;
	}

	public Object put(Object key, Object value) {
		return null;
	}
}
