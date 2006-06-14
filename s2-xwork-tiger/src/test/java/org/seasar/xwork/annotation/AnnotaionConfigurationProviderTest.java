package org.seasar.xwork.annotation;

import junit.framework.TestCase;

import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.providers.XmlConfigurationProvider;

public class AnnotaionConfigurationProviderTest extends TestCase {
	protected void setUp() throws Exception {
	}

	public void testAnnotation() throws Exception {
		ConfigurationManager
				.addConfigurationProvider(new XmlConfigurationProvider());
		ConfigurationManager
				.addConfigurationProvider(new AnnotationConfigurationProvider());
		ConfigurationManager.getConfiguration();

		ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(
				"default", "annotation_test", null);
		assertTrue(proxy.getAction() instanceof TestAction);
	}

	@XWorkAction(name = "annotation_test")
	public static class TestAction extends ActionSupport {
		public String execute() {
			return SUCCESS;
		}
	}
}
