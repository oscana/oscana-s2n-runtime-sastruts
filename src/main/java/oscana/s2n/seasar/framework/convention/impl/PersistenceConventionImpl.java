/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.convention.impl.PersistenceConventionImpl
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/convention/impl/PersistenceConventionImpl.java
 *
 * 上記ファイルを取り込み、修正を加えた。
 *
 * Copyright 2020 TIS Inc.
 *
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package oscana.s2n.seasar.framework.convention.impl;

import oscana.s2n.seasar.framework.convention.PersistenceConvention;
import oscana.s2n.seasar.framework.util.StringUtil;

/**
 * {@link PersistenceConvention}の実装するクラス。
 *
 * @author higa
 *
 */
public class PersistenceConventionImpl implements PersistenceConvention {

	/** 無視するテーブルのprefix */
    private String ignoreTablePrefix;

    /** 変換フラグ */
    private boolean noNameConversion = false;

    /**
     * テーブル名をエンティティ名に変換する。
     *
     * @param tableName
     *            テーブル名
     * @return エンティティ名
     */
    public String fromTableNameToEntityName(String tableName) {
        if (tableName == null) { throw new NullPointerException("tableName"); }
        if (noNameConversion) {
            return tableName;
        }
        return StringUtil.camelize(StringUtil.trimPrefix(tableName,
                ignoreTablePrefix));
    }

    /**
     * エンティティ名をテーブル名に変更する。
     *
     * @param entityName
     *            エンティティ名
     * @return テーブル名
     */
    public String fromEntityNameToTableName(String entityName) {
        if (entityName == null) { throw new NullPointerException("entityName"); }
        if (noNameConversion) {
            return entityName;
        }
        String tableName = StringUtil.decamelize(entityName);
        if (ignoreTablePrefix != null) {
            tableName = ignoreTablePrefix + tableName;
        }
        return tableName;
    }

    /**
     * カラム名をプロパティ名に変換する。
     *
     * @param columnName
     *            カラム名
     * @return プロパティ名
     */
    public String fromColumnNameToPropertyName(String columnName) {
        if (columnName == null) { throw new NullPointerException("columnName"); }
        if (noNameConversion) {
            return columnName;
        }
        return StringUtil.decapitalize(StringUtil.camelize(columnName));
    }

    /**
     * プロパティ名をカラム名に変換する。
     *
     * @param propertyName
     *            プロパティ名
     * @return カラム名
     */
    public String fromPropertyNameToColumnName(String propertyName) {
        if (propertyName == null) { throw new NullPointerException("propertyName"); }
        if (noNameConversion) {
            return propertyName;
        }
        return StringUtil.decamelize(propertyName);
    }

    /**
     * 無視するテーブルの<code>prefix</code>を返す。
     *
     * @return 無視するテーブルの<code>prefix</code>
     */
    public String getIgnoreTablePrefix() {
        return ignoreTablePrefix;
    }

    /**
     * 無視するテーブルの<code>prefix</code>を設定する。
     *
     * @param ignoreTablePrefix 無視するテーブルの<code>prefix</code>
     */
    public void setIgnoreTablePrefix(String ignoreTablePrefix) {
        this.ignoreTablePrefix = ignoreTablePrefix;
    }

    /**
     * 名前を変換しないかどうかを返す。
     *
     * @return 名前を変換しないかどうか
     */
    public boolean isNoNameConversion() {
        return noNameConversion;
    }

    /**
     * 名前を変換しないかどうかを設定する。
     *
     * @param noNameConversion 名前を変換しないかどうか
     */
    public void setNoNameConversion(boolean noNameConversion) {
        this.noNameConversion = noNameConversion;
    }

}