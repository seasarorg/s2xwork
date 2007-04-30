package org.seasar.xwork.clwork2.result;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

/**
 * コマンドライン用Result
 */
public class CommandLineResult implements Result {
	/** 結果コード */
	private int resultCode;

	/**
	 * 結果コードを返し終了
	 * 
	 * @param invocation
	 *            ActionInvocation
	 */
	public void execute(ActionInvocation invocation) {
		System.exit(resultCode);
	}

	/**
	 * resultCode を設定。
	 * 
	 * @param resultCode
	 *            resultCode を設定。
	 */
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * resultCode を取得。
	 * 
	 * @return resultCode
	 */
	public int getResultCode() {
		return resultCode;
	}

}