package org.seasar.xwork.component;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.xwork.S2ObjectFactory;

import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.ObjectFactory;

public class S2ComponentInterceptorTest extends TestCase {
	private S2Container container;

	protected void setUp() throws Exception { 
		container = new S2ContainerImpl();
		ObjectFactory.setObjectFactory(new S2ObjectFactory(container));
	}

	public void testInterceptor() throws Exception {
		ComponentDef componentDef = new ComponentDefImpl(TestAction.class);
		componentDef.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		container.register(componentDef);
		componentDef = new ComponentDefImpl(S2ComponentInterceptor.class);
		componentDef.setInstanceDef(InstanceDefFactory.PROTOTYPE);
		container.register(componentDef);

		ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(
				"", "test", null);
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

	public static class TestAction extends ActionSupport {
		public String execute() {
			return SUCCESS;
		}
	}

}
