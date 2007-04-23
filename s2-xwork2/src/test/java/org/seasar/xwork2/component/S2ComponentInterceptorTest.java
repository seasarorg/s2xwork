package org.seasar.xwork2.component;

import java.util.Iterator;
import java.util.List;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.xwork2.S2ObjectFactory;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionProxyFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.XWorkTestCase;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;

/**
 * S2ComponentInterceptorのテストクラス
 */
public class S2ComponentInterceptorTest extends XWorkTestCase {
	private S2Container s2container;

	protected void setUp() throws Exception {
		ObjectFactory.setObjectFactory(new S2ObjectFactory());
		s2container = SingletonS2ContainerFactory.getContainer();

		super.setUp();
		XmlConfigurationProvider c = new XmlConfigurationProvider();
		configurationManager.addConfigurationProvider(c);
		configurationManager.reload();
	}

	/**
	 * インターセプター設定のテスト
	 * 
	 * @throws Exception
	 */
	public void testInterceptor() throws Exception {
		ComponentDef componentDef = new ComponentDefImpl(TestAction.class);
		componentDef.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		s2container.register(componentDef);
		componentDef = new ComponentDefImpl(S2ComponentInterceptor.class);
		componentDef.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		s2container.register(componentDef);

		ActionProxy proxy = actionProxyFactory.createActionProxy("", "test",
				null);
		proxy.execute();

		List context = proxy.getInvocation().getStack().getRoot();
		Iterator iter = context.iterator();
		while (iter.hasNext()) {
			Object entry = iter.next();
			if (entry instanceof S2ComponentMap) {
				assertTrue(true);
				return;
			}
		}
		assertTrue(false);
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
