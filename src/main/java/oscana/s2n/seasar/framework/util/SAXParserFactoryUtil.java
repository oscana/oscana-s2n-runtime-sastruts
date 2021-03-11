/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.util.SAXParserFactoryUtil
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/util/SAXParserFactoryUtil.java
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

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * {@link SAXParser}用のユーティリティクラス。<br>
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・クラス中にキャッチしている例外はParserConfigurationException、SAXExceptionからExceptionに変更する。<br>
 *    ・クラス中に投げる例外はParserConfigurationException、SAXExceptionからRuntimeExceptionに変更する。<br>
 *
 * @author higa
 */
public class SAXParserFactoryUtil {

    protected SAXParserFactoryUtil() {
    }

    /**
     * {@link SAXParserFactory}の新しいインスタンスを作成する。
     *
     * @return {@link SAXParserFactory}の新しいインスタンス
     */
    public static SAXParserFactory newInstance() {
        return SAXParserFactory.newInstance();
    }

    /**
     * 指定の{@link SAXParserFactory}を使って{@link SAXParser}の新しいインスタンスを作成する。
     *
     * @param factory
     *            {@link SAXParserFactory}
     * @return {@link SAXParser}の新しいインスタンス
     */
    public static SAXParser newSAXParser(SAXParserFactory factory) {
        try {
            return factory.newSAXParser();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
