package org.seasar.xwork;

import com.opensymphony.xwork.ObjectFactory;
import org.seasar.framework.container.S2Container;

public class XWorkConfigurer {

	public void init(S2Container container) {
		ObjectFactory objectFactory = new S2ObjectFactory(container.getRoot());
		ObjectFactory.setObjectFactory(objectFactory);
	}
}
