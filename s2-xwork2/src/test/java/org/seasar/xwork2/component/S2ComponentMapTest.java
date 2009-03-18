package org.seasar.xwork2.component;

import java.util.List;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.PropertyDefImpl;
import org.seasar.xwork2.S2ObjectFactory;
import org.seasar.xwork2.S2ObjectFactoryTest.TestService;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.XWorkTestCase;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;

/**
 * S2ComponentMapのテストクラス
 */
public class S2ComponentMapTest extends XWorkTestCase {
	private S2Container container;

	protected void setUp() throws Exception {
		SingletonS2ContainerFactory.init();
		container = SingletonS2ContainerFactory.getContainer();

		super.setUp();
		XmlConfigurationProvider c = new XmlConfigurationProvider();
		c.setObjectFactory(new S2ObjectFactory());
		loadConfigurationProviders(new ConfigurationProvider[] { c });
	}

	/**
	 * 値取得のテスト
	 * 
	 * @throws Exception
	 */
	public void testFindValue() throws Exception {
		ComponentDef componentDef = new ComponentDefImpl(TestDto.class);
		componentDef.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		componentDef.setComponentName("testDto");
		PropertyDef propertyDef = new PropertyDefImpl("value", "test value");
		componentDef.addPropertyDef(propertyDef);
		container.register(componentDef);
		componentDef = new ComponentDefImpl(S2ComponentMap.class);
		container.register(componentDef);
		componentDef = new ComponentDefImpl(TestAction.class);
		componentDef.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		container.register(componentDef);
		componentDef = new ComponentDefImpl(S2ComponentInterceptor.class);
		componentDef.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		container.register(componentDef);

		ActionProxy proxy = actionProxyFactory.createActionProxy("", "test",
				null);
		proxy.execute();

		assertEquals(proxy.getInvocation().getStack().findString(
				"testDto.value"), "test value");
	}

	/**
	 * テスト用Dtoクラス
	 */
	public static class TestDto {
		private String value;

		/**
		 * 値取得
		 * 
		 * @return 値
		 */
		public String getValue() {
			return value;
		}

		/**
		 * 値設定
		 * 
		 * @param value
		 *            値
		 */
		public void setValue(String value) {
			this.value = value;
		}

	}

	/**
	 * テスト用アクション
	 */
	public static class TestAction extends ActionSupport {
		public String execute() {
			return SUCCESS;
		}
	}
}
