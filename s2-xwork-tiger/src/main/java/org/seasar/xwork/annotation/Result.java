package org.seasar.xwork.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Result設定
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Result {
	/**
	 * 名前
	 * 
	 * @return 名前
	 */
	String name() default "success";

	/**
	 * タイプ
	 * 
	 * @return タイプ
	 */
	String type();

	/**
	 * パラメータ
	 * 
	 * @return パラメータ
	 */
	Param[] param() default {};
}
