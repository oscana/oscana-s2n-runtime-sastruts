/*
 * 取り込み元
 *    ライブラリ名：     sa-struts
 *    クラス名：         org.seasar.struts.util.URLEncoderUtil
 *    ソースリポジトリ： https://github.com/seasarorg/sa-struts/blob/master/src/main/java/org/seasar/struts/util/URLEncoderUtil.java
 *
 * 上記ファイルを取り込み、修正を加えた。
 *
 * Copyright 2020 TIS Inc.
 *
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package oscana.s2n.seasar.struts.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import oscana.s2n.seasar.framework.exception.IORuntimeException;

/**
 * {@link URLEncoder}用のユーティリティクラス。
 *
 * @author higa
 *
 */
public class URLEncoderUtil {

    /**
     * URLをエンコードする。
     *
     * @param input
     *            入力値
     * @return エンコードした結果
     */
    public static String encode(String input) {
        String encoding = RequestUtil.getRequest().getCharacterEncoding();
        try {
            return URLEncoder.encode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IORuntimeException(e);
        }
    }
}
