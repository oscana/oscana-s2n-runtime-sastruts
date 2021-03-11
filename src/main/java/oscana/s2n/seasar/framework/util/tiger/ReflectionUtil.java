/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.util.tiger.ReflectionUtil
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/s2-tiger/src/main/java/org/seasar/framework/util/tiger/ReflectionUtil.java
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
package oscana.s2n.seasar.framework.util.tiger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Java5のgenericsや可変長を活用する、リフレクションのためのユーティリティクラス。
 *
 * @author koichik
 */
public abstract class ReflectionUtil {

    /**
     * インスタンスを構築する。
     */
    protected ReflectionUtil() {
    }

    /**
     * 現在のスレッドのコンテキストクラスローダを使って、 指定された文字列名を持つクラスまたはインタフェースに関連付けられた、
     * {@link Class}オブジェクトを返却する。
     *
     * @param <T>
     *            {@link Class}オブジェクトが表すクラス
     * @param className
     *            要求するクラスの完全修飾名
     * @return 指定された名前を持つクラスの{@link Class}オブジェクト
     * @see Class#forName(String)
     */
    public static <T> Class<T> forName(final String className) {
        return forName(className, Thread.currentThread()
                .getContextClassLoader());
    }

    /**
     * 指定されたクラスローダを使って、 指定された文字列名を持つクラスまたはインタフェースに関連付けられた{@link Class}オブジェクトを返却する。
     *
     * @param <T>
     *            {@link Class}オブジェクトが表すクラス
     * @param className
     *            要求するクラスの完全修飾名
     * @param loader
     *            クラスのロード元である必要があるクラスローダ
     * @return 指定された名前を持つクラスの{@link Class}オブジェクト
     * @see Class#forName(String, boolean, ClassLoader)
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> forName(final String className,
            final ClassLoader loader) {
        try {
            return (Class<T>) Class.forName(className, true, loader);
        } catch (final ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 現在のスレッドのコンテキストクラスローダを使って、 指定された文字列名を持つクラスまたはインタフェースに関連付けられた、
     * {@link Class}オブジェクトを返却する。
     * <p>
     * クラスが見つからなかった場合は{@code null}を返却する。
     * </p>
     *
     * @param <T>
     *            {@link Class}オブジェクトが表すクラス
     * @param className
     *            要求するクラスの完全修飾名
     * @return 指定された名前を持つクラスの{@link Class}オブジェクト
     * @see Class#forName(String)
     */
    public static <T> Class<T> forNameNoException(final String className) {
        return forNameNoException(className, Thread.currentThread()
                .getContextClassLoader());
    }

    /**
     * 指定されたクラスローダを使って、 指定された文字列名を持つクラスまたはインタフェースに関連付けられた、 {@link Class}オブジェクトを返却する。
     * <p>
     * クラスが見つからなかった場合は{@code null}を返却する。
     * </p>
     *
     * @param <T>
     *            {@link Class}オブジェクトが表すクラス
     * @param className
     *            要求するクラスの完全修飾名
     * @param loader
     *            クラスのロード元である必要があるクラスローダ
     * @return 指定された名前を持つクラスの{@link Class}オブジェクト
     * @see Class#forName(String)
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> forNameNoException(final String className,
            final ClassLoader loader) {
        try {
            return (Class<T>) Class.forName(className, true, loader);
        } catch (final Throwable ignore) {
            return null;
        }
    }

    /**
     * {@link Class}オブジェクトが表すクラスの指定された{@code public}コンストラクタをリフレクトする{@link Constructor}オブジェクトを返却する。
     *
     * @param <T>
     *            {@link Class}オブジェクトが表すクラス
     * @param clazz
     *            クラスの{@link Class}オブジェクト
     * @param argTypes
     *            パラメータ配列
     * @return 指定された{@code argTypes}と一致する{@code public}コンストラクタの{@link Constructor}オブジェクト
     * @see Class#getConstructor(Class[])
     */
    public static <T> Constructor<T> getConstructor(final Class<T> clazz,
            final Class<?>... argTypes) {
        try {
            return clazz.getConstructor(argTypes);
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(clazz.getName() + argTypes, e);
        }
    }

    /**
     * {@link Class}オブジェクトが表すクラスまたはインタフェースの指定されたコンストラクタをリフレクトする{@link Constructor}オブジェクトを返却する。
     *
     * @param <T>
     *            {@link Class}オブジェクトが表すクラス
     * @param clazz
     *            クラスの{@link Class}オブジェクト
     * @param argTypes
     *            パラメータ配列
     * @return 指定された{@code argTypes}と一致するコンストラクタの{@link Constructor}オブジェクト
     * @see Class#getDeclaredConstructor(Class[])
     */
    public static <T> Constructor<T> getDeclaredConstructor(
            final Class<T> clazz, final Class<?>... argTypes) {
        try {
            return clazz.getDeclaredConstructor(argTypes);
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(clazz.getName() + argTypes, e);
        }
    }

    /**
     * {@link Class}オブジェクトが表すクラスまたはインタフェースの指定された{@code public}メンバフィールドをリフレクトする{@link Field}オブジェクトを返却する。
     *
     * @param clazz
     *            クラスの{@link Class}オブジェクト
     * @param name
     *            フィールド名
     * @return {@code name}で指定されたこのクラスの{@link Field}オブジェクト
     * @see Class#getField(String)
     */
    public static Field getField(final Class<?> clazz, final String name) {
        try {
            return clazz.getField(name);
        } catch (final NoSuchFieldException e) {
            throw new RuntimeException(clazz.getName() + name, e);
        }
    }

    /**
     * {@link Class}オブジェクトが表すクラスまたはインタフェースの指定された宣言フィールドをリフレクトする{@link Field}オブジェクトを返却する。
     *
     * @param clazz
     *            クラスの{@link Class}オブジェクト
     * @param name
     *            フィールド名
     * @return {@code name}で指定されたこのクラスの{@link Field}オブジェクト
     * @see Class#getDeclaredField(String)
     */
    public static Field getDeclaredField(final Class<?> clazz, final String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (final NoSuchFieldException e) {
            throw new RuntimeException(clazz.getName() + name, e);
        }
    }

    /**
     * {@link Class}オブジェクトが表すクラスまたはインタフェースの指定された{@code public}メンバメソッドをリフレクトする{@link Method}オブジェクトを返却する。
     *
     * @param clazz
     *            クラスの{@link Class}オブジェクト
     * @param name
     *            メソッドの名前
     * @param argTypes
     *            パラメータのリスト
     * @return 指定された{@code name}および{@code argTypes}と一致する{@link Method}オブジェクト
     * @see Class#getMethod(String, Class[])
     */
    public static Method getMethod(final Class<?> clazz, final String name,
            final Class<?>... argTypes) {
        try {
            return clazz.getMethod(name, argTypes);
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(clazz.getName() + name + argTypes, e);
        }
    }

    /**
     * {@link Class}オブジェクトが表すクラスまたはインタフェースの指定されたメンバメソッドをリフレクトする{@link Method}オブジェクトを返却する。
     *
     * @param clazz
     *            クラスの{@link Class}オブジェクト
     * @param name
     *            メソッドの名前
     * @param argTypes
     *            パラメータのリスト
     * @return 指定された{@code name}および{@code argTypes}と一致する{@link Method}オブジェクト
     * @see Class#getDeclaredMethod(String, Class[])
     */
    public static Method getDeclaredMethod(final Class<?> clazz,
            final String name, final Class<?>... argTypes) {
        try {
            return clazz.getDeclaredMethod(name, argTypes);
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(clazz.getName() + name + argTypes, e);
        }
    }

    /**
     * 指定されたクラスのデフォルトコンストラクタで、クラスの新しいインスタンスを作成および初期化する。
     *
     * @param <T>
     *            {@link Class}オブジェクトが表すクラス
     * @param clazz
     *            クラスを表す{@link Class}オブジェクト
     * @return このオブジェクトが表すコンストラクタを呼び出すことで作成される新規オブジェクト
     * @see Constructor#newInstance(Object[])
     */
    public static <T> T newInstance(final Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(clazz.getName(), e);
        }
    }

    /**
     * 指定された初期化パラメータで、コンストラクタの宣言クラスの新しいインスタンスを作成および初期化する。
     *
     * @param <T>
     *            コンストラクタの宣言クラス
     * @param constructor
     *            コンストラクタ
     * @param args
     *            コンストラクタ呼び出しに引数として渡すオブジェクトの配列
     * @return コンストラクタを呼び出すことで作成される新規オブジェクト
     * @see Constructor#newInstance(Object[])
     */
    public static <T> T newInstance(final Constructor<T> constructor,
            final Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(constructor
                    .getDeclaringClass().getName(), e);
        }
    }

    /**
     * 指定されたオブジェクトについて、{@link Field}によって表されるフィールドの値を返却する。
     *
     * @param <T>
     *            フィールドの型
     * @param field
     *            フィールド
     * @param target
     *            表現されるフィールド値の抽出元オブジェクト
     * @return オブジェクト{@code obj}内で表現される値
     * @see Field#get(Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValue(final Field field, final Object target) {
        try {
            return (T) field.get(target);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(field.getDeclaringClass().getName(),
                    e);
        }
    }

    /**
     * 指定されたオブジェクトについて、{@link Field}によって表される{@code static}フィールドの値を返却する。
     *
     * @param <T>
     *            フィールドの型
     * @param field
     *            フィールド
     * @return {@code static}フィールドで表現される値
     * @see Field#get(Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getStaticValue(final Field field) {
        return (T) getValue(field, null);
    }

    /**
     * {@link Field}オブジェクトによって表される指定されたオブジェクト引数のフィールドを、指定された新しい値に設定する。
     *
     * @param field
     *            フィールド
     * @param target
     *            フィールドを変更するオブジェクト
     * @param value
     *            変更中の{@code target}の新しいフィールド値
     * @see Field#set(Object, Object)
     */
    public static void setValue(final Field field, final Object target,
            final Object value) {
        try {
            field.set(target, value);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(field.getDeclaringClass().getName(),
                    e);
        }
    }

    /**
     * {@link Field}オブジェクトによって表される{@code static}フィールドを、指定された新しい値に設定する。
     *
     * @param field
     *            フィールド
     * @param value
     *            {@code static}フィールドの新しい値
     * @see Field#set(Object, Object)
     */
    public static void setStaticValue(final Field field, final Object value) {
        setValue(field, null, value);
    }

    /**
     * {@link Method}オブジェクトによって表される基本となるメソッドを、指定したオブジェクトに対して指定したパラメータの処理を行う。
     *
     * @param <T>
     *            メソッドの戻り値の型
     * @param method
     *            メソッド
     * @param target
     *            基本となるメソッドの呼び出し元のオブジェクト
     * @param args
     *            メソッド呼び出しに使用される引数
     * @return このオブジェクトが表すメソッドを、パラメータ{@code args}を使用して{@code obj}にディスパッチした結果
     * @see Method#invoke(Object, Object[])
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(final Method method, final Object target,
            final Object... args) {
        try {
            return (T) method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException(method.getDeclaringClass().getName(),
                    e);
        }
    }

    /**
     * {@link Method}オブジェクトによって表される基本となる{@code static}メソッドを、指定したパラメータの処理を行う。
     *
     * @param <T>
     *            メソッドの戻り値の型
     * @param method
     *            メソッド
     * @param args
     *            メソッド呼び出しに使用される引数
     * @return このオブジェクトが表す{@code static}メソッドを、パラメータ{@code args}を使用してディスパッチした結果
     * @see Method#invoke(Object, Object[])
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeStatic(final Method method, final Object... args) {
        return (T) invoke(method, null, args);
    }

}