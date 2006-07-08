package org.seasar.xwork.clwork;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;

import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;

/**
 * xworkアクションを実行する。 <br>
 * 基本的にmainメソッドから呼び出されることを想定している。
 */
public class CommandLineDispatcher {
	/** コマンドライン引数を格納するキー */
	public static final String CMD = "CommandLineDispatcher.CMD";

	/** コマンドラインパーサー */
	private CommandLineParser parser;

	/** デフォルトのコマンドラインパーサー */
	public static final String DEFAULT_PARSER = "org.apache.commons.cli.GnuParser";

	/**
	 * 指定されたActionを実行する。
	 * 
	 * @param actionName
	 *            アクション名
	 * @param args
	 *            コマンドライン引数
	 * @throws Exception
	 *             例外が発生した場合
	 */
	public String dispatch(String actionName, String[] args) throws Exception {
		// parserが指定されなかった場合、デフォルトのparserを使用
		if (parser == null) {
			parser = (CommandLineParser) Class.forName(DEFAULT_PARSER)
					.newInstance();
		}

		// Actionの取り出し
		ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(
				"", actionName, null);

		// コマンドラインパラメータの解析
		parseParameter(args, proxy);

		// Actionの実行
		return proxy.execute();
	}

	/**
	 * パラメータの解析を行う。
	 * 
	 * @param args
	 *            コマンドライン引数
	 * @param proxy
	 *            ActionProxy
	 * @throws ParseException
	 *             解析に失敗した場合
	 */
	private void parseParameter(String[] args, ActionProxy proxy)
			throws ParseException {

		// コマンドラインパラメータを解析
		Options options = new AllowAllOptions();
		CommandLine cmd = parser.parse(options, args);

		// コマンドラインパラメータをparametersに格納
		// この後ParameterInterceptorを呼び出せば、
		// Actionのプロパティに同名のパラメータがセットされる
		Map parameters = new HashMap();
		Option[] optionList = cmd.getOptions();
		for (int i = 0; i < optionList.length; i++) {
			String parameterName = optionList[i].getOpt();
			String parameterValue = optionList[i].getValue();
			if (parameterValue != null) {
				parameters.put(parameterName, parameterValue);
			}
		}
		proxy.getInvocation().getInvocationContext().setParameters(parameters);
		proxy.getInvocation().getInvocationContext().put(CMD, cmd);
	}

	/**
	 * parser を設定。
	 * 
	 * @param parser
	 *            parser を設定。
	 */
	public void setParser(Parser parser) {
		this.parser = parser;
	}
}
