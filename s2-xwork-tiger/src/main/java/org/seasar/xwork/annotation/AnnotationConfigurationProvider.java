package org.seasar.xwork.annotation;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.opensymphony.util.FileManager;
import com.opensymphony.xwork.config.Configuration;
import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.config.ConfigurationProvider;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork.config.entities.ExternalReference;
import com.opensymphony.xwork.config.entities.PackageConfig;
import com.opensymphony.xwork.config.entities.ResultConfig;
import com.opensymphony.xwork.config.entities.ResultTypeConfig;
import com.opensymphony.xwork.config.providers.InterceptorBuilder;
import com.sun.org.apache.bcel.internal.util.ClassLoader;

/**
 * 既存のXWorkのConfigurationにアノテーションの設定を追加する
 */
public class AnnotationConfigurationProvider implements ConfigurationProvider {
	/** xwork.xmlのペースとなるパッケージ */
	private String packageName = "default";

	/** 読み込むクラスパターン */
	private String classPattern = ".*Action";

	/** 読み込むベースとなるパッケージ名 */
	private String classPackage = "";

	/** 読み込みから除外するパッケージ名 */
	private String ignoreClassPackage = "";

	/** クラスを読み込むベースディレクトリのルートに存在するファイル名 */
	private String resource = "xwork.xml";

	/** 毎回設定を読み直すかどうか */
	private boolean reload;

	/** PackageConfig */
	private PackageConfig packageConfig;

	/**
	 * XWorkActionアノテーションが設定されているクラスを登録します
	 * 
	 * @param configuration
	 *            設定
	 */
	public void init(Configuration configuration) throws ConfigurationException {
		packageConfig = configuration.getPackageConfig(packageName);

		try {
			searchAnnotation();
		} catch (ClassNotFoundException e) {
			throw new ConfigurationException(e);
		}
	}

	/**
	 * アノテーションが付いているクラスを探索する。
	 * 
	 * @throws ClassNotFoundException
	 */
	public void searchAnnotation() throws ClassNotFoundException {
		File root = AnnotationConfigurationProvider.getRootPath(resource);
		Collection files = FileUtils.listFiles(new File(root.getAbsolutePath()
				+ File.separator + classPackage.replace(".", File.separator)),
				new SuffixFileFilter("class"), TrueFileFilter.INSTANCE);
		Pattern pattern = Pattern.compile(classPattern);
		Pattern ignorePattern = Pattern.compile(ignoreClassPackage);
		for (Iterator iter = files.iterator(); iter.hasNext();) {
			File file = (File) iter.next();
			String suffix = file.getName().substring(0,
					file.getName().indexOf("."));
			if (pattern.matcher(suffix).matches()) {
				String className = file.getAbsolutePath().substring(
						root.getAbsolutePath().length() + 1,
						(int) file.getAbsolutePath().length()
								- ".class".length()).replace(File.separator,
						".");
				if (!ignorePattern.matcher(className).matches()) {
					setAction(packageConfig, Thread.currentThread()
							.getContextClassLoader().loadClass(className));
				}
			}
		}
	}

	/**
	 * PackageConfigにAction設定を追加します。
	 * 
	 * @param packageConfig
	 *            PackageConfig
	 * @param clazz
	 *            アノテーションが付いているActionクラス
	 */
	public static void setAction(PackageConfig packageConfig, Class clazz) {
		XWorkAction action = (XWorkAction) clazz
				.getAnnotation(XWorkAction.class);
		if (action != null) {
			packageConfig.addActionConfig(action.name(), buildAction(clazz
					.getName(), action, packageConfig));
		}
		XWorkActions actions = (XWorkActions) clazz
				.getAnnotation(XWorkActions.class);
		if (actions != null) {
			for (int i = 0; i < actions.actions().length; i++) {
				packageConfig.addActionConfig(actions.actions()[i].name(),
						buildAction(clazz.getName(), actions.actions()[i],
								packageConfig));
			}
		}
	}

	/**
	 * XWorkActionアノテーションからActionConfigを作成します
	 * 
	 * @param className
	 *            クラス名
	 * @param action
	 *            XWorkActionアノテーション
	 * @param packageConfig
	 *            PackageConfig
	 * @return ActionConfig
	 */
	static ActionConfig buildAction(String className, XWorkAction action,
			PackageConfig packageConfig) {
		ActionConfig actionConfig = new ActionConfig();
		actionConfig.setClassName(className);
		actionConfig.setMethodName(action.method());
		actionConfig.setResults(buildResults(action.result(), packageConfig));
		actionConfig.setParams(buildParam(action.param()));
		for (InterceptorRef interceptorRef : action.interceptorRef()) {
			actionConfig.addInterceptors(buildInterceptor(interceptorRef,
					packageConfig));
		}
		for (ExternalRef externalRef : action.externalRef()) {
			actionConfig.addExternalRef(buildExternalRef(externalRef,
					packageConfig));
		}
		for (ExceptionMapping exceptionMapping : action.exceptionMapping()) {
			actionConfig.addExceptionMapping(buildExceptionMapping(
					exceptionMapping, packageConfig));
		}

		return actionConfig;
	}

	private static ExceptionMappingConfig buildExceptionMapping(
			ExceptionMapping exceptionMapping, PackageConfig packageConfig) {
		ExceptionMappingConfig config = new ExceptionMappingConfig();
		config.setExceptionClassName(exceptionMapping.exception());
		config.setName(exceptionMapping.name());
		config.setResult(exceptionMapping.result());
		Map params = buildParam(exceptionMapping.param());
		for (Iterator iter = params.entrySet().iterator(); iter.hasNext();) {
			Entry entry = (Entry) iter.next();
			config.addParam((String) entry.getKey(), entry.getValue());
		}

		return config;
	}

	private static ExternalReference buildExternalRef(ExternalRef externalRef,
			PackageConfig packageConfig) {
		ExternalReference externalReference = new ExternalReference();
		externalReference.setName(externalRef.name());
		externalReference.setExternalRef(externalRef.externalRef());
		externalReference.setRequired(externalRef.required());
		return externalReference;
	}

	private static List buildInterceptor(InterceptorRef interceptorRef,
			PackageConfig packageConfig) {

		return InterceptorBuilder.constructInterceptorReference(packageConfig,
				interceptorRef.name(), buildParam(interceptorRef.param()));
	}

	/**
	 * ResultアノテーションからResultConfigのMapを作成します
	 * 
	 * @param results
	 *            Resultアノテーション
	 * @param packageConfig
	 *            PackageConfig
	 * @return ResultConfigのMap
	 */
	static Map buildResults(Result[] results, PackageConfig packageConfig) {
		Map<String, ResultConfig> resultMap = new HashMap<String, ResultConfig>();
		Map resultTypeConfigs = packageConfig.getAllResultTypeConfigs();
		for (int i = 0; i < results.length; i++) {
			ResultTypeConfig result = (ResultTypeConfig) resultTypeConfigs
					.get(results[i].type());
			ResultConfig resultConfig = new ResultConfig(results[i].name(),
					result.getClazz(), buildParam(results[i].param()));
			resultMap.put(resultConfig.getName(), resultConfig);
		}
		return resultMap;

	}

	/**
	 * ParamアノテーションからparamのMapを作成します
	 * 
	 * @param params
	 *            Paramアノテーション
	 * @return paramのMap
	 */
	static Map buildParam(Param[] params) {
		Map<String, String> paramMap = new HashMap<String, String>();
		for (int i = 0; i < params.length; i++) {
			paramMap.put(params[i].name(), params[i].value());
		}
		return paramMap;
	}

	public void destroy() {
	}

	public boolean needsReload() {
		return reload;
	}

	/**
	 * リソース名を元にクラスパスのルートを取得します。
	 * 
	 * @param resource
	 *            クラスを読み込むベースディレクトリのルートに存在するファイル名
	 * @return クラスパスのルート
	 */
	public static File getRootPath(String resource) {
		URL url = ClassLoader.getSystemResource(resource);
		if (url == null) {
			url = Thread.currentThread().getContextClassLoader().getResource(
					resource);
		}
		return new File(url.getFile()).getParentFile();
	}

	/**
	 * 再読込を行うかどうかを設定します。
	 * 
	 * @param reload
	 *            再読込を行うかどうか
	 */
	public void setReload(boolean reload) {
		this.reload = reload;
		if (reload) {
			FileManager.setReloadingConfigs(true);
		}
	}

	/**
	 * xwork.xmlのペースとなるパッケージ名を設定します。
	 * 
	 * @param packageName
	 *            xwork.xmlのペースとなるパッケージ名
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * 読み込むベースとなるパッケージ名を設定します。
	 * 
	 * @param classPackage
	 *            読み込むベースとなるパッケージ名
	 */
	public void setClassPackage(String classPackage) {
		this.classPackage = classPackage;
	}

	/**
	 * 読み込むクラスパターンを設定します。
	 * 
	 * @param classPattern
	 *            読み込むクラスパターン
	 */
	public void setClassPattern(String classPattern) {
		this.classPattern = classPattern;
	}

	/**
	 * 読み込みから除外するパッケージ名を設定します。
	 * 
	 * @param ignoreClassPackage
	 *            読み込みから除外するパッケージ名
	 */
	public void setIgnoreClassPackage(String ignoreClassPackage) {
		this.ignoreClassPackage = ignoreClassPackage;
	}

	/**
	 * クラスを読み込むベースディレクトリのルートに存在するファイル名を設定します。
	 * 
	 * @param resource
	 *            クラスを読み込むベースディレクトリのルートに存在するファイル名
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
}
