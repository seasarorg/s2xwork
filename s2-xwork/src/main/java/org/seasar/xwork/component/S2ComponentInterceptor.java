package org.seasar.xwork.component;

import java.util.Map;

import org.seasar.framework.container.S2Container;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

/**
 * ValueStackにS2ComponentMapを追加します
 */
public class S2ComponentInterceptor implements Interceptor {
	/** S2Container */
	private S2Container container;

	/**
	 * S2ComponentMapをValueStackに追加します。
	 * 
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		Map map = new S2ComponentMap(container.getRoot());
		ActionContext.getContext().getValueStack().getRoot().add(map);
		return invocation.invoke();
	}

	/**
	 * 初期化を行います。
	 */
	public void init() {
	}

	/**
	 * 終了処理を行います。
	 */
	public void destroy() {
	}

	/**
	 * S2Containerを設定します。
	 * 
	 * @param container
	 *            S2Container
	 */
	public void setContainer(S2Container container) {
		this.container = container;
	}
}
