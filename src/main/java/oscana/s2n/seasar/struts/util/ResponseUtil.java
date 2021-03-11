/*
 * 取り込み元
 *    ライブラリ名：     sa-struts
 *    クラス名：         org.seasar.struts.util.ResponseUtil
 *    ソースリポジトリ： https://github.com/seasarorg/sa-struts/blob/master/src/main/java/org/seasar/struts/util/ResponseUtil.java
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import nablarch.fw.dicontainer.nablarch.Containers;
import nablarch.fw.web.HttpResponse;
import oscana.s2n.common.web.download.S2NStreamResponse;
import oscana.s2n.handler.HttpResourceHolder;

/**
 * レスポンス用のユーティリティクラス。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・getResponseメソッド：{@link nablarch.fw.dicontainer.nablarch.Containers}を利用して レスポンスコンポーネントを返却する。<br>
 *    ・downloadメソッド：{@link S2NStreamResponse}レスポンスを作成し、リソースホルダーに保存する。<br>
 *    ・writeメソッド：{@link S2NStreamResponse}レスポンスに書き込む、リソースホルダーに保存する。<br>
 *
 * @author higa
 *
 */
public final class ResponseUtil {

    private ResponseUtil() {
    }

    /**
     * レスポンスを返却する。
     *
     * @return レスポンス
     */
    public static HttpServletResponse getResponse() {
        HttpResourceHolder resource = Containers.get().getComponent(HttpResourceHolder.class);
        return resource.getHttpServletResponse();
    }

    /**
     * ダウンロードする。
     *
     * @param fileName
     *            ファイル名
     * @param data
     *            ダウンロードするデータ
     */
    public static void download(String fileName, byte[] data) {
        S2NStreamResponse response = new S2NStreamResponse(new ByteArrayInputStream(data));
        response.setContentType("application/octet-stream");
        response.setContentDisposition(fileName);
        HttpResourceHolder resource = Containers.get().getComponent(HttpResourceHolder.class);
        resource.setForcedNextResponse(response);
    }

    /**
     * 指定されたストリームから読み込んで、ダウンロードレスポンスをリソースホルダーに保存する。
     *
     * @param fileName レスポンスとして返されるファイル名
     * @param in ダウンロードさせたいデータ
     */
    public static void download(String fileName, InputStream in) {
        S2NStreamResponse response = new S2NStreamResponse(in);
        response.setContentType("application/octet-stream");
        response.setContentDisposition(fileName);
        HttpResourceHolder resource = Containers.get().getComponent(HttpResourceHolder.class);
        resource.setForcedNextResponse(response);
    }

    /**
     * 指定されたストリームから読み込んで、指定したContentLengthとともにダウンロードレスポンスをリソースホルダーに保存する。
     *
     * @param fileName レスポンスとして返されるファイル名
     * @param in ダウンロードさせたいデータ
     * @param length Content-Length:フィールドの値
     */
    public static void download(String fileName, InputStream in, int length) {
        HttpServletResponse response = getResponse();
        response.setContentLength(length);
        download(fileName, in);
    }

    /**
     * レスポンスにテキストを書き込む。
     *
     * @param text
     *            テキスト
     */
    public static void write(String text) {
        write(text, null, null);
    }

    /**
     * レスポンスにテキストを書き込む。
     *
     * @param text
     *            テキスト
     * @param contentType
     *            コンテントタイプ。 デフォルトはtext/plain。
     */
    public static void write(String text, String contentType) {
        write(text, contentType, null);
    }

    /**
     * レスポンスにテキストを書き込む。
     *
     * @param text
     *            テキスト
     * @param contentType
     *            コンテントタイプ。 デフォルトはtext/plain。
     * @param encoding
     *            エンコーディング。 指定しなかった場合は、リクエストのcharsetEncodingが設定される。
     *            リクエストのcharsetEncodingも指定がない場合は、UTF-8。
     */
    public static void write(String text, String contentType, String encoding) {
        if (contentType == null) {
            contentType = "text/plain";
        }

        if (encoding == null) {
            encoding = RequestUtil.getRequest().getCharacterEncoding();
            if (encoding == null) {
                encoding = "UTF-8";
            }
        }

        HttpResponse outPutRes = new HttpResponse();
        outPutRes.setContentType(contentType + "; charset=" + encoding);
        outPutRes.write(text);
        HttpResourceHolder resource = Containers.get().getComponent(HttpResourceHolder.class);
        resource.setForcedNextResponse(outPutRes);

    }
}