package org.seasar.xwork.component;

import junit.framework.TestCase;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.PropertyDefImpl;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.xwork.S2ObjectFactory;

import com.opensymphony.xwork.util.OgnlValueStack;

public class S2ComponentMapTest extends TestCase {
	private S2Container container;

	private S2ObjectFactory objectFactory;

	protected void setUp() throws Exception {
		container = new S2ContainerImpl();
		objectFactory = new S2ObjectFactory(container);
	}

	public void testFindValue() throws Exception {
		ComponentDef componentDef = new ComponentDefImpl(TestDto.class);
		componentDef.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		componentDef.setComponentName("testDto");
		PropertyDef propertyDef = new PropertyDefImpl("value", "test value");
		componentDef.addPropertyDef(propertyDef);
		container.register(componentDef);
		componentDef = new ComponentDefImpl(S2ComponentMap.class);
		container.register(componentDef);

		OgnlValueStack valueStack = new OgnlValueStack();
		valueStack.getRoot().add(container.getComponent(S2ComponentMap.class));
		assertEquals(valueStack.findString("testDto.value"), "test value");
	}

	public static class TestDto {
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}
