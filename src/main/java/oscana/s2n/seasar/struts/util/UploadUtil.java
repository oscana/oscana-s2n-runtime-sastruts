/*
 * 取り込み元
 *    ライブラリ名：     sa-struts
 *    クラス名：         org.seasar.struts.util.UploadUtil
 *    ソースリポジトリ： https://github.com/seasarorg/sa-struts/blob/master/src/main/java/org/seasar/struts/util/UploadUtil.java
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

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import oscana.s2n.seasar.framework.exception.IORuntimeException;
import oscana.s2n.struts.upload.FormFile;

/**
 * ファイルアップロード用のユーティリティクラス。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・checkSizeLimitメソッド：ファイルサイズ制限については、NablarchではFW側で自動的にチェックされるので、Actionが呼ばれている時点で制限には抵触していないことが確定している。そのため、checkSizeLimitは必ずtrueを返す実装に変更。<br>
 *
 * @author higa
 *
 */
public class UploadUtil {

    /**
     * trueを返却する。
     * @param request リクエスト
     * @return true
     */
    public static boolean checkSizeLimit(HttpServletRequest request) {
        //NablarchではFW側でチェックがかかるのでこの機能は意味がない
        return true;
    }

    /**
     * 書き込むを行う。
     * @param path パス
     * @param formFile formFileオブジェクト
     */
    public static void write(String path, FormFile formFile) {
        if (formFile == null || formFile.getFileSize() == 0) {
            return;
        }
        BufferedOutputStream out = null;
        InputStream in = null;
        try {
            in = formFile.getInputStream();
            out = new BufferedOutputStream(new FileOutputStream(path));
            copy(in, out);
            out.flush();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            try {
                close(in);
            } finally {
                close(out);
            }
        }

    }

    /**
     * {@link InputStream}の内容を {@link OutputStream}にコピーする。
     *
     * @param is 入力ストリーム
     * @param os 出力ストリーム
     * @throws IORuntimeException
     *             {@link IOException}が発生した場合
     */
    private static final void copy(InputStream is, OutputStream os)
            throws IORuntimeException {
        byte[] buf = new byte[8192];
        try {
            int n = 0;
            while ((n = is.read(buf, 0, buf.length)) != -1) {
                os.write(buf, 0, n);
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * {@link InputStream}を閉じる。
     *
     * @param is 入力ストリーム
     * @throws IORuntimeException
     *             {@link IOException}が発生した場合
     * @see InputStream#close()
     */
    private static void close(InputStream is) throws IORuntimeException {
        if (is == null) {
            return;
        }
        try {
            is.close();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * {@link OutputStream}を閉じる。
     *
     * @param out 出力ストリーム
     * @throws IORuntimeException
     *             {@link IOException}が発生した場合
     */
    private static void close(OutputStream out) throws IORuntimeException {
        if (out == null) {
            return;
        }
        try {
            out.close();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
