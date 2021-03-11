/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.beans.util.BeanMap
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/s2-tiger/src/main/java/org/seasar/framework/beans/util/BeanMap.java
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

import java.util.LinkedHashMap;

/**
 *
 * Stringがキーで、存在しないキーにアクセスする(get)と例外を投げるマップクラス。<br>
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・キーを小文字に変換して、マップに設定する。<br>
 *    ・キーを小文字に変換して、マップから値を取得する。<br>
 *
 * @author higa
 *
 */
public class BeanMap extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 1;

    /**
     * マップからオブジェクトを取得する。
     *
     * @param key 条件を持つオブジェクト
     * @return 結果オブジェクト
     */
    @Override
    public Object get(Object key) {
        if (!containsKey(key)) {
            throw new IllegalArgumentException(key + " is not found in "
                    + keySet());
        }
        return super.get(key);
    }

}