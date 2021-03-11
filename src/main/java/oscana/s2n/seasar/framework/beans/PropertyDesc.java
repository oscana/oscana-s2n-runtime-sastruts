/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.beans.PropertyDesc
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/beans/PropertyDesc.java
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
package oscana.s2n.seasar.framework.beans;

import nablarch.core.beans.BeanUtil;

/**
 * プロパティを扱うクラス。<br>
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・インターフェースと実装クラスの構成を単一のクラスとして移植する。<br>
 *    ・{@link nablarch.core.beans.BeanUtil} を利用して、プロパティの値を取得する。<br>
 *
 * @author higa
 *
 */
public class PropertyDesc {

    /**
     * プロパティー名
     */
    private String propertyName;

    /**
    * {@link PropertyDesc}を作成する。<br>
    * 既存との互換性を維持するために、PropertyDescのオブジェクトを生成できるように追加する。
    *
    * @param propertyName プロパティ名
    */
    public PropertyDesc(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * プロパティの値を返却する。
     * {@link nablarch.core.beans.BeanUtil} を利用して、プロパティの値を取得するように変更する。
     *
     * @param target ターゲット
     * @return プロパティの値
     *
     */
    public final Object getValue(Object target) {
        return BeanUtil.getProperty(target, propertyName);
    }
}
