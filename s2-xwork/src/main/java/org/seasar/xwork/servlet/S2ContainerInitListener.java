package org.seasar.xwork.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.StringUtil;

public class S2ContainerInitListener implements ServletContextListener {
	public static final String CONFIG_PATH_KEY = "configPath";

	private static S2ContainerInitListener INSTANCE;

	public S2ContainerInitListener() {
		INSTANCE = this;
	}

	public static S2ContainerInitListener getInstance() {
		return INSTANCE;
	}

	public void contextInitialized(ServletContextEvent event) {
		String configPath = null;
		configPath = event.getServletContext()
				.getInitParameter(CONFIG_PATH_KEY);
		if (!StringUtil.isEmpty(configPath))
			SingletonS2ContainerFactory.setConfigPath(configPath);
		SingletonS2ContainerFactory
				.setServletContext(event.getServletContext());
		SingletonS2ContainerFactory.init();
	}

	public void contextDestroyed(ServletContextEvent event) {
		SingletonS2ContainerFactory.destroy();
	}

	public static S2Container getContainer() {
		return SingletonS2ContainerFactory.getContainer();
	}

}
