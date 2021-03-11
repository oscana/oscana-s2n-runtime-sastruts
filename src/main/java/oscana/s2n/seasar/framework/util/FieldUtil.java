/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.util.FieldUtil
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/util/FieldUtil.java
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * {@link Field}用のユーティリティクラス。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・クラス中に投げる例外はIllegalAccessRuntimeExceptionからRuntimeExceptionに変更する。<br>
 *
 * @author higa
 *
 */
public class FieldUtil {

    /**
     * インスタンスを構築する。
     */
    protected FieldUtil() {
    }

    /**
     * {@link Field}の値をオブジェクトとして取得する。
     *
     * @param field フィルド
     * @param target ターゲット
     * @return {@link Object}
     * @see Field#get(Object)
     */
    public static Object get(Field field, Object target) {

        try {
            return field.get(target);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(field.getDeclaringClass().getName(),
                    ex);
        }
    }

    /**
     * staticな {@link Field}の値をintとして取得する。
     *
     * @param field フィルド
     * @return intの値
     * @see #getInt(Field, Object)
     */
    public static int getInt(Field field) {
        return getInt(field, null);
    }

    /**
     * {@link Field}の値をintとして取得する。
     *
     * @param field フィルド
     * @param target ターゲット
     * @return intの値
     * @see Field#getInt(Object)
     */
    public static int getInt(Field field, Object target) {
        try {
            return field.getInt(target);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(field.getDeclaringClass().getName(),
                    ex);
        }
    }

    /**
     * staticな {@link Field}の値を {@link String}として取得する。
     *
     * @param field フィルド
     * @return {@link String}の値
     * @see #getString(Field, Object)
     */
    public static String getString(Field field) {
        return getString(field, null);
    }

    /**
     * {@link Field}の値を {@link String}として取得する。
     *
     * @param field フィルド
     * @param target ターゲット
     * @return {@link String}の値
     * @see Field#get(Object)
     */
    public static String getString(Field field, Object target) {

        try {
            return (String) field.get(target);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(field.getDeclaringClass().getName(),
                    ex);
        }
    }

    /**
     * {@link Field}に値を設定する。
     *
     * @param field フィルド
     * @param target ターゲット
     * @param value オブジェクト
     * @see Field#set(Object, Object)
     */
    public static void set(Field field, Object target, Object value) {

        try {
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(field.getDeclaringClass().getName(),
                    e);
        }

    }

    /**
     * インスタンスフィールドかどうか返却する。
     *
     * @param field フィルド
     * @return インスタンスフィールドかどうか
     */
    public static boolean isInstanceField(Field field) {
        int mod = field.getModifiers();
        return !Modifier.isStatic(mod) && !Modifier.isFinal(mod);
    }

    /**
     * パブリックフィールドかどうか返却する。
     *
     * @param field フィルド
     * @return パブリックフィールドかどうか
     */
    public static boolean isPublicField(Field field) {
        int mod = field.getModifiers();
        return Modifier.isPublic(mod);
    }

}
