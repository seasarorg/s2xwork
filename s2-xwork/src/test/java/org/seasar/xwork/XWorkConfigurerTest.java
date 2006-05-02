package org.seasar.xwork;

import junit.framework.TestCase;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.impl.S2ContainerImpl;

import com.opensymphony.xwork.ObjectFactory;

public class XWorkConfigurerTest extends TestCase {

	public void testInit() throws Exception {
		S2Container childContainer = new S2ContainerImpl();
		childContainer.register("childContainer");
		S2Container parentContainer = new S2ContainerImpl();
		parentContainer.register("parentContainer");
		parentContainer.include(childContainer);

		XWorkConfigurer xworkConfigurer = new XWorkConfigurer();
		xworkConfigurer.init(childContainer);

		assertTrue(ObjectFactory.getObjectFactory() instanceof S2ObjectFactory);
		assertEquals("parentContainer", ObjectFactory.getObjectFactory()
				.buildBean(String.class, null));
	}
}
