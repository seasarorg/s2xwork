package org.seasar.xwork.annotation;

import junit.framework.TestCase;

import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.entities.InterceptorMapping;
import com.opensymphony.xwork.config.providers.XmlConfigurationProvider;
import com.opensymphony.xwork.interceptor.LoggingInterceptor;

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
		assertEquals(1,proxy.getConfig().getInterceptors().size());
		InterceptorMapping interceptorMapping = (InterceptorMapping)proxy.getConfig().getInterceptors().get(0);
		assertTrue(interceptorMapping.getInterceptor() instanceof LoggingInterceptor);
	}
	@XWorkAction(name = "annotation_test", interceptorRef = { @InterceptorRef(name="log") })
	public static class TestAction extends ActionSupport {
		public String execute() {
			return SUCCESS;
		}
	}
}
