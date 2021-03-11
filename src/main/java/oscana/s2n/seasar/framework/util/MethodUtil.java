/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.util.MethodUtil
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/util/MethodUtil.java
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

import java.lang.reflect.Method;

/**
 * {@link Method}用のユーティリティクラス。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・クラス中にキャッチしている例外はInvocationTargetRuntimeException、IllegalAccessRuntimeExceptionからExceptionに変更する。<br>
 *    ・クラス中に投げる例外はInvocationTargetRuntimeException、IllegalAccessRuntimeExceptionからRuntimeExceptionに変更する。<br>
 *
 */
public class MethodUtil {

    /** ブリッジメソッド  */
    private static final Method IS_BRIDGE_METHOD = getIsBridgeMethod();

    /** 合成メソッド  */
    private static final Method IS_SYNTHETIC_METHOD = getIsSyntheticMethod();


    protected MethodUtil() {
    }

    /**
     * {@link Method#invoke(Object, Object[])}の例外処理をラップする。
     *
     * @param method メソッド
     * @param target ターゲット
     * @param args パラメータ配列
     * @return 戻り値
     * @see Method#invoke(Object, Object[])
     */
    public static Object invoke(Method method, Object target, Object[] args) {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException(method.getDeclaringClass().getName(),
                    e);
        }
    }


    /**
     * 合成メソッドかどうかを返す。
     *
     * @param method
     * @return 合成メソッドかどうか
     */
    public static boolean isSyntheticMethod(final Method method) {
        if (IS_SYNTHETIC_METHOD == null) {
            return false;
        }
        return ((Boolean) MethodUtil.invoke(IS_SYNTHETIC_METHOD, method, null))
                .booleanValue();
    }

    /**
     * ブリッジメソッドかどうか返す。
     * @param method メソッド
     * @return ブリッジメソッドかどうか
     */
    public static boolean isBridgeMethod(final Method method) {
        if (IS_BRIDGE_METHOD == null) {
            return false;
        }
        return ((Boolean) MethodUtil.invoke(IS_BRIDGE_METHOD, method, null))
                .booleanValue();
    }

    /**
     * ブリッジメソッドを返す。
     * @return ブリッジメソッド
     */
    private static Method getIsBridgeMethod() {
        try {
            return Method.class.getMethod("isBridge", (Class<?>)null);
        } catch (final NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * 合成メソッドを返す。
     * @return 合成メソッド
     */
    private static Method getIsSyntheticMethod() {
        try {
            return Method.class.getMethod("isSynthetic", (Class<?>)null);
        } catch (final NoSuchMethodException e) {
            return null;
        }
    }
}