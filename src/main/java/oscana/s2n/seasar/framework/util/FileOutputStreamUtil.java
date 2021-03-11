/*
 * 取り込み元
 *    ライブラリ名：     seasar2
 *    クラス名：         org.seasar.framework.util.FileOutputStreamUtil
 *    ソースリポジトリ： https://github.com/seasarorg/seasar2/blob/master/seasar2/s2-framework/src/main/java/org/seasar/framework/util/FileOutputStreamUtil.java
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import oscana.s2n.seasar.framework.exception.IORuntimeException;

/**
 * {@link FileOutputStream}用のユーティリティクラス。
 *
 * @author higa
 *
 */
public class FileOutputStreamUtil {

    protected FileOutputStreamUtil() {
    }

    /**
     * {@link FileOutputStream}を作成する。
     *
     * @param file ファイル
     * @return {@link FileOutputStream}
     * @throws IORuntimeException
     *             {@link IOException}が発生した場合
     * @see FileOutputStream#FileOutputStream(File)
     */
    public static FileOutputStream create(File file) throws IORuntimeException {
        try {
            return new FileOutputStream(file);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}