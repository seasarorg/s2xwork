package org.seasar.xwork.annotation;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.opensymphony.xwork.config.Configuration;
import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.config.ConfigurationProvider;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.PackageConfig;
import com.opensymphony.xwork.config.entities.ResultConfig;
import com.opensymphony.xwork.config.entities.ResultTypeConfig;
import com.sun.org.apache.bcel.internal.util.ClassLoader;

/**
 * ������XWork��Configuration�ɃA�m�e�[�V�����̐ݒ��ǉ�����
 */
public class AnnotationConfigurationProvider implements ConfigurationProvider {
	/** xwork.xml�̃y�[�X�ƂȂ�p�b�P�[�W */
	private String packageName = "default";

	private PackageConfig packageConfig;

	private String classPattern = ".*Action";

	private String classPackage = "";

	private String resource = "xwork.xml";

	private boolean reload;

	/**
	 * XWorkAction�A�m�e�[�V�������ݒ肳��Ă���N���X��o�^���܂�
	 * 
	 * @param configuration
	 *            �ݒ�
	 */
	public void init(Configuration configuration) throws ConfigurationException {
		packageConfig = configuration.getPackageConfig(packageName);
		try {
			searchAnnotation();
		} catch (ClassNotFoundException e) {
			throw new ConfigurationException(e);
		}
	}

	public void searchAnnotation() throws ClassNotFoundException {
		File root = AnnotationConfigurationProvider.getRootPath(resource);
		Collection files = FileUtils.listFiles(new File(root.getAbsolutePath()
				+ File.separator + classPackage.replace(".", File.separator)),
				new SuffixFileFilter("class"), TrueFileFilter.INSTANCE);
		Pattern pattern = Pattern.compile(classPattern);
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
				setAction(packageConfig, Thread.currentThread()
						.getContextClassLoader().loadClass(className));
			}
		}
	}

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
	 * XWorkAction�A�m�e�[�V��������ActionConfig���쐬���܂�
	 * 
	 * @param action
	 *            XWorkAction�A�m�e�[�V����
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

		return actionConfig;
	}

	/**
	 * Result�A�m�e�[�V��������ResultConfig��Map���쐬���܂�
	 * 
	 * @param results
	 *            Result�A�m�e�[�V����
	 * @param packageConfig
	 *            PackageConfig
	 * @return ResultConfig��Map
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
	 * Param�A�m�e�[�V��������param��Map���쐬���܂�
	 * 
	 * @param params
	 *            Param�A�m�e�[�V����
	 * @return param��Map
	 */
	static Map buildParam(Param[] params) {
		Map<String, String> paramMap = new HashMap<String, String>();
		for (int i = 0; i < params.length; i++) {
			paramMap.put(params[i].name(), params[i].value());
		}
		return paramMap;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void destroy() {
	}

	public boolean needsReload() {
		return reload;
	}

	public static File getRootPath(String resource) {
		URL url = ClassLoader.getSystemResource(resource);
		if (url == null) {
			url = Thread.currentThread().getContextClassLoader().getResource(
					resource);
		}
		return new File(url.getFile()).getParentFile();
	}

	public boolean isReload() {
		return reload;
	}

	public void setReload(boolean reload) {
		this.reload = reload;
	}
}
