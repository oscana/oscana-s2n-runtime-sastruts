/*
 * 取り込み元
 *    ライブラリ名：     sa-struts
 *    クラス名：         org.seasar.struts.util.ServletContextUtil
 *    ソースリポジトリ： https://github.com/seasarorg/sa-struts/blob/master/src/main/java/org/seasar/struts/util/ServletContextUtil.java
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

import javax.servlet.ServletContext;

import nablarch.fw.dicontainer.nablarch.Containers;
import oscana.s2n.handler.HttpResourceHolder;
import oscana.s2n.seasar.framework.util.StringUtil;

/**
 * サーブレットコンテキスト用のユーティリティクラス。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・getServletContextメソッド：{@link nablarch.fw.dicontainer.nablarch.Containers}を利用して サーブレットコンテキストを取得する。<br>
 *
 * @author higa
 *
 */
public final class ServletContextUtil {

    /**
     * サフィックス
     */
    private static final String VIEW_PREFIX = "sastruts.VIEW_PREFIX";

    private ServletContextUtil() {
    }

    /**
     * サーブレットコンテキストを返却する。
     *
     * @return サーブレットコンテキスト
     */
    public static ServletContext getServletContext() {
        HttpResourceHolder resource = Containers.get().getComponent(HttpResourceHolder.class);
        return resource.getServletContext();
    }

    /**
     * Viewプレフィックスを返却する。
     *
     * @return Viewプレフィックス
     */
    public static String getViewPrefix() {
        String viewPrefix = getServletContext().getInitParameter(VIEW_PREFIX);
        if (StringUtil.isBlank(viewPrefix)) {
            return null;
        }
        return viewPrefix.trim();
    }
}