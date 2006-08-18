package org.seasar.xwork.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ExceptionMapping設定
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ExceptionMapping {
	/**
	 * 名前
	 * 
	 * @return 名前
	 */
	String name();

	/**
	 * 例外クラス名
	 * 
	 * @return 例外クラス名
	 */
	String exception();

	/**
	 * MappingするResult名
	 * 
	 * @return MappingするResult名
	 */
	String result();

	/**
	 * パラメータ
	 * 
	 * @return パラメータ
	 */
	Param[] param() default {};
}
