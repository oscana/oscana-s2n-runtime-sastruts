/*
 * 取り込み元
 *    ライブラリ名：     sa-struts
 *    クラス名：         org.seasar.struts.config.S2ActionMapping
 *    ソースリポジトリ： https://github.com/seasarorg/sa-struts/blob/master/src/main/java/org/seasar/struts/config/S2ActionMapping.java
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
package oscana.s2n.seasar.struts.config;

import oscana.s2n.seasar.struts.util.RequestUtil;

/**
 * アクションマッピングクラス。<br>
 *
 * <br>
 * 移植内容の変更点：<br>
 * <br>
 *    ・{@link org.apache.struts.action.ActionMapping}を継承しないように変更する。<br>
 *
 * @author higa
 *
 */
public class OscanaActionMapping {

    /**
     * パスを返却する。
     * @return パス
     */
    public String getPath() {
        return RequestUtil.getPath();
    }

}
