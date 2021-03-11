/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.beans.BeanDesc
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/beans/BeanDesc.java
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
import nablarch.core.beans.BeansException;

/**
 * Bean(JavaBeans)を扱うクラス。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 * ・インターフェースと実装クラスの構成を単一のクラスとして移植する。<br>
 * ・{@link nablarch.core.beans.BeanUtil} を利用して、プロパティの値を取得する。<br>
 *
 * @author higa
 */
public class BeanDesc {

    private Class<?> beanClass;

    /**
     * {@link BeanDesc}を作成する。<br>
     * 既存との互換性を維持するために、BeanDescのオブジェクトを生成できるように追加する。
     *
     * @param beanClass クラス
     */
    public BeanDesc(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * {@link PropertyDesc}を返却する。<br>
     * 既存との互換性を維持するために、
     * プロパティの値を取得できない時、PropertyNotFoundRuntimeException例外を投げるように追加する。
     *
     * @param propertyName プロパティ名
     * @return {@link PropertyDesc}
     */
    public PropertyDesc getPropertyDesc(String propertyName) {
        try {
            BeanUtil.getPropertyDescriptor(beanClass, propertyName);
        } catch (BeansException e) {
            throw new PropertyNotFoundRuntimeException();
        }
        return new PropertyDesc(propertyName);
    }
}