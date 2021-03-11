/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.beans.factory.BeanDescFactory
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/beans/factory/BeanDescFactory.java
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
package oscana.s2n.seasar.framework.beans.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import oscana.s2n.seasar.framework.beans.BeanDesc;

/**
 * {@link BeanDesc}を生成するクラス。<br>
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 * ・Java5以前の互換コードを削除（Java5以前では動作しない）。
 *
 * @author higa
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class BeanDescFactory {

    private static Map beanDescCache = new ConcurrentHashMap(1024);

    /**
     * {@link BeanDesc}を返却する。
     *
     * @param clazz クラス
     * @return {@link BeanDesc}
     */
    public static BeanDesc getBeanDesc(Class<?> clazz) {
        BeanDesc beanDesc = (BeanDesc) beanDescCache.get(clazz);
        if (beanDesc == null) {
            beanDesc = new BeanDesc(clazz);
            beanDescCache.put(clazz, beanDesc);
        }
        return beanDesc;

    }
}
