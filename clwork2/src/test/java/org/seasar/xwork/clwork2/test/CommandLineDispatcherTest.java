package org.seasar.xwork.clwork2.test;

import junit.framework.TestCase;

import org.seasar.xwork.clwork2.CommandLineDispatcher;
import org.seasar.xwork.clwork2.result.CommandLineResult;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;

/**
 * CommandLineDispatcherのテストを行う。 <br />
 * 
 * @see org.seasar.xwork.clwork2.CommandLineDispatcher
 */
public class CommandLineDispatcherTest extends TestCase {
	/**
	 * パラメータをつけないで実行すると処理失敗
	 * 
	 * @throws Exception
	 */
	public void testDispatch() throws Exception {
		CommandLineDispatcher dispatcher = new CommandLineDispatcher();
		String result = dispatcher.dispatch("test", new String[0]);

		assertEquals(result, Action.ERROR);
	}

	/**
	 * パラメータtestをつけると処理成功
	 * 
	 * @throws Exception
	 */
	public void testDispatchWithParameter() throws Exception {
		CommandLineDispatcher dispatcher = new CommandLineDispatcher();
		String result = dispatcher.dispatch("test", new String[] { "-test",
				"test2" });

		assertEquals(result, Action.SUCCESS);
	}

	/**
	 * パラメータをつけないで実行すると処理失敗(設定ファイル名を指定)
	 * 
	 * @throws Exception
	 */
	public void testDispatchSetConfigFile() throws Exception {
		CommandLineDispatcher dispatcher = new CommandLineDispatcher();
		String result = dispatcher.dispatch("test", new String[0]);
		assertEquals(result, Action.ERROR);
	}

	public static class TestAction extends ActionSupport {
		private String test;

		public String execute() {
			if (test != null) {
				return Action.SUCCESS;
			}
			return Action.ERROR;
		}

		/**
		 * test を設定。
		 * 
		 * @param test
		 *            test を設定。
		 */
		public void setTest(String test) {
			this.test = test;
		}
	}

	public static class CommandLineMockResult extends CommandLineResult {
		public void execute(ActionInvocation invocation) {
		}
	}
}
