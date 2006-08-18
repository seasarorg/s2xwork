package org.seasar.xwork.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * アクション設定
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface XWorkAction {
	/**
	 * 名前
	 * 
	 * @return 名前
	 */
	String name();

	/**
	 * メソッド名
	 * 
	 * @return メソッド名
	 */
	String method() default "execute";

	/**
	 * コンバータ
	 * 
	 * @return コンバータ
	 */
	String converter() default "";

	/**
	 * Result設定
	 * 
	 * @return Result設定
	 */
	Result[] result() default {};

	/**
	 * パラメータ
	 * 
	 * @return パラメータ
	 */
	Param[] param() default {};

	/**
	 * InterceptorRef設定
	 * 
	 * @return InterceptorRef設定
	 */
	InterceptorRef[] interceptorRef() default {};

	/**
	 * ExternalRef設定
	 * 
	 * @return ExternalRef設定
	 */
	ExternalRef[] externalRef() default {};

	/**
	 * ExceptionMapping設定
	 * 
	 * @return ExceptionMapping設定
	 */
	ExceptionMapping[] exceptionMapping() default {};
}
