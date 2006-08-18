package org.seasar.xwork.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ExternalRef設定
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ExternalRef {
	/**
	 * 名前
	 * 
	 * @return 名前
	 */
	String name() default "";

	/**
	 * ExternalRef名
	 * 
	 * @return ExternalRef名
	 */
	String externalRef();

	/**
	 * 必須かどうか
	 * 
	 * @return 必須かどうか
	 */
	boolean required() default true;
}
