/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.util.ResourceUtil
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/util/ResourceUtil.java
 *
 * 上記ファイルを取り込み、修正を加えた。
 *
 * Copyright 2020 TIS Inc.
 *
 * Copyright 2004-2013 the Seasar Foundation and the Others.
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
package oscana.s2n.seasar.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import oscana.s2n.seasar.framework.exception.IORuntimeException;

/**
 * リソース用のユーティリティクラス。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・クラス中に投げる例外はResourceNotFoundRuntimeExceptionからRuntimeExceptionに変更する。<br>
 *    ・呼んでいるorg.seasar.framework.util.URLUtilのopenStreamメソッドをクラス内に移植する。<br>
 *
 * @author higa
 */
public class ResourceUtil {

    /**
     * インスタンスを構築する。
     */
    protected ResourceUtil() {
    }

    /**
     * リソースパスを返却する。
     *
     * @param path パス
     * @param extension 拡張子名
     * @return リソースパス
     */
    public static String getResourcePath(String path, String extension) {
        if (extension == null) {
            return path;
        }
        extension = "." + extension;
        if (path.endsWith(extension)) {
            return path;
        }
        return path.replace('.', '/') + extension;
    }

    /**
     * リソースを返却する。
     *
     * @param path パス
     * @param extension 拡張子名
     * @return リソース
     * @throws RuntimeException
     *             リソースが見つからなかった場合
     */
    public static URL getResource(String path, String extension) {

        URL url = getResourceNoException(path, extension);
        if (url != null) {
            return url;
        }
        throw new RuntimeException(getResourcePath(path,
                extension));
    }

    /**
     * リソースを返却する、見つからなかった場合は<code>null</code>を返却する。
     *
     * @param path パス
     * @param extension 拡張子名
     * @return リソース
     * @see #getResourceNoException(String, String, ClassLoader)
     */
    public static URL getResourceNoException(String path, String extension) {
        return getResourceNoException(path, extension, Thread.currentThread()
                .getContextClassLoader());
    }

    /**
     * リソースを返却する、見つからなかった場合は<code>null</code>を返却する。
     *
     * @param path パス
     * @param extension 拡張子名
     * @param loader ローダー
     * @return リソース
     * @see #getResourcePath(String, String)
     */
    public static URL getResourceNoException(String path, String extension,
            ClassLoader loader) {
        if (path == null || loader == null) {
            return null;
        }
        path = getResourcePath(path, extension);
        return loader.getResource(path);
    }

    /**
     * リソースをストリームとして返却する。
     *
     * @param path パス
     * @return ストリーム
     * @see #getResourceAsStream(String, String)
     */
    public static InputStream getResourceAsStream(String path) {
        return getResourceAsStream(path, null);
    }

    /**
     * リソースをストリームとして返却する。
     *
     * @param path パス
     * @param extension 拡張子名
     * @return ストリーム
     * @see #getResource(String, String)
     */
    public static InputStream getResourceAsStream(String path, String extension) {
        URL url = getResource(path, extension);
        return openStream(url);
    }

    /**
     * URLをオープンして{@link InputStream}を返却する。
     *
     * @param url
     *            URL
     * @return URLが表すリソースを読み込むための{@link InputStream}
     */
    private static InputStream openStream(URL url) {
        try {
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}