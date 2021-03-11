/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.util.ArrayUtil
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/util/ArrayUtil.java
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

import java.lang.reflect.Array;

/**
 * 配列に対するユーティリティクラス。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・配列がnullの場合、投げる例外はEmptyRuntimeExceptionからRuntimeExceptionに変更する。<br>
 *
 * @author higa
 *
 */
public class ArrayUtil {

    /**
     * インスタンスを構築する。
     */
    protected ArrayUtil() {
    }

    /**
     * 配列にオブジェクトを追加する。
     *
     * @param array 配列
     * @param obj オブジェクト
     * @return オブジェクトが追加された結果の配列
     */
    public static Object[] add(Object[] array, Object obj) {
        if (array == null) {
            throw new RuntimeException("array");
        }
        Object[] newArray = (Object[]) Array.newInstance(array.getClass()
                .getComponentType(), array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = obj;
        return newArray;
    }

    /**
     * 配列が空かどうかを返却する。
     *
     * @param arrays 配列
     * @return 配列が空かどうか
     */
    public static boolean isEmpty(Object[] arrays) {
        return (arrays == null || arrays.length == 0);
    }

}
