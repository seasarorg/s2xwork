package org.seasar.xwork;

import java.util.Map;

import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.InterceptorConfig;
import com.opensymphony.xwork.config.entities.ResultConfig;
import com.opensymphony.xwork.interceptor.Interceptor;
import com.opensymphony.xwork.validator.Validator;

/**
 * ObjectFactory.getObjectFactory()Ç≈éÊìæÇµÇΩ
 * ObjectFactoryÇ…ëSÇƒÇÃèàóùÇàœè˜Ç∑ÇÈObjectFactoryÇ≈Ç∑ÅB
 * 
 * @see ObjectFactory.getObjectFactory()
 * 
 */
public class DelegateObjectFactory extends ObjectFactory {
	private ObjectFactory objectFactory;

	public DelegateObjectFactory() {
		objectFactory = ObjectFactory.getObjectFactory();
	}

	public Object buildAction(String arg0, String arg1, ActionConfig arg2,
			Map arg3) throws Exception {
		return objectFactory.buildAction(arg0, arg1, arg2, arg3);
	}

	public Object buildBean(Class arg0, Map arg1) throws Exception {
		return objectFactory.buildBean(arg0, arg1);
	}

	public Object buildBean(String arg0, Map arg1) throws Exception {
		return objectFactory.buildBean(arg0, arg1);
	}

	public Interceptor buildInterceptor(InterceptorConfig arg0, Map arg1)
			throws ConfigurationException {
		return objectFactory.buildInterceptor(arg0, arg1);
	}

	public Result buildResult(ResultConfig arg0, Map arg1) throws Exception {
		return objectFactory.buildResult(arg0, arg1);
	}

	public Validator buildValidator(String arg0, Map arg1, Map arg2)
			throws Exception {
		return objectFactory.buildValidator(arg0, arg1, arg2);
	}

	public boolean equals(Object obj) {
		return objectFactory.equals(obj);
	}

	public Class getClassInstance(String arg0) throws ClassNotFoundException {
		return objectFactory.getClassInstance(arg0);
	}

	public int hashCode() {
		return objectFactory.hashCode();
	}

	public boolean isNoArgConstructorRequired() {
		return objectFactory.isNoArgConstructorRequired();
	}

	public String toString() {
		return objectFactory.toString();
	}
}
