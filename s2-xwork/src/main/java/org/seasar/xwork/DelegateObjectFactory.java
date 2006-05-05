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
 * ObjectFactory#getObjectFactory()Ç≈éÊìæÇµÇΩ
 * ObjectFactoryÇ…ëSÇƒÇÃèàóùÇàœè˜Ç∑ÇÈObjectFactoryÇ≈Ç∑ÅB
 * 
 * @see ObjectFactory.getObjectFactory()
 * 
 */
public class DelegateObjectFactory extends ObjectFactory {
	/** èàóùÇàœè˜Ç∑ÇÈObjectFactory */
	private ObjectFactory objectFactory;

	/**
	 * ObjectFactory#getObjectFactory()Ç≈àœè˜Ç∑ÇÈObjectFactoryÇéÊìæÇµÇ‹Ç∑ÅB
	 */
	public DelegateObjectFactory() {
		objectFactory = ObjectFactory.getObjectFactory();
	}

	/**
	 * @see com.opensymphony.xwork.ObjectFactory#buildAction(java.lang.String,
	 *      java.lang.String,
	 *      com.opensymphony.xwork.config.entities.ActionConfig, java.util.Map)
	 */
	public Object buildAction(String arg0, String arg1, ActionConfig arg2,
			Map arg3) throws Exception {
		return objectFactory.buildAction(arg0, arg1, arg2, arg3);
	}

	/**
	 * @see com.opensymphony.xwork.ObjectFactory#buildBean(java.lang.Class,
	 *      java.util.Map)
	 */
	public Object buildBean(Class arg0, Map arg1) throws Exception {
		return objectFactory.buildBean(arg0, arg1);
	}

	/**
	 * @see com.opensymphony.xwork.ObjectFactory#buildBean(java.lang.String,
	 *      java.util.Map)
	 */
	public Object buildBean(String arg0, Map arg1) throws Exception {
		return objectFactory.buildBean(arg0, arg1);
	}

	/**
	 * @see com.opensymphony.xwork.ObjectFactory#buildInterceptor(com.opensymphony.xwork.config.entities.InterceptorConfig,
	 *      java.util.Map)
	 */
	public Interceptor buildInterceptor(InterceptorConfig arg0, Map arg1)
			throws ConfigurationException {
		return objectFactory.buildInterceptor(arg0, arg1);
	}

	/**
	 * @see com.opensymphony.xwork.ObjectFactory#buildResult(com.opensymphony.xwork.config.entities.ResultConfig,
	 *      java.util.Map)
	 */
	public Result buildResult(ResultConfig arg0, Map arg1) throws Exception {
		return objectFactory.buildResult(arg0, arg1);
	}

	/**
	 * @see com.opensymphony.xwork.ObjectFactory#buildValidator(java.lang.String,
	 *      java.util.Map, java.util.Map)
	 */
	public Validator buildValidator(String arg0, Map arg1, Map arg2)
			throws Exception {
		return objectFactory.buildValidator(arg0, arg1, arg2);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return objectFactory.equals(obj);
	}

	/**
	 * @see com.opensymphony.xwork.ObjectFactory#getClassInstance(java.lang.String)
	 */
	public Class getClassInstance(String arg0) throws ClassNotFoundException {
		return objectFactory.getClassInstance(arg0);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return objectFactory.hashCode();
	}

	/**
	 * @see com.opensymphony.xwork.ObjectFactory#isNoArgConstructorRequired()
	 */
	public boolean isNoArgConstructorRequired() {
		return objectFactory.isNoArgConstructorRequired();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return objectFactory.toString();
	}
}
