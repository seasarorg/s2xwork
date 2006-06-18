package org.seasar.xwork.component;

import java.util.Map;

import org.seasar.framework.container.S2Container;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

/**
 * ValueStack��S2ComponentMap��ǉ����܂�
 */
public class S2ComponentInterceptor extends AroundInterceptor {
	private S2Container container;

	protected void before(ActionInvocation arg0) throws Exception {
		Map map = new S2ComponentMap(container.getRoot());
		ActionContext.getContext().getValueStack().getRoot().add(map);
	}

	protected void after(ActionInvocation arg0, String arg1) throws Exception {
	}

	public void setContainer(S2Container container) {
		this.container = container;
	}
}
