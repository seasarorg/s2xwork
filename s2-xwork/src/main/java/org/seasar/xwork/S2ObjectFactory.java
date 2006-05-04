package org.seasar.xwork;

import java.util.Map;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.impl.ComponentDefImpl;

import com.opensymphony.xwork.ObjectFactory;

public class S2ObjectFactory extends ObjectFactory {
	protected final S2Container container;

	/** オブジェクトがコンテナに登録されていない場合自動登録する */
	private boolean autoRegist;

	protected S2ObjectFactory(S2Container container) {
		this.container = container;
	}

	public Object buildBean(Class clazz, Map extraContext) throws Exception {
		if (container.hasComponentDef(clazz)) {
			return container.getComponent(clazz);
		} else if (autoRegist) {
			ComponentDef componentDef = new ComponentDefImpl(clazz);
			componentDef.setContainer(container);
			return componentDef.getComponent();
		} else {
			return super.buildBean(clazz, extraContext);
		}
	}

	public Object buildBean(String className, Map extraContext)
			throws Exception {
		Class clazz = getClassInstance(className);
		return buildBean(clazz, extraContext);
	}

	public boolean isAutoRegist() {
		return autoRegist;
	}

	public void setAutoRegist(boolean autoRegist) {
		this.autoRegist = autoRegist;
	}
}
