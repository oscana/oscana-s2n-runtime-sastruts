/*
 * 取り込み元
 *    ライブラリ名：     sa-struts
 *    クラス名：         org.seasar.struts.taglib.S2Functions
 *    ソースリポジトリ： https://github.com/seasarorg/sa-struts/blob/master/src/main/java/org/seasar/struts/taglib/S2Functions.java
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
package oscana.s2n.seasar.struts.taglib;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import nablarch.core.util.ObjectUtil;
import nablarch.core.util.StringUtil;
import oscana.s2n.seasar.framework.container.OscanaContainer;
import oscana.s2n.seasar.framework.container.factory.OscanaSingletonContainerFactory;
import oscana.s2n.seasar.struts.util.ActionUtil;
import oscana.s2n.seasar.struts.util.RequestUtil;
import oscana.s2n.seasar.struts.util.ResponseUtil;
import oscana.s2n.seasar.struts.util.RoutingUtil;

/**
 * ファンクションクラス。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・uメソッド：入力引数がnullの場合、そのまま空文字を返却する。エンコードをUTF-8に設定する。<br>
 *
 * @author higa
 *
 */
public class OscanaFunctions {

    private static final int HIGHEST_SPECIAL = '>';

    /** 改行コード */
    private static String BR = "<br />";

    /**スペース */
    private static String NBSP = "&nbsp;";

    private static char[][] specialCharactersRepresentation = new char[HIGHEST_SPECIAL + 1][];

    static {
        specialCharactersRepresentation['&'] = "&amp;".toCharArray();
        specialCharactersRepresentation['<'] = "&lt;".toCharArray();
        specialCharactersRepresentation['>'] = "&gt;".toCharArray();
        specialCharactersRepresentation['"'] = "&#034;".toCharArray();
        specialCharactersRepresentation['\''] = "&#039;".toCharArray();
    }

    /**
     * HTMLをエスケープする。
     *
     * @param input
     *            入力値
     * @return エスケープした結果
     */
    public static String h(Object input) {
        if (input == null) {
            return "";
        }
        String str = "";
        if (input.getClass().isArray()) {
            Class<?> clazz = input.getClass().getComponentType();
            if (clazz == String.class) {
                str = Arrays.toString((Object[]) input);
            } else if (clazz == boolean.class) {
                str = Arrays.toString((boolean[]) input);
            } else if (clazz == int.class) {
                str = Arrays.toString((int[]) input);
            } else if (clazz == long.class) {
                str = Arrays.toString((long[]) input);
            } else if (clazz == byte.class) {
                str = Arrays.toString((byte[]) input);
            } else if (clazz == short.class) {
                str = Arrays.toString((short[]) input);
            } else if (clazz == float.class) {
                str = Arrays.toString((float[]) input);
            } else if (clazz == double.class) {
                str = Arrays.toString((double[]) input);
            } else if (clazz == char.class) {
                str = Arrays.toString((char[]) input);
            } else {
                str = Arrays.toString((Object[]) input);
            }
        } else {
            str = input.toString();
        }
        return escape(str);
    }

    /**
     * 文字列をHTMLエスケープする。
     *
     * @param buffer
     *            文字列
     * @return エスケープした結果
     */
    public static String escape(String buffer) {
        int start = 0;
        int length = buffer.length();
        char[] arrayBuffer = buffer.toCharArray();
        StringBuilder escapedBuffer = null;

        for (int i = 0; i < length; i++) {
            char c = arrayBuffer[i];
            if (c <= HIGHEST_SPECIAL) {
                char[] escaped = specialCharactersRepresentation[c];
                if (escaped != null) {
                    if (start == 0) {
                        escapedBuffer = new StringBuilder(length + 5);
                    }
                    if (start < i) {
                        escapedBuffer.append(arrayBuffer, start, i - start);
                    }
                    start = i + 1;
                    escapedBuffer.append(escaped);
                }
            }
        }
        if (start == 0) {
            return buffer;
        }
        if (start < length) {
            escapedBuffer.append(arrayBuffer, start, length - start);
        }
        return escapedBuffer.toString();
    }

    /**
     * URLをエスケープする。
     *
     * @param input
     *            入力値
     * @return エスケープした結果
     */
    public static String u(String input) {
        return urlEncode(input);
    }

    /**
     * URLをエスケープする。
     * @param value 入力値
     * @return 変換後のString
     */
    private static String urlEncode(String value) {
        return urlEncode(value, "UTF-8");
    }

    /**
     * URLをエスケープする。
     * @param value 入力値
     * @param encode エンコーディング・スキーム
     * @return 変換後のString
     */
    private static String urlEncode(String value, String encode) {
        if (value == null) {
            return "";
        } else {
            try {
                return URLEncoder.encode(value, encode);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * URLを計算する。
     *
     * @param input
     *            入力値
     * @return エスケープした結果
     */
    public static String url(String input) {
        String contextPath = RequestUtil.getRequest().getContextPath();
        StringBuilder sb = new StringBuilder();
        if (contextPath.length() > 1) {
            sb.append(contextPath);
        }
        if (StringUtil.isNullOrEmpty(input)) {
            sb.append(ActionUtil.calcActionPath());
        } else if (!input.startsWith("/")) {
            sb.append(ActionUtil.calcActionPath()).append(input);
        } else {
            List<String> objs = StringUtil.split(input, "/");
            String[] names = new String[objs.size()];
            objs.toArray(names);

            OscanaContainer container = OscanaSingletonContainerFactory.getContainer();
            StringBuilder sb2 = new StringBuilder(50);
            String input2 = input;
            for (int i = 0; i < names.length; i++) {
                if (container.hasComponentDef(sb2 + names[i] + "Action")) {
                    String actionPath = RoutingUtil.getActionPath(names, i);
                    String paramPath = RoutingUtil.getParamPath(names, i + 1);
                    if (StringUtil.isNullOrEmpty(paramPath)) {
                        input2 = actionPath + "/";
                        break;
                    }
                }
                sb2.append(names[i] + "_");
            }
            sb.append(input2);
        }

        return ResponseUtil.getResponse().encodeURL(sb.toString());
    }

    /**
     * 日付に変換する。
     * <br>
     * 移植内容の変更点：<br>
     * <br>
     * ・関数のシグネチャーをjava.util.Date date(java.lang.Object, java.lang.String)に変更<br>
     * ・パラメータinputがnullの場合、nullを返す<br>
     * ・パラメータinputがjava.util.Dateの場合、inputを返す<br>
     *
     * @param input
     *            入力値
     * @param pattern
     *            パターン
     * @return 変換した結果
     */
    public static Date date(Object input, String pattern) {
        if (input == null) {
            return null;
        }
        if (input instanceof Date) {//java.util.Dateの場合、そのまま戻す
            return (Date) input;
        }

        String strInput = String.valueOf(input);
        if (StringUtil.isNullOrEmpty(strInput)) {
            return null;
        }
        if (StringUtil.isNullOrEmpty(pattern)) {
            throw new NullPointerException("pattern");
        }
        try {

            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.parse(strInput);
        } catch (ParseException ea) {
            throw new RuntimeException(ea);
        }
    }

    /**
     * 数値に変換する。
     *
     * @param input
     *            入力値
     * @param pattern
     *            パターン
     * @return 変換した結果
     */
    public static Number number(String input, String pattern) {
        if (StringUtil.isNullOrEmpty(input)) {
            return null;
        }
        if (StringUtil.isNullOrEmpty(pattern)) {
            throw new NullPointerException("pattern");
        }
        try {
            DecimalFormat format = new DecimalFormat(pattern);
            return format.parse(input);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 改行をbrタグに変換する。
     *
     * @param input
     *            入力値
     * @return 変換した結果
     */
    public static String br(String input) {
        if (StringUtil.isNullOrEmpty(input)) {
            return "";
        }
        return input.replaceAll("\r\n", BR).replaceAll("\r", BR).replaceAll(
                "\n", BR);
    }

    /**
     * 空白を&nbsp;に変換する。
     *
     * @param input
     *            入力値
     * @return 変換した結果
     */
    public static String nbsp(String input) {
        if (StringUtil.isNullOrEmpty(input)) {
            return "";
        }
        return input.replaceAll(" ", NBSP);
    }

    /**
     * 値をラベルに変換する。
     *
     * @param value
     *            値
     * @param dataList
     *            JavaBeansあるいはMapのリスト
     * @param valueName
     *            値用のプロパティ名
     * @param labelName
     *            ラベル用のプロパティ名
     *
     * @return ラベル
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String label(Object value, List dataList, String valueName,
            String labelName) {
        if (valueName == null) {
            throw new NullPointerException("valueName");
        }
        if (labelName == null) {
            throw new NullPointerException("labelName");
        }
        if (dataList == null) {
            throw new NullPointerException("dataList");
        }
        for (Object o : dataList) {
            if (o instanceof Map) {
                Map<String, Object> m = (Map<String, Object>) o;
                Object v = m.get(valueName);
                if (equals(value, v)) {
                    return (String) m.get(labelName);
                }
            } else {
                Object v = ObjectUtil.getProperty(o, valueName);
                if (equals(value, v)) {
                    return ObjectUtil.getProperty(o, labelName).toString();
                }
            }
        }
        return "";
    }

    /**
     * 2つのオブジェクトの値が等しいかどうかを返却する。
     *
     * @param o1
     *            オブジェクト1
     * @param o2
     *            オブジェクト2
     * @return 2つのオブジェクトの値が等しいかどうか
     */
    private static boolean equals(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if (o1 == null) {
            if (o2 instanceof String && StringUtil.isNullOrEmpty((String) o2)) {
                return true;
            }
            return false;
        }
        if (o2 == null) {
            if (o1 instanceof String && StringUtil.isNullOrEmpty((String) o1)) {
                return true;
            }
            return false;
        }
        if (o1.getClass() == o2.getClass()) {
            return o1.equals(o2);
        }
        return o1.toString().equals(o2.toString());
    }
}
