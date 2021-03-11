/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.beans.util.CreateAndCopy
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/s2-tiger/src/main/java/org/seasar/framework/beans/util/CreateAndCopy.java
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
package oscana.s2n.seasar.framework.beans.util;

import java.util.Map;

/**
 * JavaBeansやMapを作成し、プロパティをコピーするクラス。<br>
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・executeメソッドに作成対象クラスdestClassが抽象クラスかの判断処理を削除する。<br>
 * @author higa
 *
 */
public class CreateAndCopy<T> extends AbstractCopy<CreateAndCopy<T>> {

    /**
     * 作成対象クラス
     */
    protected Class<T> destClass;

    /**
     * コピー元
     */
    protected Object src;

    /**
     * インスタンスを構築する。
     *
     * @param destClass
     *            作成対象クラス
     * @param src
     *            コピー元
     * @throws NullPointerException
     *             引数が<code>null</code>だった場合
     */
    public CreateAndCopy(Class<T> destClass, Object src)
            throws NullPointerException {
        if (destClass == null) {
            throw new NullPointerException("destClass");
        }
        if (src == null) {
            throw new NullPointerException("src");
        }
        this.destClass = destClass;
        this.src = src;
    }

    /**
     * JavaBeansやMapを作成し、プロパティをコピーする。
     *
     * @return 作成結果
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public T execute() {
        if (Map.class.isAssignableFrom(destClass)) {

            if (src instanceof Map) {
                return (T) createAndCopyMapToMap((Map) src, destClass);
            } else {
                return (T) createAndCopyBeanToMap(src, destClass);
            }
        }

        if (src instanceof Map) {
            return (T) createAndcopyMapToBean((Map) src, destClass);
        } else {
            return (T) createAndCopyBeanToBean(src, destClass);
        }
    }
}