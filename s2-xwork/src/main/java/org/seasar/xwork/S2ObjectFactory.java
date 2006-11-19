package org.seasar.xwork;

import java.util.Map;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;

import com.opensymphony.xwork.ObjectFactory;

/**
 * S2Containerからコンポーネントを取得するObjectFactory
 */
public class S2ObjectFactory extends ObjectFactory {
	/** S2Container */
	protected S2Container container;

	/** オブジェクトがコンテナに登録されていない場合自動登録する */
	private boolean autoRegist;

	/**
	 * SingletonS2ContainerFactoryからS2Containerを取得するコンストラクタ。
	 */
	public S2ObjectFactory() {
	}

	/**
	 * 使用するS2Containerを受け取るコンストラクタ。
	 * 
	 * @param container
	 *            S2Container
	 */
	public S2ObjectFactory(S2Container container) {
		this.container = container.getRoot();
	}

	/**
	 * コンポーネントを生成する。
	 * 
	 * @see com.opensymphony.xwork.ObjectFactory#buildBean(java.lang.Class,
	 *      java.util.Map)
	 */
	public Object buildBean(Class clazz, Map extraContext) throws Exception {
		if (container == null && SingletonS2ContainerFactory.hasContainer()) {
			this.container = SingletonS2ContainerFactory.getContainer();
		}
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

	/**
	 * コンポーネントを生成する。
	 * 
	 * @see com.opensymphony.xwork.ObjectFactory#buildBean(java.lang.String,
	 *      java.util.Map)
	 */
	public Object buildBean(String className, Map extraContext)
			throws Exception {
		Class clazz = getClassInstance(className);
		return buildBean(clazz, extraContext);
	}

	/**
	 * @see com.opensymphony.xwork.ObjectFactory#isNoArgConstructorRequired()
	 */
	public boolean isNoArgConstructorRequired() {
		return false;
	}

	/**
	 * 自動登録するかどうか
	 * 
	 * @return trueの場合自動登録する、falseの場合自動登録しない
	 */
	public boolean isAutoRegist() {
		return autoRegist;
	}

	/**
	 * 自動登録するかどうか
	 * 
	 * @param autoRegist
	 *            trueの場合自動登録する、falseの場合自動登録しない
	 */
	public void setAutoRegist(boolean autoRegist) {
		this.autoRegist = autoRegist;
	}
}
