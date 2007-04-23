package org.seasar.xwork2;

import junit.framework.TestCase;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.xwork2.S2ObjectFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * S2ObjectFactoryのテストクラス
 */
public class S2ObjectFactoryTest extends TestCase {
	private S2Container container;

	private S2ObjectFactory objectFactory;

	public void setUp() {
		container = new S2ContainerImpl();
		objectFactory = new S2ObjectFactory(container);
	}

	/**
	 * 設定したActionクラスをクラス指定で取得するテスト
	 * 
	 * @throws Exception
	 */
	public void testBuildRegisteredBeanByClass() throws Exception {
		registerTestService();
		registerTestAction();

		assertTrue(objectFactory.buildBean(TestAction.class, null) instanceof TestAction);
	}

	/**
	 * 自動設定で設定したActionクラスをクラス指定で取得するテスト
	 * 
	 * @throws Exception
	 */
	public void testBuildNonRegisteredBeanByClassAutoRegist() throws Exception {
		registerTestService();
		objectFactory.setAutoRegist(true);

		assertTrue(objectFactory.buildBean(TestAction.class, null) instanceof TestAction);
		assertEquals(1, container.getComponentDefSize());
	}

	/**
	 * 設定していないActionクラスをクラス指定で取得するテスト
	 * 
	 * @throws Exception
	 */
	public void testBuildNonRegisteredBeanByClass() throws Exception {
		registerTestService();
		objectFactory.setAutoRegist(false);

		assertTrue(objectFactory.buildBean(TestAction.class, null) instanceof TestAction);
		assertEquals(1, container.getComponentDefSize());
	}

	/**
	 * 設定したActionクラスを名前指定で取得するテスト
	 * 
	 * @throws Exception
	 */
	public void testBuildRegisteredBeanByClassName() throws Exception {
		registerTestService();
		registerTestAction();

		assertTrue(objectFactory.buildBean(
				"org.seasar.xwork2.S2ObjectFactoryTest$TestAction", null) instanceof TestAction);
	}

	/**
	 * 設定していないActionクラスを名前指定で取得するテスト
	 * 
	 * @throws Exception
	 */
	public void testBuildNonRegisteredBeanByClassName() throws Exception {
		registerTestService();

		assertTrue(objectFactory.buildBean(
				"org.seasar.xwork2.S2ObjectFactoryTest$TestAction", null) instanceof TestAction);
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

	/**
	 * テスト用アクションクラス
	 */
	public static class TestAction extends ActionSupport {
		/** TestService */
		private TestService testService;

		/**
		 * TestService設定
		 * 
		 * @param testService
		 *            TestService
		 */
		public void setTestService(TestService testService) {
			this.testService = testService;
		}
	}

	/**
	 * テスト用サービスインターフェース
	 */
	public static interface TestService {
	}

	/**
	 * テスト用サービス実装クラス
	 */
	public static class TestServiceImpl implements TestService {
	}
}
