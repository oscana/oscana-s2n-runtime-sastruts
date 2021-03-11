/*
 * 取り込み元
 *    ライブラリ名：     sa-struts
 *    クラス名：         org.seasar.struts.util.ActionUtil
 *    ソースリポジトリ： https://github.com/seasarorg/sa-struts/blob/master/src/main/java/org/seasar/struts/util/ActionUtil.java
 *
 * 上記ファイルを取り込み、修正を加えた。
 *
 * Copyright 2020 TIS Inc.
 *
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package oscana.s2n.seasar.struts.util;

/**
 * Action用のユーティリティクラス。
 *
 * @author higa
 *
 */
public final class ActionUtil {

    /**
     * サフィックス
     */
    private static final String SUFFIX = "Action";

    private ActionUtil() {
    }

    /**
     * パスをアクション名に変換する。
     *
     * @param path
     *            パス
     * @return アクション名
     */
    public static String fromPathToActionName(String path) {
        return path.substring(1).replace('/', '_') + SUFFIX;
    }

    /**
     * アクション名をパスに変換する。
     *
     * @param actionName
     *            アクション名
     * @return パス
     */
    public static String fromActionNameToPath(String actionName) {
        return "/"
                + actionName.replace('_', '/').substring(0,
                        actionName.length() - SUFFIX.length());
    }

    /**
     * Viewのパスからアクションのパスを計算する。
     *
     * @return アクションのパス
     */
    public static String calcActionPath() {
        String s = RequestUtil.getPath();
        if (s.indexOf('.') > 0) {
            s = s.substring(0, s.lastIndexOf('/') + 1);
        }
        return s;
    }
}
