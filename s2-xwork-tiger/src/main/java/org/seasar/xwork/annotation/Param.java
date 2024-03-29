package org.seasar.xwork.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * パラメータ設定
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Param {
	/**
	 * 名前
	 * 
	 * @return 名前
	 */
	String name();

	/**
	 * 値
	 * 
	 * @return 値
	 */
	String value();
}
