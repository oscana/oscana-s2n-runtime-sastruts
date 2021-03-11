/*
 * 取り込み元
 *    ライブラリ名：     sa-struts
 *    クラス名：         org.seasar.struts.util.RequestUtil
 *    ソースリポジトリ： https://github.com/seasarorg/sa-struts/blob/master/src/main/java/org/seasar/struts/util/RequestUtil.java
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

import javax.servlet.http.HttpServletRequest;

import nablarch.fw.dicontainer.nablarch.Containers;
import oscana.s2n.handler.HttpResourceHolder;
import oscana.s2n.seasar.framework.util.StringUtil;

/**
 * リクエストに関するユーティリティです。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・getRequestメソッド：{@link nablarch.fw.dicontainer.nablarch.Containers}を利用して、リクエストコンポーネントを取得する。<br>
 *
 * @author higa
 *
 */
public final class RequestUtil {

    private RequestUtil() {
    }

    /**
     * リクエストを返却する。
     *
     * @return リクエスト
     */
    public static HttpServletRequest getRequest() {

        HttpResourceHolder resource = Containers.get().getComponent(HttpResourceHolder.class);
        return resource.getHttpServletRequest();
    }

    /**
     * パスを返返却する。
     *
     * @return パス
     */
    public static String getPath() {
        return getPath(getRequest());
    }

    /**
     * パスを返却する。
     *
     * @param request
     *            リクエスト
     * @return パス
     */
    public static String getPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        if (StringUtil.isEmpty(path)) {
            path = request.getServletPath();
        }
        if (path == null) {
            return null;
        }
        String viewPrefix = ServletContextUtil.getViewPrefix();
        if (viewPrefix == null) {
            return path;
        }
        if (path.startsWith(viewPrefix)) {
            path = path.substring(viewPrefix.length());
        }
        return path;
    }
}