package org.seasar.xwork;

import java.util.Map;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.impl.ComponentDefImpl;

import com.opensymphony.xwork.ObjectFactory;

/**
 * S2Container����R���|�[�l���g���擾����ObjectFactory
 */
/**
 * @author matoba
 * 
 */
public class S2ObjectFactory extends ObjectFactory {
	/** S2Container */
	protected final S2Container container;

	/** �I�u�W�F�N�g���R���e�i�ɓo�^����Ă��Ȃ��ꍇ�����o�^���� */
	private boolean autoRegist;

	/**
	 * �g�p����S2Container���󂯎��
	 */
	public S2ObjectFactory(S2Container container) {
		this.container = container.getRoot();
	}

	/**
	 * �R���|�[�l���g�𐶐�����B
	 * 
	 * @see com.opensymphony.xwork.ObjectFactory#buildBean(java.lang.Class,
	 *      java.util.Map)
	 */
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

	/**
	 * �R���|�[�l���g�𐶐�����B
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

	public boolean isAutoRegist() {
		return autoRegist;
	}

	public void setAutoRegist(boolean autoRegist) {
		this.autoRegist = autoRegist;
	}
}
