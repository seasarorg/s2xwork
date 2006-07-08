package org.seasar.xwork.clwork;

import org.apache.commons.cli.Options;

/**
 * 全てのオプションを受け付けるためのOptions
 */
public class AllowAllOptions extends Options {
    /**
     * 全てのオプションを受け付けるため、オプションが無い場合は追加する。 <br>
     * なお先頭に"-"が付くものをオプション名とみなす(例:-name)
     * 
     * @param name
     *            パラメータ名
     * @return 先頭に"-"がつく場合は必ずtrue
     */
    public boolean hasOption(String name) {
        if (name.startsWith("-")) {
            if (!super.hasOption(name)) {
                addOption(name.replaceAll("-", ""), true, "");
            }
        }
        return super.hasOption(name);
    }
}
