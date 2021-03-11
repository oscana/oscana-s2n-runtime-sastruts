/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.container.S2Container
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/container/S2Container.java
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
package oscana.s2n.seasar.framework.container;

import nablarch.fw.dicontainer.nablarch.Containers;
import oscana.s2n.seasar.framework.util.tiger.ReflectionUtil;

/**
 * コンテナからのコンポーネント取得、検索などを行うコンポーネントを管理するクラス。<br>
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 * ・NablarchのDIコンテナを使用するように変更。<br>
 *
 * @author higa
 * @author vestige &amp; SeasarJavaDoc Committers
 */
public class OscanaContainer {

    /**
     * 指定したクラスがDIコンテナに登録されているか調べる。
     * @param className クラス名
     * @return 登録されておればtrue
     */
    public boolean hasComponentDef(String className) {
        try {
            Class<Object> clazz = ReflectionUtil.forName(className);
            Object o = Containers.get().getComponent(clazz);
            if ( o != null) {
                return true;
            }else {
                return false;

            }
        }catch(Exception e) {
            if(e.getCause() instanceof ClassNotFoundException) {
                return false;
            }
            throw e;
        }
    }

    /**
     * 指定されたキーに対応するコンポーネントを返却する。<br>
     * <br>
     * ・キーが文字列の場合、名前が一致するコンポーネントを返却する。<br>
     * ・キーがクラスまたはインターフェースの場合、キーの型に代入可能なコンポーネントを返却する。
     * <br>
     * コンポーネントが見つからない場合は実行時例外を送出する。
     *
     * @param componentKey
     *            コンポーネントを取得するためのキー
     * @return コンポーネント
     */
    public Object getComponent(Object componentKey) {
        if (componentKey instanceof Class<?>) {
            return Containers.get().getComponent((Class<?>) componentKey);
        } else if (componentKey instanceof String) {
            Class<Object> clazz = ReflectionUtil.forName((String) componentKey);
            return Containers.get().getComponent(clazz);
        } else {
            throw new RuntimeException("Component not found. componentKey = [" + componentKey + "]");
        }
    }
}
