package org.seasar.xwork;

import junit.framework.TestCase;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.S2ContainerImpl;

import com.opensymphony.xwork.ActionSupport;

public class S2ObjectFactoryTest extends TestCase {
	private S2Container container;

	private S2ObjectFactory objectFactory;

	public void setUp() {
		container = new S2ContainerImpl();
		objectFactory = new S2ObjectFactory(container);
	}

	public void testBuildRegisteredBeanByClass() throws Exception {
		registerTestService();
		registerTestAction();

		assertTrue(objectFactory.buildBean(TestAction.class, null) instanceof TestAction);
	}

	public void testBuildNonRegisteredBeanByClassAutoRegist() throws Exception {
		registerTestService();
		objectFactory.setAutoRegist(true);

		assertTrue(objectFactory.buildBean(TestAction.class, null) instanceof TestAction);
		assertEquals(1, container.getComponentDefSize());
	}

	public void testBuildNonRegisteredBeanByClass() throws Exception {
		registerTestService();

		assertTrue(objectFactory.buildBean(TestAction.class, null) instanceof TestAction);
		assertEquals(1, container.getComponentDefSize());
	}

	public void testBuildRegisteredBeanByClassName() throws Exception {
		registerTestService();
		registerTestAction();

		assertTrue(objectFactory.buildBean(
				"org.seasar.xwork.S2ObjectFactoryTest$TestAction", null) instanceof TestAction);
	}

	public void testBuildNonRegisteredBeanByClassName() throws Exception {
		registerTestService();

		assertTrue(objectFactory.buildBean(
				"org.seasar.xwork.S2ObjectFactoryTest$TestAction", null) instanceof TestAction);
		assertEquals(1, container.getComponentDefSize());
	}

	private void registerTestService() {
		container.register(TestServiceImpl.class);
	}

	private void registerTestAction() {
		ComponentDef componentDef = new ComponentDefImpl(TestAction.class);
		componentDef.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		container.register(componentDef);
	}

	public static class TestAction extends ActionSupport {
		private TestService testService;

		public void setTestService(TestService testService) {
			this.testService = testService;
		}
	}

	public static interface TestService {
	}

	public static class TestServiceImpl implements TestService {
	}
}
