/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.beans.util.AbstractCopy
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/s2-tiger/src/main/java/org/seasar/framework/beans/util/AbstractCopy.java
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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nablarch.core.beans.BeanUtil;
import nablarch.core.beans.ConversionUtil;
import nablarch.core.beans.CopyOptions;
import nablarch.core.log.Logger;
import nablarch.core.log.LoggerManager;
import nablarch.core.util.StringUtil;

/**
 * JavaBeansやMapに対して操作する抽象クラス。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 * ・JavaBeansやMapに対して内部実装をNablarchのユーティリティを呼ぶように変更する。<br>
 *
 * @author higa
 */
public abstract class AbstractCopy<S extends AbstractCopy<S>> {

    /** ロガー */
    private static final Logger LOGGER = LoggerManager.get(BeanUtil.class);

    /**
     * 空文字列の配列
     */
    protected static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * 操作のオプション
     */
    protected CopyOptions copyOptions = CopyOptions.options().build();

    /**
     * 操作の対象に含めるプロパティ名の配列
     */
    protected String[] includePropertyNames = EMPTY_STRING_ARRAY;

    /**
     * 操作の対象に含めないプロパティ名の配列
     */
    protected String[] excludePropertyNames = EMPTY_STRING_ARRAY;

    /**
     * null値のプロパティを操作の対象外にするかどうかの判定フィールド
     */
    protected boolean excludesNull = false;

    /**
     * 空白を操作の対象外にするかどうかの判定フィールド
     */
    protected boolean excludesWhitespace = false;

    /**
     * 空白のプロパティ名
     */
    protected String[] whiteSpacePropertyNames = EMPTY_STRING_ARRAY;

    /**
     * prefixプロパティ名
     */
    protected String[] prefixPropertyNames = EMPTY_STRING_ARRAY;

    /**
     * ネストビーンプロパティ名
     */
    protected String[] nestBeanPropertyNames = EMPTY_STRING_ARRAY;

    /**
     * プロパティ退避用Map(prifixプロパティとネストビーン)
     */
    protected Map<String, Object> restorePropertyMap = new HashMap<String, Object>();

    /**
     * プロパティのデリミタ
     */
    protected String propertyDelimiter = ",";

    /**
     * プレフィックス
     */
    protected String prefix;

    /**
     * BeanとMapの互換性の判断
     */
    protected String beanMapExchange;

    /**
     * JavaBeanのデリミタ
     */
    protected char beanDelimiter = '$';

    /**
     * Mapのデリミタ
     */
    protected char mapDelimiter = '.';

    /**
     * CharSequenceの配列をStringの配列に変換する。
     *
     * @param charSequenceArray
     *            CharSequenceの配列
     * @return Stringの配列
     */
    protected String[] toStringArray(CharSequence[] charSequenceArray) {
        int length = charSequenceArray.length;
        String[] stringArray = new String[length];
        for (int index = 0; index < length; index++) {
            stringArray[index] = charSequenceArray[index].toString();
        }
        return stringArray;
    }

    /**
     * 操作の対象に含めるプロパティ名を指定する。
     *
     * @param propertyNames プロパティ名の配列
     * @return このインスタンス自身
     */
    @SuppressWarnings("unchecked")
    public S includes(CharSequence... propertyNames) {
        this.includePropertyNames = toStringArray(propertyNames);
        return (S) this;
    }

    /**
     * 操作の対象に含めないプロパティ名を指定する。
     *
     * @param propertyNames
     *            プロパティ名の配列
     * @return このインスタンス自身
     */
    @SuppressWarnings("unchecked")
    public S excludes(CharSequence... propertyNames) {
        this.excludePropertyNames = toStringArray(propertyNames);
        return (S) this;
    }

    /**
     * null値のプロパティを操作の対象外にする。<br>
     * <br>null値をMapにコピーする場合、mapで該当項目のkeyを作成し、valueをnullにする。
     *
     * @return このインスタンス自身
     */
    @SuppressWarnings("unchecked")
    public S excludesNull() {
        this.excludesNull = true;
        return (S) this;
    }

    /**
     * 空白のプロパティを操作の対象外にする。
     *
     * @return このインスタンス自身
     */
    @SuppressWarnings("unchecked")
    public S excludesWhitespace() {
        this.excludesWhitespace = true;
        return (S) this;
    }

    /**
     * プレフィックスを指定する。
     *
     * @param prefix プレフィックス
     * @return このインスタンス自身
     *
     */
    @SuppressWarnings("unchecked")
    public S prefix(CharSequence prefix) {
        this.prefix = prefix.toString();
        return (S) this;
    }

    /**
     * JavaBeansのデリミタを設定する。
     *
     * @param beanDelimiter JavaBeansのデリミタ
     * @return このインスタンス自身
     */
    @SuppressWarnings("unchecked")
    public S beanDelimiter(char beanDelimiter) {
        this.beanDelimiter = beanDelimiter;
        return (S) this;
    }

    /**
     * Mapのデリミタを設定します。
     *
     * @param mapDelimiter Mapのデリミタ
     * @return このインスタンス自身
     */
    @SuppressWarnings("unchecked")
    public S mapDelimiter(char mapDelimiter) {
        this.mapDelimiter = mapDelimiter;
        return (S) this;
    }

    /**
     * コピー対象プロパティであるかどうかを返却する。
     *
     * @param name プロパティ名
     * @return 対象のプロパティかどうか
     */
    protected boolean isTargetProperty(String name) {
        if (prefix != null && !name.startsWith(prefix)) {
            return false;
        }
        if (includePropertyNames.length > 0) {
            for (String s : includePropertyNames) {
                if (s.equals(name)) {
                    for (String s2 : excludePropertyNames) {
                        if (s2.equals(name)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        if (excludePropertyNames.length > 0) {
            for (String s : excludePropertyNames) {
                if (s.equals(name)) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    /**
     * 対象の空白プロパティの名の配列を取得する。
     *
     * @param obj オブジェクト
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void getWhitespaceProperties(Object obj) {

        StringBuilder whiteSpacePropertyName = new StringBuilder();

        if (obj instanceof Map) {// コピー元はMapの場合
            Map mapObj = (Map) obj;
            for (Iterator<String> i = mapObj.keySet().iterator(); i.hasNext();) {
                String propertyName = i.next();

                Object value = mapObj.get(propertyName);
                if (value instanceof String && ((String) value).trim().length() == 0) {
                    if (whiteSpacePropertyName.length() > 0) {
                        whiteSpacePropertyName.append(propertyDelimiter);
                    }
                    whiteSpacePropertyName.append(propertyName);//空白のプロパティの名称を保存
                }
            }
        } else {// コピー元はBeanの場合
            final PropertyDescriptor[] srcPds = BeanUtil.getPropertyDescriptors(obj.getClass());

            for (PropertyDescriptor pd : srcPds) {
                final Method getter = pd.getReadMethod();
                if (getter == null) {
                    continue;
                }
                try {
                    final Object val = getter.invoke(obj);

                    if (((String) val).trim().length() == 0) {
                        final String propertyName = pd.getName();
                        if (whiteSpacePropertyName.length() > 0) {
                            whiteSpacePropertyName.append(propertyDelimiter);
                        }
                        whiteSpacePropertyName.append(propertyName);//空白のプロパティの名称を保存
                    }
                } catch (Exception e) {
                    LOGGER.logDebug(
                            "An error occurred while reading to the property :" + pd.getName());
                }
            }
        }
        whiteSpacePropertyNames = whiteSpacePropertyName.toString().split(propertyDelimiter);//空白のプロパティの名称を配列に変換

    }

    /**
     * NablarchのBeanUtilのコピーのオプションを設定する。<br>
     * <br>
     * コピーのオプションについて<br>
     *     -->exclude,include両方設定された場合、excludeの設定は優先とします<br>
     *     -->includeする項目のみコピー対象とします<br>
     * <br>
     * 以下の流れでコピーのオプションを設定する。<br>
     * <br>
     * ・excludesNullを設定された場合、CopyOptionsにexcludesNullを設定する。<br>
     * ・プレフィックスを設定された場合、include指定でprefix付いていないプロパティをコピー対象から排除する。<br>
     * ・プレフィックスを設定されていない場合、includePropertyNamesをCopyOptionsのincludesに設定する。<br>
     * ・exclude項目、空白でexclude項目(excludesWhitespace指定した場合)をCopyOptionsのexcludesに設定する。<br>
     * ・prefix付いている項目とnestBean項目のコピーは個別実装するため、CopyOptionsのexcludesに設定する。<br>
     * <br>
     * ※nestBean（ネストビーン）とはBeanのプロパティに別のBeanが設定されているもの。<br>
     *
     */
    private void buildCopyOptions() {
        CopyOptions.Builder copyOptionBuilder = CopyOptions.options();

        if (excludesNull) {//excludesNullを設定された場合、CopyOptionsにexcludesNullを設定
            copyOptionBuilder = copyOptionBuilder.excludesNull();
        }

        if (!StringUtil.isNullOrEmpty(prefixPropertyNames)) {//プレフィックスを設定された場合、includesオプション指定でprefix項目以外の項目を排除する
            copyOptionBuilder = copyOptionBuilder.includes(prefixPropertyNames);
        } else {
            copyOptionBuilder = copyOptionBuilder.includes(includePropertyNames);
        }

        // exclude項目、空白でexclude項目、prefix,nestBeanを退避で、exclude項目をコピーオプションに設定
        copyOptionBuilder = copyOptionBuilder.excludes(excludePropertyNames).excludes(whiteSpacePropertyNames)
                .excludes(prefixPropertyNames).excludes(nestBeanPropertyNames);

        this.copyOptions = copyOptionBuilder.build();

    }

    /**
     * 項目(prefix,ネストビーン)に対し、一時的に退避(最後に再コピー)<br>
     * ※Nablarchの{@link BeanUtil}はプレフィックスとネストビーンサポートしないため、コピーするまえ、
     * プレフィックス付いている項目とネストビーンを一時退避して(Mapに保存に保存)、{@link BeanUtil}コピーした後、独自でコピーする。<br>
     * ※nestBean（ネストビーン）とはBeanのプロパティに別のBeanが設定されているもの
     *
     * @param src ソース
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void backProperty(Object src) {

        StringBuilder nestBeanPropertyName = new StringBuilder();
        StringBuilder prefixPropertyName = new StringBuilder();

        if (src instanceof Map) {// コピー元はMapの場合
            Map mapObj = (Map) src;

            for (Iterator<String> i = mapObj.keySet().iterator(); i.hasNext();) {
                String propertyName = i.next();
                Object val = mapObj.get(propertyName);

                if (val == null) {
                    continue;
                }

                Class propertyType = val.getClass();

                if (prefix != null && propertyName.startsWith(prefix)) {

                    if (prefixPropertyName.length() > 0) {
                        prefixPropertyName.append(propertyDelimiter);
                    }
                    prefixPropertyName.append(propertyName);// prefix付いているプロパティの名称を保存

                    restorePropertyMap.put(propertyName, val);// prefix付いているプロパティを一時退避するMapに保存

                } else if (prefix == null && isTargetProperty(propertyName)
                        && !propertyType.isPrimitive()
                        && !Number.class.isAssignableFrom(propertyType)
                        && !java.util.Date.class.isAssignableFrom(propertyType)
                        && !Boolean.class.isAssignableFrom(propertyType)
                        && !String.class.equals(propertyType)
                        && !java.util.Calendar.class.isAssignableFrom(propertyType)) {//プロパティはビーンの場合

                    if (nestBeanPropertyName.length() > 0) {
                        nestBeanPropertyName.append(propertyDelimiter);
                    }
                    nestBeanPropertyName.append(propertyName);// ネストビーンの名称を保存

                    restorePropertyMap.put(propertyName, val);// ネストビーンを一時退避するMapに保存
                }
            }
        } else {// コピー元はMap以外の場合（Bean）
            final PropertyDescriptor[] srcPds = BeanUtil.getPropertyDescriptors(src.getClass());

            for (PropertyDescriptor pd : srcPds) {
                String propertyName = pd.getName();
                Class propertyType = pd.getPropertyType();

                if (prefix != null && propertyName.startsWith(prefix)) {

                    if (prefixPropertyName.length() > 0) {
                        prefixPropertyName.append(propertyDelimiter);
                    }
                    prefixPropertyName.append(propertyName);// prefix付いているプロパティの名称を保存

                    final Method getter = pd.getReadMethod();
                    if (getter == null) {
                        continue;
                    }

                    Object val;
                    try {
                        val = getter.invoke(src);

                        restorePropertyMap.put(propertyName, val);// prefix付いているプロパティを一時退避するMapに保存
                    } catch (Exception e) {

                        LOGGER.logDebug(
                                "An error occurred while reading to the property :" + pd.getName());
                    }

                } else if (prefix == null && isTargetProperty(propertyName)
                        && !propertyType.isPrimitive()
                        && !Number.class.isAssignableFrom(propertyType)
                        && !java.util.Date.class.isAssignableFrom(propertyType)
                        && !Boolean.class.isAssignableFrom(propertyType)
                        && !String.class.equals(propertyType)
                        && !java.util.Calendar.class.isAssignableFrom(propertyType)) {//プロパティはビーンの場合

                    if (nestBeanPropertyName.length() > 0) {
                        nestBeanPropertyName.append(propertyDelimiter);
                    }
                    nestBeanPropertyName.append(propertyName);// ネストビーンの名称を保存

                    final Method getter = pd.getReadMethod();
                    if (getter == null) {
                        continue;
                    }

                    Object val;
                    try {
                        val = getter.invoke(src);

                        restorePropertyMap.put(propertyName, val);// ネストビーンを一時退避するMapに保存
                    } catch (Exception e) {

                        LOGGER.logDebug(
                                "An error occurred while reading to the property :" + pd.getName());
                    }
                }
            }
        }

        prefixPropertyNames = prefixPropertyName.toString().split(propertyDelimiter);// prefix付いているプロパティの名称の配列を作成
        nestBeanPropertyNames = nestBeanPropertyName.toString().split(propertyDelimiter);// ネストビーンのプロパティの名称の配列を作成
    }

    /**
     * 退避したプロパティをコピーする。
     *
     * @param dest
     *            コピー先
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void afterCopyForRestoreProperty(Object dest) {

        //一時退避した項目は排除項目である場合、排除する
        if (excludePropertyNames.length != 0) {
            for (int i = 0; i < excludePropertyNames.length; i++) {
                restorePropertyMap.remove(excludePropertyNames[i]);
            }
        }

        // 一時退避したデータをコピー先にコピーする
        for (Iterator<String> j = restorePropertyMap.keySet().iterator(); j.hasNext();) {

            String restorePropertyName = j.next();

            String destPropertyName;

            if ("BeanToMap".equals(beanMapExchange)) {
                destPropertyName = trimPrefix(restorePropertyName.replace(beanDelimiter, mapDelimiter));
            } else if ("MapToBean".equals(beanMapExchange)) {
                destPropertyName = trimPrefix(restorePropertyName.replace(mapDelimiter, beanDelimiter));
            } else {
                destPropertyName = trimPrefix(restorePropertyName);
            }

            Object value = restorePropertyMap.get(restorePropertyName);

            if (this.excludesNull && null == value) {//excludesNullの適用
                continue;
            }

            if (value instanceof String && this.excludesWhitespace && ((String) value).trim().length() == 0) {//excludesWhitespaceの適用
                continue;
            }

            if (dest instanceof Map) {
                Map mapObj = (Map) dest;

                mapObj.put(destPropertyName, value);
            } else {

                try {
                    BeanUtil.setProperty(dest, destPropertyName, value);
                } catch (Exception e) {
                    // propertyNameに対応するプロパティが定義されていない場合
                    continue;
                }
            }
        }
    }

    /**
     * nablarchのBeanUtil.createMapAndCopyを利用してコピーした結果を、コピー先のMapに渡す。
     *
     * @param src
     *            コピー元（NablarchのBeanUtilでコピーした結果）
     * @param dest
     *            コピー先
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void afterCopyForMap(Map src, Map dest) {

        //Nablarchのコピー結果をコピー先に転記
        for (Iterator<String> i = src.keySet().iterator(); i.hasNext();) {
            String srcPropertyName = i.next();
            Object value = src.get(srcPropertyName);
            srcPropertyName = trimPrefix(srcPropertyName.replace(beanDelimiter, mapDelimiter));
            dest.put(srcPropertyName, value);
        }
    }

    /**
     * BeanからBeanにコピーを行う。<br>
     * <br>
     * 以下の流れでコピーを行う。<br>
     * <br>
     * ・excludesWhitespaceを設定された場合、空白のプロパティの名前を配列に保存する。(NablarchのBeanUtilのコピーのオプションを設定する際に、これら空文字のプロパティー名をコピー対象外に設定する。)<br>
     * ・nablarchのBeanUtilコピーできない項目(プレフィックス、ネストビーン)を一時退避する。<br>
     * ・nablarchのコピーオプションを作成する。<br>
     * ・nablarchのBeanUtilを利用して、コピーを行う。<br>
     * ・一時退避した項目をコピー先にコピーする。<br>
     *
     * @param src
     *            コピー元
     * @param dest
     *            コピー先
     */
    protected void copyBeanToBean(Object src, Object dest) {

        if (this.excludesWhitespace) {// 空白のプロパティの名前を配列に保存
            this.getWhitespaceProperties(src);
        }

        this.backProperty(src);//BeanUtilコピーできない項目を一時退避する

        this.buildCopyOptions();// nablarchのコピーオプションを作成

        BeanUtil.copy(src, dest, copyOptions);// nablarchのBeanUtilでコピーを実施

        this.afterCopyForRestoreProperty(dest);// 一時退避した項目をコピー先にコピーする

    }

    /**
     * BeanからMapにコピーを行う。<br>
     * <br>
     * 以下の流れでコピーを行う。<br>
     * <br>
     * ・excludesWhitespaceを設定された場合、空白のプロパティの名前を配列に保存する。(NablarchのBeanUtilのコピーのオプションを設定する際に、これら空文字のプロパティー名をコピー対象外に設定する。)<br>
     * ・nablarchのBeanUtilコピーできない項目(プレフィックス、ネストビーン)を一時退避する。<br>
     * ・excludeNullの場合、excludePropertyを作成する。<br>
     * ・nablarchのコピーオプションを作成する。<br>
     * ・nablarchの{@link BeanUtil#createMapAndCopy}を利用して、コピーを実施してから、一時退避した項目をコピー先にコピーする。<br>
     *
     * @param src
     *            コピー元
     * @param dest
     *            コピー先
     */
    @SuppressWarnings({ "rawtypes" })
    protected void copyBeanToMap(Object src, Map dest) {

        beanMapExchange = "BeanToMap";

        if (this.excludesWhitespace) {//空白のプロパティの名前を配列に保存
            this.getWhitespaceProperties(src);
        }

        this.backProperty(src);//BeanUtilコピーできない項目を一時退避する

        if (excludesNull) {
            StringBuilder excludePropertyName = new StringBuilder();
            final PropertyDescriptor[] srcPds = BeanUtil.getPropertyDescriptors(src.getClass());
            for (PropertyDescriptor pd : srcPds) {
                String propertyName = pd.getName();

                final Method getter = pd.getReadMethod();
                if (getter == null) {
                    continue;
                }

                Object val;
                try {
                    val = getter.invoke(src);

                    if (val == null) {
                        excludePropertyName.append(propertyName);
                        excludePropertyName.append(propertyDelimiter);
                    }
                } catch (Exception e) {
                    LOGGER.logDebug(
                            "An error occurred while reading to the property :" + pd.getName());
                }
            }
            excludePropertyNames = excludePropertyName.toString().split(propertyDelimiter);
        }

        this.buildCopyOptions();

        this.afterCopyForMap(BeanUtil.createMapAndCopy(src, copyOptions), dest);
        this.afterCopyForRestoreProperty(dest);

    }

    /**
     * MapからBeanにコピーを行う。<br>
     * <br>
     * 以下の流れでコピーを行う。<br>
     * <br>
     *   ・excludesWhitespaceを設定された場合、空白のプロパティの名前を配列に保存する。(NablarchのBeanUtilのコピーのオプションを設定する際に、これら空文字のプロパティー名をコピー対象外に設定する。)<br>
     *   ・nablarchのBeanUtilコピーできない項目(プレフィックス、ネストビーン)を一時退避するする。<br>
     *   ・excludeNullの場合、excludePropertyを作成する。<br>
     *   ・nablarchのコピーオプションを作成する。<br>
     *   ・nablarchの{@link BeanUtil#copy}を利用して、コピーを実施してから、一時退避した項目をコピー先にコピーする。<br>
     *
     *
     * @param src
     *            コピー元
     * @param dest
     *            コピー先
     */
    protected void copyMapToBean(Map<String, ?> src, Object dest) {

        beanMapExchange = "MapToBean";

        if (this.excludesWhitespace) {
            this.getWhitespaceProperties(src);
        }

        this.backProperty(src);

        if (excludesNull) {
            StringBuilder excludePropertyName = new StringBuilder();
            for (Iterator<String> i = src.keySet().iterator(); i.hasNext();) {
                String srcPropertyName = i.next();
                Object value = src.get(srcPropertyName);

                if (value == null) {
                    excludePropertyName.append(srcPropertyName);
                    excludePropertyName.append(propertyDelimiter);
                }

            }
            excludePropertyNames = excludePropertyName.toString().split(propertyDelimiter);
        }

        this.buildCopyOptions();

        BeanUtil.copy(dest.getClass(), dest, src, copyOptions);

        this.afterCopyForRestoreProperty(dest);
    }

    /**
     * MapからMapにコピーを行う。<br>
     * 各オプション（excludesWhitespace,excludesNull,includeProperty,excludeProperty）を直接適用する。
     *
     * @param src
     *            コピー元
     * @param dest
     *            コピー先
     */
    protected void copyMapToMap(Map<String, Object> src,
            Map<String, Object> dest) {

        this.buildCopyOptions();

        for (Iterator<String> i = src.keySet().iterator(); i.hasNext();) {
            String srcPropertyName = i.next();
            if (!isTargetProperty(srcPropertyName)) {
                continue;
            }
            String destPropertyName = trimPrefix(srcPropertyName);
            Object value = src.get(srcPropertyName);
            if (value instanceof String && excludesWhitespace
                    && ((String) value).trim().length() == 0) {
                continue;
            }
            if (value == null && excludesNull) {
                continue;
            }
            if (value != null) {
                value = ConversionUtil.convert(value.getClass(), value);
            }
            dest.put(destPropertyName, value);
        }
    }

    /**
     * BeanからBeanにコピーを行う。<br>
     * <br>
     * 以下の流れでコピーを行う。<br>
     * <br>
     *   ・excludesWhitespaceを設定された場合、空白のプロパティの名前を配列に保存する。(NablarchのBeanUtilのコピーのオプションを設定する際に、これら空文字のプロパティー名をコピー対象外に設定する。)<br>
     *   ・nablarchのBeanUtilコピーできない項目(プレフィックス、ネストビーン)を一時退避する。<br>
     *   ・nablarchのコピーオプションを作成する。<br>
     *   ・nablarchの{@link BeanUtil#createAndCopy}を利用して、コピーを実施する。<br>
     *   ・一時退避した項目をコピー先にコピーする。<br>
     * <br>
     *
     * @param src コピー元
     * @param destClass コピー先
     * @param <T> パラメータ型
     * @return dest
     */
    protected <T> T createAndCopyBeanToBean(Object src, Class<T> destClass) {

        if (this.excludesWhitespace) {
            this.getWhitespaceProperties(src);
        }

        this.backProperty(src);

        this.buildCopyOptions();

        T dest = (T) BeanUtil.createAndCopy(destClass, src, copyOptions);

        this.afterCopyForRestoreProperty(dest);

        return dest;

    }

    /**
     * BeanからMap(作成)にコピーを行う。<br>
     * <br>
     * 以下の流れでコピーを行う。<br>
     * <br>
     *   ・excludesWhitespaceを設定された場合、空白のプロパティの名前を配列に保存する。(NablarchのBeanUtilのコピーのオプションを設定する際に、これら空文字のプロパティー名をコピー対象外に設定する。)<br>
     *   ・nablarchのBeanUtilコピーできない項目(プレフィックス、ネストビーン)を一時退避する。<br>
     *   ・excludeNullの場合、excludePropertyを作成。<br>
     *   ・nablarchのコピーオプションを作成する。<br>
     *   ・コピー先を新規作成する。<br>
     *   ・nablarchの{@link BeanUtil#createMapAndCopy}を利用して、コピーを実施してから、一時退避した項目をコピー先にコピーする。<br>
     *
     *
     * @param src
     *            コピー元
     * @param destClass
     *            コピー先
     * @param <T> パラメータ型
     * @return T
     */
    @SuppressWarnings("unchecked")
    protected <T> T createAndCopyBeanToMap(Object src, Class<T> destClass) {

        beanMapExchange = "BeanToMap";

        if (this.excludesWhitespace) {
            this.getWhitespaceProperties(src);
        }

        this.backProperty(src);

        if (excludesNull) {
            StringBuilder excludePropertyName = new StringBuilder();
            final PropertyDescriptor[] srcPds = BeanUtil.getPropertyDescriptors(src.getClass());
            for (PropertyDescriptor pd : srcPds) {
                String propertyName = pd.getName();

                final Method getter = pd.getReadMethod();
                if (getter == null) {
                    continue;
                }

                Object val;
                try {
                    val = getter.invoke(src);

                    if (val == null) {
                        excludePropertyName.append(propertyName);
                        excludePropertyName.append(propertyDelimiter);
                    }
                } catch (Exception e) {
                    LOGGER.logDebug(
                            "An error occurred while reading to the property :" + pd.getName());
                }
            }
            excludePropertyNames = excludePropertyName.toString().split(propertyDelimiter);
        }

        this.buildCopyOptions();

        Map<String, Object> dest;
        if (BeanMap.class.isAssignableFrom(destClass)) {
            dest = new BeanMap();

        } else {
            dest = new HashMap<String, Object>();
        }

        Map<String, Object> srcMap = BeanUtil.createMapAndCopy(src, copyOptions);

        this.afterCopyForMap(srcMap, dest);
        this.afterCopyForRestoreProperty(dest);

        return (T) dest;
    }

    /**
     * MapからMapにコピーを行う。<br>
     * <br>
     * 以下の流れでコピーを行う。<br>
     * <br>
     *   ・コピー先を新規作成する。<br>
     *   ・{@link #copyMapToMap}を利用して、コピーを実施してから、一時退避した項目をコピー先にコピーする。<br>
     *
     *
     * @param src
     *            コピー元
     * @param destClass
     *            コピー先
     * @param <T> パラメータ型
     * @return T
     */
    @SuppressWarnings({ "unchecked" })
    protected <T> T createAndCopyMapToMap(Map<String, Object> src, Class<T> destClass) {
        // BeanMap対応
        Map<String, Object> dest;
        if (BeanMap.class.isAssignableFrom(destClass)) {
            dest = new BeanMap();

        } else {
            dest = new HashMap<String, Object>();
        }

        copyMapToMap(src, dest);

        return (T) dest;
    }

    /**
     * MapからBeanにコピーを行う。<br>
     * <br>
     * 以下の流れでコピーを行う。<br>
     * <br>
     *   ・excludesWhitespaceを設定された場合、空白のプロパティの名前を配列に保存する。(NablarchのBeanUtilのコピーのオプションを設定する際に、これら空文字のプロパティー名をコピー対象外に設定する。)<br>
     *   ・nablarchのBeanUtilコピーできない項目(プレフィックス、ネストビーン)を一時退避するする。<br>
     *   ・excludeNullの場合、excludePropertyを作成する。<br>
     *   ・nablarchのコピーオプションを作成する。<br>
     *   ・nablarchの{@link BeanUtil#createAndCopy}を利用して、コピーを実施する。<br>
     *   ・一時退避した項目をコピー先にコピーする。<br>
     * <br>
     *
     * @param src
     *            コピー元
     * @param destClass
     *            コピー先
     * @param <T> パラメータ型
     * @return T
     */
    protected <T> T createAndcopyMapToBean(Map<String, ?> src, Class<T> destClass) {

        beanMapExchange = "MapToBean";

        if (this.excludesWhitespace) {
            getWhitespaceProperties(src);
        }

        backProperty(src);

        if (excludesNull) {
            StringBuilder excludePropertyName = new StringBuilder();
            for (Iterator<String> i = src.keySet().iterator(); i.hasNext();) {
                String srcPropertyName = i.next();
                Object value = src.get(srcPropertyName);

                if (value == null) {
                    excludePropertyName.append(srcPropertyName);
                    excludePropertyName.append(propertyDelimiter);
                }

            }
            excludePropertyNames = excludePropertyName.toString().split(propertyDelimiter);
        }

        this.buildCopyOptions();

        T dest = (T) BeanUtil.createAndCopy(destClass, src, copyOptions);

        this.afterCopyForRestoreProperty(dest);

        return dest;
    }

    /**
     * プレフィックスを削る。
     * ※仕様はコピー先はプレフィックスを削りますため
     *
     * @param propertyName
     *            プロパティ名
     * @return 削った結果
     */
    protected String trimPrefix(String propertyName) {
        if (prefix == null) {
            return propertyName;
        }
        if (!propertyName.startsWith(prefix)) {
            return propertyName;
        }
        return propertyName.substring(prefix.length());

    }

}
