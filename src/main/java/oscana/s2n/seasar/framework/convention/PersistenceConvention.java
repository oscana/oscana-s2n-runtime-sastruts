/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.convention.PersistenceConvention
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/convention/PersistenceConvention.java
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
package oscana.s2n.seasar.framework.convention;

/**
 * 永続化層の命名規約を表すインターフェース。
 *
 * @author higa
 *
 */
public interface PersistenceConvention {

    /**
     * テーブル名をエンティティ名に変換する。
     *
     * @param tableName
     *            テーブル名
     * @return エンティティ名
     */
    String fromTableNameToEntityName(String tableName);

    /**
     * エンティティ名をテーブル名に変更する。
     *
     * @param entityName
     *            エンティティ名
     * @return テーブル名
     */
    String fromEntityNameToTableName(String entityName);

    /**
     * カラム名をプロパティ名に変換する。
     *
     * @param columnName
     *            カラム名
     * @return プロパティ名
     */
    String fromColumnNameToPropertyName(String columnName);

    /**
     * プロパティ名をカラム名に変換する。
     *
     * @param propertyName
     *            プロパティ名
     * @return カラム名
     */
    String fromPropertyNameToColumnName(String propertyName);
}
