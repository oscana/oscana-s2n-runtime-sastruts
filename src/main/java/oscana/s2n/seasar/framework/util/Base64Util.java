/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.util.Base64Util
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/util/Base64Util.java
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

/**
 * Base64を扱うためのユーティリティクラス。
 *
 * @author higa
 *
 */
public class Base64Util {

    /**
     * Base64でエンコードする。
     *
     * @param inData 入力データ
     * @return エンコードされたデータ
     */
    public static String encode(final byte[] inData) {
        if (inData == null || inData.length == 0) {
            return "";
        }
        return nablarch.core.util.Base64Util.encode(inData);
    }

    /**
     * Base64でエンコードされたデータをデコードする。
     *
     * @param inData 入力データ
     * @return デコードされたデータ
     */
    public static byte[] decode(final String inData) {
        if (inData == null) {
            throw new NullPointerException();
        }
        int length = inData.length();
        if (inData.equals("")) {
            throw new StringIndexOutOfBoundsException("String index out of range:-2");
        }
        if (length%4 != 0) {
            throw new StringIndexOutOfBoundsException("String index out of range:-4");
        }
        return nablarch.core.util.Base64Util.decode(inData);
    }

}